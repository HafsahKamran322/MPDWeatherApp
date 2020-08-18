package com.example.weatherforecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.weatherforecast.dummy.DummyContent;
import com.example.weatherforecast.model.Channel;
import com.example.weatherforecast.model.Item;
import com.example.weatherforecast.utils.NetworkUtil;
import com.example.weatherforecast.utils.ParseXml;
import com.example.weatherforecast.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//Name and Matric Number: Hafsah Kamran, S1627179
public class ScrollingActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ArrayList<DummyContent.DummyItem> dummyItems;

    SharedPreferences prefs = null;
    MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(dummyItems);
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.example.weatherforecast", MODE_PRIVATE);
        setContentView(R.layout.activity_scrolling);

        Button btn = (Button) findViewById(R.id.menu_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ScrollingActivity.this, v);
               popup.setOnMenuItemClickListener(ScrollingActivity.this);
                popup.inflate(R.menu.menu_scrolling);
                popup.show();
            }
        });


        String s = NetworkUtil.getConnectivityStatusString(this);
        Utility.print("Network String ::  "+s);
        if(!s.equalsIgnoreCase("no_internet") && !s.isEmpty()){
            Utility.print("Network is available ");

            new GetItems().execute();
            dummyItems = new ArrayList<>();
            dummyItems.add(DummyContent.createDummyItem("","",""));

            setAdapter();
        }else{
            Utility.print("No Network is available");
            Toast.makeText(this, "No internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ScrollingActivity.this, ScrollingActivity.class);
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.menu_home:
                System.out.println(" home button clicked ");

                bundle.putString("fetchData", "0");
                bundle.putString("data", new Gson().toJson(dummyItems));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.menu_refresh:
                System.out.println(" refresh button clicked ");
                bundle.putString("fetchData", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }


    private class GetItems extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing loading dialog
           pDialog = new ProgressDialog(ScrollingActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... voids) {


            String fetchData ="1";
            Bundle bundle = getIntent().getExtras();

            Intent intent= getIntent();
            if (intent.hasExtra("fetchData")) {
                String stuff = bundle.getString("fetchData");
                System.out.println(" fetch data = " + fetchData + " and stuff = " + stuff);
                fetchData = stuff;
            }


            if(fetchData.equalsIgnoreCase("1")) {
               fetchData();
                System.out.println("size of the list in background = "+dummyItems.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("size of the list in background  2 = "+dummyItems.size());

                        // this will be called on refresh
                        setAdapter();

                    }
                });

            }else{
                if (intent.hasExtra("data")) {
                    String stuff = bundle.getString("data");
                    System.out.println("stuff is printing now = "+stuff);
                    try {

                        dummyItems = new ArrayList<>();
                        Gson gson = new Gson();
                        TypeToken<List<DummyContent.DummyItem>> list2 = new TypeToken<List<DummyContent.DummyItem>>() {};
                        dummyItems.addAll((Collection<? extends DummyContent.DummyItem>) gson.fromJson(stuff, list2.getType()));
                       System.out.println("gson data = "+dummyItems.size());

                        setAdapter();
                    } catch (Throwable t) {
                        //System.out.println("My App"+ "Could not parse malformed JSON: "+ stuff );
                    }

                }else{
                    dummyItems = new ArrayList<>();
                    dummyItems.add(DummyContent.createDummyItem("","",""));
                }


            }





            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }


        }
    }


    public void fetchData (){
        dummyItems = new ArrayList<>();

        ParseXml parseXml = new ParseXml();
        List<Channel> channelList =  new ArrayList<>();

        Channel glasgowList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579"); //Glasgow
        channelList.add(glasgowList);
               Channel londonList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743"); //London
        Channel newYorkList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581"); //NewYork
        Channel omanList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286"); //Oman
        Channel mauritiusList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154"); //Mauritius
        Channel bangladeshList = parseXml.getData("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241"); //Bangladesh

        channelList.add(londonList);
        channelList.add(newYorkList);
        channelList.add(omanList);
        channelList.add(mauritiusList);
        channelList.add(bangladeshList);
        dummyItems = new ArrayList<>();
        dummyItems.add(DummyContent.createDummyItem("","",""));
        for (Channel channel : channelList) {
        //    System.out.println(" data : " + item.toString());
            String a ="";
            if(channel!=null && channel.getTitle()!=null) {
                 a = channel.getTitle().replace("BBC Weather - Forecast for", "");
            }

            int count=0;
           for (Item item : channel.getItemList()) {
                //System.out.println(" item data : " + item.toString());
                if(count==0){
                    String description = item.getDescription();
                    String desc[] = description.split(",");
                    desc[0] = desc[0].split(": ")[1];
                    desc[0] = desc[0].split(" ")[0];
                    desc[0] = desc[0].replaceAll("\\D+","");
                    dummyItems.add(DummyContent.createDummyItem(a,desc[0], "\u2103"));
                }
                count++;


            }
        }
    }

    public  void setAdapter(){
        final RecyclerView rvContacts = (RecyclerView) findViewById(R.id.list);
        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(dummyItems);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = rvContacts.getChildAdapterPosition(v);
                if(pos>0) {
                   //Toast.makeText(getBaseContext(), "Selected position : " +pos, Toast.LENGTH_SHORT).show();
                    System.out.println("dummy item list size = " + dummyItems.size());
                    DummyContent.DummyItem itemCLicked = dummyItems.get(pos);
                    System.out.println("clicked = " + itemCLicked.getContent());
                    Intent intent = new Intent(ScrollingActivity.this, City.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", pos + "::" + itemCLicked.getId() +"::" + dummyItems.size());
                    bundle.putString("data", new Gson().toJson(dummyItems));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
}