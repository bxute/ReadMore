package bxute.readmore;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ankit on 8/17/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BooksTest {

    /*
    *  This test assumes that we have crossed the authentication part
    * */

    @Test
    public void testBooksLoad() {

        // this is a display test for item 1
        onView(withId(R.id.books_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView(withId(R.id.cover_art))
                .check(matches(isDisplayed()));
        onView(withId(R.id.title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.author))
                .check(matches(isDisplayed()));
        onView(withId(R.id.overflow_icon))
                .check(matches(isDisplayed()));

        //check for item 2
        onView(withId(R.id.books_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, scrollTo()));
        onView(withId(R.id.cover_art))
                .check(matches(isDisplayed()));
        onView(withId(R.id.title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.author))
                .check(matches(isDisplayed()));
        onView(withId(R.id.overflow_icon))
                .check(matches(isDisplayed()));


        // check for item 3
        onView(withId(R.id.books_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, scrollTo()));
        onView(withId(R.id.cover_art))
                .check(matches(isDisplayed()));
        onView(withId(R.id.title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.author))
                .check(matches(isDisplayed()));
        onView(withId(R.id.overflow_icon))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testDisplayOfTabs(){
        // Books tab is displayed
        onView(withText("Books"))
                .check(matches(isDisplayed()));

        // Magazines tab is displayed

        onView(withText("Magazines"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSwipeOfTabs(){
        //swipe to magazine tab
        onView(withId(R.id.viewpager)).perform(swipeRight());
        // now swipe back to books
        onView(withId(R.id.viewpager)).perform(swipeLeft());

    }

    @Test
    public void testMagazinesLoad(){
        // assumes that the selected tab is book
        //first select the magazine tab

        onView(withId(R.id.viewpager))
                .perform(swipeRight());     // selected

        onView(withId(R.id.magazines_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView(withId(R.id.cover_art))
                .check(matches(isDisplayed()));
        onView(withId(R.id.title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.author))
                .check(matches(isDisplayed()));
        onView(withId(R.id.overflow_icon))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testDetailsPage(){

        onView(withId(R.id.books_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        // check for description and publication date

        onView(withId(R.id.pub_date))
                .check(matches(isDisplayed()));

        onView(withId(R.id.description))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testDrawer(){

        onView(withId(R.id.drawer_layout))
                .perform(swipeRight());

        onView(withId(R.id.favorites_wrapper))
                .check(matches(isDisplayed()));

        onView(withId(R.id.logout_wrapper))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testFavoritePage(){

        onView(withId(R.id.drawer_layout))
                .perform(swipeRight());

        onView(withId(R.id.favorites_wrapper))
                .perform(click());

        onView(withId(R.id.favorite_recycler_view))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testLogout(){

        onView(withId(R.id.viewpager))
                .perform(swipeRight());

        onView(withId(R.id.logout_wrapper))
                .perform(click());
        // if sign in button is displayed, means we are in login page
        onView(withId(R.id.sign_in_button))
                .check(matches(isDisplayed()));

    }

}
