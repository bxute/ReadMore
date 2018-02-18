package bxute.readmore;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Ankit on 8/17/2017.
 */

public class SuggestionAsyncTaskTest {
    CountDownLatch signal = null;
    String[] mSuggestions = {};

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("bxute.readmore", appContext.getPackageName());
    }

    @Test
    public void testJokeLoaderAsyncTask() throws InterruptedException {
        signal = new CountDownLatch(1);
        SuggestionAsyncTask task = new SuggestionAsyncTask();
        task.setSuggestionListener(new SuggestionAsyncTask.OnSuggestionAvailableListener() {
            @Override
            public void onSuggestionsAvailable(String[] suggs) {
                mSuggestions = suggs;
            }
        }).execute("love");

        signal.await();
        assertFalse(mSuggestions.length==0);
    }
}

