package viewpagerexample.viewpagerexample;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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


public class MainActivityFragment extends  BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

    private ArrayList<String> imageUrls;
    private DisplayImageOptions options;
    private ImageAdapter imageAdapter;
    private Drawable mCustomImage;
    private FloatingActionButton mFab;
    private TextView mFabText;
    private String FolderName =  Constants.FolderName;
    private final static int LOADER_ID = 0;

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final MainActivityFragment newInstance(String message)
    {
        MainActivityFragment f = new MainActivityFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    //LOADER METHODS
    @Override
    public Loader<Cursor> onCreateLoader(int loader, Bundle args) {
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        return new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!cursor.moveToFirst()) return;

        Context context = getActivity().getBaseContext();

        this.imageUrls = new ArrayList<String>();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String pathToImage =   cursor.getString(dataColumnIndex).toString();
            if (pathToImage.indexOf(FolderName) <=0 )
            {
                imageUrls.add(cursor.getString(dataColumnIndex));
            }
        }


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.stub_image)
                .showImageForEmptyUri(R.drawable.image_for_empty_url)
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        imageAdapter = new ImageAdapter(getActivity(), imageUrls);

        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prompt = (String)parent.getItemAtPosition(position);
                int itemsSelected = imageAdapter.getCheckedCount();
                mFabText.setText(String.valueOf(itemsSelected));
                Toast.makeText(getActivity(),
                        prompt,
                        Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        //mForecastAdapter.swapCursor(null);
        //return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            String message = getArguments().getString(EXTRA_MESSAGE);
    //        View v = inflater.inflate(R.layout.fragment_main, container, false);
                View v = inflater.inflate(R.layout.ac_image_grid, container, false);
        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
        mFab = (FloatingActionButton)v.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProcessImages();
            }
        });

//        getLoaderManager().initLoader(LOADER_ID, null, this);

        mFabText = (TextView) v.findViewById(R.id.textFab);
//            messageTextView.setText(message);


        //v.setContentView(R.layout.ac_image_grid);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .build();
        ImageLoader.getInstance().init(config);




        //Bundle bundle = getIntent().getExtras();
        //imageUrls = bundle.getStringArray(Extra.IMAGES);

        /*

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = getActivity().managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        this.imageUrls = new ArrayList<String>();

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String pathToImage =   imagecursor.getString(dataColumnIndex).toString();
            if (pathToImage.indexOf(FolderName) <=0 )
            {
                imageUrls.add(imagecursor.getString(dataColumnIndex));


            }
        }


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.stub_image)
                .showImageForEmptyUri(R.drawable.image_for_empty_url)
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        imageAdapter = new ImageAdapter(getActivity(), imageUrls);


        GridView gridView = (GridView) v.findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);

//        gridView.setOnItemClickListener(myOnItemClickListener);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prompt = (String)parent.getItemAtPosition(position);
                int itemsSelected = imageAdapter.getCheckedCount();
                mFabText.setText(String.valueOf(itemsSelected));
                Toast.makeText(getActivity(),
                        prompt,
                        Toast.LENGTH_LONG).show();
//				startImageGalleryActivity(position);

            }
        });
        */

        return v;
        }


    public void ProcessImages(){

        ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
        Toast.makeText(getActivity(), "Total photos selected: "+selectedItems.size(), Toast.LENGTH_SHORT).show();


//        Log.d(MultiPhotoSelectActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
    }


/*    @Override
    protected void onStop() {
        imageLoader.stop();
        super.onStop();
    }
*/



    public class ImageAdapter extends BaseAdapter {

        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray mSparseBooleanArray;
        int mPosition;


        public ImageAdapter(Context context, ArrayList<String> imageList) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageList;

        }

        public int getCheckedCount() {
            ArrayList<String> mTempArry = new ArrayList<String>();
            int i=0, j=0;
            for(i=0;i<mList.size();i++) {
                if(mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                    j++;
                }
            }
           return j;

        }

        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for(int i=0;i<mList.size();i++) {
                if(mSparseBooleanArray.get(i)) {
                    Bitmap tgtImg = BitmapFactory.decodeFile(mList.get(i));
                    Bitmap out = drawTextToBitmap(tgtImg, getActivity(), 11, "asdasdasdasdasdasdasda scdasdasdasdasdasdasdasdasd");


                    //Saves the image to the gallery area
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "title");
                    values.put(MediaStore.Images.Media.BUCKET_ID, "test");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "test Image taken");
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Uri uri = getActivity().getContentResolver().insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    saveImage(out);
                    mTempArry.add(mList.get(i));
                }

            }

            return mTempArry;
        }


        private void saveImage(Bitmap finalBitmap) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            System.out.println(root +" Root value in saveImage Function");
            File myDir = new File(root + FolderName);

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String iname = "Image-" + n + ".jpg";
            File file = new File(myDir, iname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

            String Image_path = Environment.getExternalStorageDirectory()+ "/Pictures/folder_name/"+iname;

            File[] files = myDir.listFiles();
            int numberOfImages=files.length;
            System.out.println("Total images in Folder "+numberOfImages);
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            mPosition = position;

            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
            }

            CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);

            imageLoader.displayImage("file://"+imageUrls.get(position), imageView, options, new SimpleImageLoadingListener() {
/*
                @Override
                public void onLoadingComplete(Bitmap loadedImage) {
                    Animation anim = AnimationUtils.loadAnimation(MultiPhotoSelectActivity.this, R.anim.fade_in);
                    imageView.setAnimation(anim);
                    anim.start();
                }
*/
            });

            imageView.setTag(position);
            mCheckBox.setTag(position);
            mCheckBox.setChecked(mSparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

            imageView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater)getActivity()
                            .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    ImageView imgView = (ImageView) popupView.findViewById(R.id.imgView);


//                    Picasso.with(mContext).load(get).resize(200, 200).centerCrop().into(imgView);


                    Toast.makeText(getActivity(),
                            String.valueOf(arg0.getTag()),
                            Toast.LENGTH_LONG).show();

                    int pos = Integer.valueOf(String.valueOf(arg0.getTag()));

                    imageLoader.displayImage("file://" + imageUrls.get(pos), imgView, options, new SimpleImageLoadingListener() {
                    });
/*
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL);
                    ImageView fullImageView = (ImageView) findViewById(R.id.full_image);
                    fullImageView.setImageResource(mThumbIds[position]);
*/


                    imgView.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            popupWindow.dismiss();
                        }});


                    Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                    btnDismiss.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            popupWindow.dismiss();
                        }});

                    popupWindow.setWidth(800);
                    popupWindow.setHeight(800);
//                    popupWindow.setFocusable(true);


//                    popupWindow.showAtLocation(this, Gravity.CENTER_VERTICAL);
                    popupWindow.showAsDropDown(imageView, 50, -100);

                }

/*
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),
                            "asdasda",
                            Toast.LENGTH_LONG).show();



                }
*/
            });

            return convertView;
        }





        CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                int itemsSelected = imageAdapter.getCheckedCount();
                mFabText.setText(String.valueOf(itemsSelected));


            }
        };

        public Bitmap drawTextToBitmap(Bitmap src, Context gContext,
                                       int gResId,
                                       String gText) {
            Resources resources = gContext.getResources();
            float scale = resources.getDisplayMetrics().density;

            Bitmap bitmap = src;
/*
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);
*/

            android.graphics.Bitmap.Config bitmapConfig =
                    bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(61, 61, 61));
            // text size in pixels
            paint.setTextSize((int) (14 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(gText, 0, gText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width())/2;
            int y = (bitmap.getHeight() + bounds.height())/2;

            canvas.drawText(gText, x, y, paint);
            Paint p = new Paint();
            mCustomImage = getActivity().getApplication().getDrawable(R.drawable.cmplogo);
            mCustomImage.setBounds(mCustomImage.getBounds());
//        mCustomImage.draw(canvas);

            SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String my_edittext_preference = mySharedPreferences.getString(getString(R.string.pref_watermarkImage_key), getString(R.string.pref_watermarkImage_default));
            Bitmap myBitmap = BitmapFactory.decodeFile(my_edittext_preference);

            Drawable d = (Drawable) new BitmapDrawable( myBitmap);


            Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.cmplogo);
//            canvas.drawBitmap(b,0,0,p);
            canvas.drawBitmap( myBitmap,0,0,p);

            return bitmap;
        }


    }


}
