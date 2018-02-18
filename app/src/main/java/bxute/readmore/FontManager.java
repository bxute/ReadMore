package bxute.readmore;

import android.content.Context;
import android.graphics.Typeface;


public class FontManager {

    public static final String FONT_MATERIAL = "materialFont.ttf";

    private Context context;

    public FontManager(Context context) {
        this.context = context;
    }

    public Typeface getTypeFace() {
        return Typeface.createFromAsset(context.getAssets(), FONT_MATERIAL);
    }

}
