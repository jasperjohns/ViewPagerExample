package viewpagerexample.viewpagerexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */


public class MainActivityFragment extends android.support.v4.app.Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            String message = getArguments().getString(EXTRA_MESSAGE);
            View v = inflater.inflate(R.layout.fragment_main, container, false);
            TextView messageTextView = (TextView)v.findViewById(R.id.textView);
            messageTextView.setText(message);

            return v;
        }
}
