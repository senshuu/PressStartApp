package com.example.david.pressstart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class SplashScreen extends Activity {

    ArrayList<XMLReader.Item> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new DownloadXmlTask().execute("http://stackoverflow.com/feeds/tag?tagnames=android&amp;sort=newest");
    }

        private class DownloadXmlTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... urls) {

                try {
                    items = loadXmlFromNetwork(urls[0]);
                } catch (IOException e) {
                    return "Error";
                } catch (XmlPullParserException e) {
                    return "Error";
                }
                return null;
            }


            @Override
            protected void onPostExecute(String result) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("items", items);

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);

                // close this activity
                finish();
                // Displays the HTML string in the UI via a WebView
                //myWebView.loadData(result, "text/html", null);
            }

        }

            private ArrayList<XMLReader.Item> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
                InputStream stream = null;
                // Instantiate the parser
                XMLReader XmlParser = new XMLReader();

                String title = null;
                String url = null;
                String summary = null;
                Calendar rightNow = Calendar.getInstance();
                DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");

                try {
                    stream = downloadUrl(urlString);
                    items = XmlParser.parse(stream);
                    // Makes sure that the InputStream is closed after the app is
                    // finished using it.
                } finally {
                    if (stream != null) {
                        stream.close();
                    }
                }

                // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
                // Each Entry object represents a single post in the XML feed.
                // This section processes the entries list to combine each entry with HTML markup.
                // Each entry is displayed in the UI as a link that optionally includes
                // a text summary.
                return items;
            }

            private InputStream downloadUrl(String urlString) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                return conn.getInputStream();
            }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
}
