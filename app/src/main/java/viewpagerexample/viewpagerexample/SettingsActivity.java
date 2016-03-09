package viewpagerexample.viewpagerexample;

/**
 * Created by asaldanha on 6/15/2015.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.provider.MediaStore;
import android.util.Log;


/**
 * A {@link PreferenceActivity} that presents a set of application settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity
         {

    private static String LOG_TAG = SettingsActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        // TODO: Add preferences from XML
        addPreferencesFromResource(R.xml.pref_general);
        // Bind the prefernce back
//        bindPreferenceSummaryToValue(findPreference("pref_location_key"));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_watermarkImage_key)));


    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
//        Preference prefereces=findPreference("test");
        preference.setOnPreferenceClickListener (new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                int PICK_IMAGE = 1;
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                return true;
            }
        });
    }

     protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
         super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

         if (resultCode == RESULT_OK) {
             Uri selectedImage = imageReturnedIntent.getData();
             String[] filePathColumn = {MediaStore.Images.Media.DATA};

             Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
             cursor.moveToFirst();

             int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
             String filePath = cursor.getString(columnIndex);
             cursor.close();



             Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
             e.putString("WatermarkImage", filePath.toString());
             e.commit();

             Log.d(LOG_TAG, "Data Recieved! " + filePath);

         }
     }
}