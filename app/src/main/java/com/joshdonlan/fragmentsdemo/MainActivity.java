package com.joshdonlan.fragmentsdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends Activity implements SearchFragment.OnFragmentInteractionListener {

    private final String TAG = "FragmentsDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.search, new SearchFragment())
                    .add(R.id.details, new DetailsFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDisplay(Stock stock){
        ((TextView) findViewById(R.id.data_symbol)).setText(stock.getSymbol());
        ((TextView) findViewById(R.id.data_price)).setText(stock.getPrice().toString());
        ((TextView) findViewById(R.id.data_updated)).setText(stock.getDate());
        ((TextView) findViewById(R.id.data_high)).setText(stock.getHigh().toString());
        ((TextView) findViewById(R.id.data_low)).setText(stock.getLow().toString());
        ((TextView) findViewById(R.id.data_change)).setText(stock.getChange().toString());
        ((TextView) findViewById(R.id.data_open)).setText(stock.getOpen().toString());
        ((TextView) findViewById(R.id.data_volume)).setText(stock.getVolume().toString());
        ((TextView) findViewById(R.id.data_chgpct)).setText(stock.getPercent());
    }

    @Override
    public void OnSearchFragmentInteraction(String symbol) {
        Log.i(TAG,"Search Received: " + symbol);
        try {
            String baseURL = "http://query.yahooapis.com/v1/public/yql";
            String yql = "select * from csv where url='http://download.finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1d1t1c1ohgvp2&e=.csv' and columns='symbol,price,date,time,change,open,high,low,volume,chgpct'";
            String qs = URLEncoder.encode(yql, "UTF-8");
            URL queryURL = new URL(baseURL + "?q=" + qs + "&format=json");
            new GetQuoteTask().execute(queryURL);
        } catch (Exception e) {
            Log.e(TAG, "Invalid query for symbol: " + symbol);
        }
    }

    private class GetQuoteTask extends AsyncTask<URL, Integer, JSONObject> {

        final String TAG = "API DEMO ASYNCTASK";

        @Override
        protected JSONObject doInBackground(URL... urls) {

            String jsonString = "";

            //COLLECT STRING RESPONSES FROM API
            for(URL queryURL : urls){
                try{
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                    break;
                } catch (Exception e){
                    Log.e(TAG, "Could not establish URLConnection to " + queryURL.toString());
                }
            }

            Log.i(TAG, "Received Data: " + jsonString);


            //CONVERT API STRING RESPONSE TO JSONOBJECT

            JSONObject apiData;

            try{
                apiData = new JSONObject(jsonString);
            } catch (Exception e){
                Log.e(TAG, "Cannot convert API response to JSON");
                apiData = null;
            }

            try{
                apiData = (apiData != null) ? apiData.getJSONObject("query").getJSONObject("results").getJSONObject("row") : null;
                Log.i(TAG, "API JSON data received: " + apiData.toString());
            } catch (Exception e){
                Log.e(TAG, "Could not parse data record from response: " + apiData.toString());
                apiData = null;
            }

            return apiData;
        }

        protected void onPostExecute(JSONObject apiData) {
            Stock result = new Stock(apiData);
            updateDisplay(result);
        }
    }
}
