package viewpagerexample.viewpagerexample;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by asaldanha on 3/14/2016.
 */
public class WatermarkerContract {

    public static final String WATERMARKER_TABLE = "watermarker_table";
    public static final String CONTENT_AUTHORITY = "viewpagerexample.viewpagerexample";
//    public static final String PATH = "watermarker";
    public static final String PATH = "shares";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class watermarker_table implements BaseColumns
    {

        //Table data - fields
        public static final String ID = "id";
        public static final String FILEPATH = "path";


        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, "watermarker");

        //Types
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        /**
         * A projection of all columns in the photos table.
         */
        public static final String[] PROJECTION_ALL = {ID, FILEPATH};

        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = FILEPATH + " ASC";

    }

}
