package viewpagerexample.viewpagerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.screen_default_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String my_edittext_preference = mySharedPreferences.getString(getString(R.string.pref_watermarkImage_key), getString(R.string.pref_watermarkImage_default));


        // Get a list of fragments into a List
        List<Fragment> fragments = getFragments();

        // Use the list to create a Page Adapter - use the supprtFragmentAdapter and the fragments
        // implement constructor - assign all the fragmengts to a local variable getCount - total number of items and getItem - get specific item
        pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
        // assign the page adapter to the ViewPager
        // Get the ViewPager ID, set the adaper to it
        ViewPager view = (ViewPager) findViewById(R.id.viewpager);
        view.setAdapter(pageAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                if (position == 1){
//                    List<Fragment> fragments= getSupportFragmentManager().getFragments();
                    ProcessedImagesFragment p = (ProcessedImagesFragment) getSupportFragmentManager().getFragments().get(position);
                    if (p != null ){
//                        p.reLoad();

                    }
 /*
                    if(fragments != null){
                        for(Fragment fragment : fragments){
                            if(fragment != null && fragment.isVisible())
                        }
                    }
*/
                }


                Toast.makeText(MainActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        tabs.setViewPager(view);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent SettingsAct = new Intent( this, SettingsActivity.class) ;
            startActivity(SettingsAct);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Create and return a lits of fragments
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MainActivityFragment.newInstance("Fragment 1"));
        fList.add(ProcessedImagesFragment.newInstance("Fragment 2"));
        fList.add(TBD.newInstance("Fragment 3"));

        return fList;
    }

    private class PageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        // constructor - super on fragment manager, assign the fragment sto a private list
        public PageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override

        public CharSequence getPageTitle(int position) {
            CharSequence tabString="";
            if (position ==0 ) tabString = "Select";
            if (position ==1 ) tabString =  "Processed";
            if (position ==2 ) tabString =  "";
            return tabString;
        }


        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


}
