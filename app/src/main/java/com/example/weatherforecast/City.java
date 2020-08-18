package com.example.weatherforecast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecast.model.Channel;
import com.example.weatherforecast.model.Item;
import com.example.weatherforecast.utils.Utility;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

//Name and Matric Number: Hafsah Kamran, S1627179
public class City extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener {
    String data = "";
    String posAndName="";
    String name = "";
    private ProgressDialog pDialog;
    String position = "-1";
    int int_position = 0;
    int int_length = 0;
    Item day1 = null;
    Item day2 = null;
    Item day3 = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Bundle bundle = getIntent().getExtras();
        posAndName =bundle.getString("name");
        String nameAndPosition[] = posAndName.split("::");
        try{
            int_length = Integer.parseInt(nameAndPosition[2].trim());
        }catch (NumberFormatException ex){}

        position = nameAndPosition[0];
        //Toast.makeText(getBaseContext(), "Selected position : " +position, Toast.LENGTH_SHORT).show();
         data = bundle.getString("data");


        new background_city().execute();



        Button btn = (Button) findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(City.this, v);
                 popup.setOnMenuItemClickListener(City.this);
                popup.inflate(R.menu.menu_scrolling);
                popup.show();
            }
        });

        final Button today = (Button) findViewById(R.id.button);
        final Button day_2 = (Button) findViewById(R.id.button2);
        final Button day_3 = (Button) findViewById(R.id.button3);
        final Button backButton = (Button) findViewById(R.id.buttonBack);
        final Button nextButton = (Button) findViewById(R.id.button6);

        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date date = new Date();
        System.out.println("simple date formater "+formatter.format(date));
       // String date1[]=currentTime.toString().split("T");
        today.setText("Today \n "+date.getDate() +" "+Utility.monthName(date.getMonth()));
        date.setDate(date.getDate()+1);
        day_2.setText("Tomorrow \n "+date.getDate() +" "+Utility.monthName(date.getMonth()));
        date.setDate(date.getDate()+1);
        day_3.setText("Day After Tomorrow \n "+date.getDate() +" "+Utility.monthName(date.getMonth()));
        final float y = (float) 1.5;
        final float y2 = (float) 1.00;
        today.setScaleY(y);
        today.setBackgroundResource(R.drawable.button);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    today.setScaleY(y);
                    day_2.setScaleY(y2);
                    day_3.setScaleY(y2);

                populatePage(day1);

            }
        });


        day_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                today.setScaleY(y2);
                day_2.setScaleY(y);
                day_3.setScaleY(y2);

                populatePage(day2);

            }
        });


        day_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                today.setScaleY(y2);
                day_2.setScaleY(y2);
                day_3.setScaleY(y);

                populatePage(day3);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 int_position = 0;
                try{
                    int_position = Integer.parseInt(position);
                }catch (NumberFormatException ex){}


                int_position++;
                if(int_position>=(int_length)){
                    int_position=1;
                }
                position = int_position+"";
                Utility.print("position next =:: "+position);
                backgroundProcess();
                today.setScaleY(y);
                today.setBackgroundResource(R.drawable.button);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int_position = 0;
                try{
                    int_position = Integer.parseInt(position);
                }catch (NumberFormatException ex){}


                int_position--;
                if(int_position<1){
                    int_position=int_length-1;
                }
                position = int_position+"";
                Utility.print("position back =:: "+position);
                backgroundProcess();
                today.setScaleY(y);
                today.setBackgroundResource(R.drawable.button);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.menu_home:
                System.out.println(" home button clicked ");
                Intent intent = new Intent(City.this, ScrollingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fetchData", "0");
                bundle.putString("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.menu_refresh:
                Intent intent2 = new Intent(City.this, City.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("name", position + "::" + posAndName +"::" + int_length);
                bundle2.putString("data", data);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                return true;

            default:
                return false;
        }
    }


    private class background_city extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing loading dialog
            pDialog = new ProgressDialog(City.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... voids) {

            backgroundProcess();

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


    public  void backgroundProcess(){
        if(!position.equalsIgnoreCase("-1")) {
            final Channel channel = Utility.fetchSingleChannelInfo(position);

            if(channel!=null && channel.getTitle()!=null) {
                name = channel.getTitle().replace("BBC Weather - Forecast for", "");
            }
            //System.out.println("channel by position is = " + channel.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int count=0;
                    for(Item item:channel.getItemList()){
                        count++;
                        if(count==1){
                            //for today;
                            day1 =item;
                            populatePage(item);

                        }else if(count==2){
                            day2 =item;
                        }else if(count==3){
                            day3 =item;
                        }
                    }

                }
            });
        }
    }
    public void populatePage(Item item){
        String desc[] = item.getDescription().split(",");

        int pos_humidity = 0;

        String maxTemp = desc[0];
        String minTemp = desc[1];


        String sunRise = desc[desc.length-2];

        if(sunRise.toLowerCase().contains("sunrise")){
            pos_humidity = desc.length-5;
        }else{
            sunRise ="0";
            pos_humidity = desc.length-4;
        }

        String humidity = desc[pos_humidity];

        String sunSet = desc[desc.length-1];
        String windSpeed = desc[pos_humidity-3];


        TextView humidityText = findViewById(R.id.textView11);
        TextView windSpeedText = findViewById(R.id.textView7);
        TextView sunSetText = findViewById(R.id.textView14);
        TextView sunRiseText = findViewById(R.id.textView12);



        humidityText.setText(humidity.replaceAll("\\D+","")+"%");
        windSpeedText.setText(windSpeed.replaceAll("\\D+",""));


        sunSetText.setText(sunSet.replace("EDT",""));


        if(!sunRise.equalsIgnoreCase("0")){

            sunRiseText.setText(sunRise.replace("EDT",""));
            sunRiseText.setVisibility(View.VISIBLE);
        }else{
            sunRiseText.setVisibility(View.INVISIBLE);
        }

        TextView maxTempText = findViewById(R.id.textView6);
        TextView minTempText = findViewById(R.id.textView5);
        TextView mainTempText = findViewById(R.id.textView4);
        TextView lastUpdatedTimeText = findViewById(R.id.textView3);
        TextView textView = findViewById(R.id.textView15);

        lastUpdatedTimeText.setText("Last updated today at: "+item.getPubDate().split(" ")[4]);
        textView.setText(name);

        if(maxTemp.toLowerCase().contains("maximum")){

            maxTemp = maxTemp.split(": ")[1];
            maxTemp = maxTemp.split(" ")[0];
            maxTemp = maxTemp.replaceAll("\\D+","");

            minTemp = minTemp.split(": ")[1];
            minTemp = minTemp.split(" ")[0];
            minTemp = minTemp.replaceAll("\\D+","");

            maxTempText.setText("Max Temp: "+maxTemp +"\u2103");
            minTempText.setText("Min Temp: "+minTemp +"\u2103");
            mainTempText.setText(minTemp +"\u2103");
            maxTempText.setVisibility(View.VISIBLE);
        }else{
            maxTemp = maxTemp.split(": ")[1];
            maxTemp = maxTemp.split(" ")[0];
            maxTemp = maxTemp.replaceAll("\\D+","");

            minTempText.setText("Min Temp: "+maxTemp +"\u2103");
            mainTempText.setText(maxTemp +"\u2103");
            maxTempText.setVisibility(View.INVISIBLE);
        }

    }
}