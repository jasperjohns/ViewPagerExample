package viewpagerexample.viewpagerexample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by asaldanha on 3/14/2016.
 */
public class WatermarkerProvider extends ContentProvider {

    // helper constants for use with the UriMatcher
    private static final int WATERMARKER_LIST = 1;
    private static final int WATERMARKER_ID = 2;

    // integer values used in content URI
    static final int SHARES_LIST = 1;
    static final int SHARE_ID = 2;


    private static final UriMatcher URI_MATCHER;

    private WatermarkerDBHelper mHelper = null;
    private  SQLiteDatabase database;
    private static HashMap<String, String> BirthMap;

    private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<Boolean>();

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
/*
        URI_MATCHER.addURI(WatermarkerContract.CONTENT_AUTHORITY, "items", WATERMARKER_LIST);
        URI_MATCHER.addURI(WatermarkerContract.CONTENT_AUTHORITY, "items/#", WATERMARKER_ID);
*/
        URI_MATCHER.addURI(WatermarkerContract.CONTENT_AUTHORITY, "shares", SHARES_LIST);
        URI_MATCHER.addURI(WatermarkerContract.CONTENT_AUTHORITY, "shares/#", SHARE_ID);

    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        mHelper = new WatermarkerDBHelper(context);
        // permissions to be writable
        database = mHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(WatermarkerContract.WATERMARKER_TABLE);

        switch (URI_MATCHER.match(uri)) {
            // maps all database column names
            case SHARES_LIST:
                //queryBuilder.setProjectionMap();
                break;
            case SHARE_ID:
                queryBuilder.appendWhere(  WatermarkerContract.watermarker_table.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = WatermarkerContract.watermarker_table.FILEPATH;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long row = database.insert(WatermarkerContract.WATERMARKER_TABLE, "", values);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(WatermarkerContract.BASE_CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (URI_MATCHER.match(uri)){
            case SHARES_LIST:
                count = database.update(WatermarkerContract.WATERMARKER_TABLE, values, selection, selectionArgs);
                break;
            case SHARE_ID:
                count = database.update(WatermarkerContract.WATERMARKER_TABLE, values,WatermarkerContract.WATERMARKER_TABLE +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (URI_MATCHER.match(uri)){
            case SHARES_LIST:
                // delete all the records of the table
                count = database.delete(WatermarkerContract.WATERMARKER_TABLE, selection, selectionArgs);
                break;
            case SHARE_ID:
                String id = uri.getLastPathSegment();	//gets the id
                count = database.delete( WatermarkerContract.WATERMARKER_TABLE, WatermarkerContract.watermarker_table.ID+  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;


    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        switch (URI_MATCHER.match(uri)){
            // Get all friend-birthday records
            case SHARES_LIST:
                return WatermarkerContract.watermarker_table.CONTENT_TYPE;
            // Get a item
            case SHARE_ID:
                return WatermarkerContract.watermarker_table.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
