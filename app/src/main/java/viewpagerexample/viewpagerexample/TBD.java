package viewpagerexample.viewpagerexample;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */


public class TBD extends BaseFragment {


    private String FolderName =  Constants.FolderName;

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";


    public static final TBD newInstance(String message)
    {
        TBD f = new TBD();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
/*
    @Override
    public void onStart() {
        newImagesReceiver = new NewImagesReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.SendBroadcast");

        getActivity().registerReceiver(newImagesReceiver, intentFilter);

        super.onStart();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (newImagesReceiver != null){

            getActivity().unregisterReceiver(newImagesReceiver);

        }
    }

*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.tbd, container, false);
//        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
//            messageTextView.setText(message);

        return v;
        }




}
