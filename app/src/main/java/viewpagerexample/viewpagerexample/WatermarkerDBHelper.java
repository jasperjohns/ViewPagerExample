package viewpagerexample.viewpagerexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import viewpagerexample.viewpagerexample.WatermarkerContract.watermarker_table;
/**
 * Created by asaldanha on 3/14/2016.
 */
public class WatermarkerDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Watermarker.db";
    private static final int DATABASE_VERSION = 1;
    public WatermarkerDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CreateWatermarkerTable = "CREATE TABLE " + WatermarkerContract.WATERMARKER_TABLE + " ("
                + watermarker_table.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + watermarker_table.FILEPATH + " TEXT NOT NULL"
                + " );";
        db.execSQL(CreateWatermarkerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + WatermarkerContract.WATERMARKER_TABLE);
    }
}
