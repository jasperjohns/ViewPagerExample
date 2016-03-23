package viewpagerexample.viewpagerexample;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by asaldanha on 3/10/2016.
 */
public class FetchService  extends IntentService {

    public FetchService()
    {
        super("FetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        ArrayList<String> imageUrls;
        Integer j=0;
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        imageUrls = new ArrayList<String>();

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String pathToImage =   imagecursor.getString(dataColumnIndex).toString();
            if (pathToImage.indexOf(Constants.FolderName) >0 )
            {
                j++;
            }
            if (j > 0){
                    Intent intentNew = new Intent(this, AppWidgetProvider.class);
                    intentNew.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                    int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), AppWidgetProvider.class));
                    intentNew.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                    intentNew.putExtra(Constants.TOTAL_INSERTED_DATA, j);
                    sendBroadcast(intentNew);
            }
        }




        return;

    }


}
