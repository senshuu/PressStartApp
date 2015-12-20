package com.example.david.pressstart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.david.pressstart.MESSAGE";

    ListView lv;
    public ArrayList<XMLReader.Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = (ArrayList<XMLReader.Item>) getIntent().getExtras().get("items");
        lv = (ListView) findViewById(R.id.listView);
        lv.setVerticalFadingEdgeEnabled(true);
        CustomListAdapter adapter = new CustomListAdapter(this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
// actions to be performed when a list item clicked
                int pos = arg2;
                String link = items.get(pos).link;
                Intent intent = new Intent(MainActivity.this,
                        WebViewActivity.class);
                intent.putExtra("link", link);
                startActivity(intent);

            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Create an Adapter Class extending the BaseAdapter
    class CustomListAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public CustomListAdapter(MainActivity activity) {
// TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getCount() {
// Set the count value to the total number of items in the Array
            return items.size();
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

// Inflate the item layout and set the views
            View listItem = convertView;
            int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.feedfragment, null);
            }

// Initialize the views in the layout
            TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
            //TextView tvDate = (TextView) listItem.findViewById(R.id.date);
            if (tvTitle != null)
            {
                tvTitle.setText(items.get(pos).title);
            }
            //tvDate.setText(items.get(pos).summary);

// Set the views in the layout

            return listItem;
        }

    }
}






