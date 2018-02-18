package bxute.readmore.data;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ankit on 8/15/2017.
 */

public class BookFlavors {

    public static final String PRINT_MAGAZINE = "magazines";
    public static final String PRINT_BOOK = "books";

    static String[] flavor = {
        "Android","Love", "Relationship", "Sherlock Holmes", "Sports", "Revolution", "Science", "Fiction", "Astronomy", "Erotic"
    };

    public static String getBookFlavor(){

        Random random = new Random();
        return flavor[random.nextInt(flavor.length)];

    }

}
