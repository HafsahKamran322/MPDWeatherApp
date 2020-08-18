package com.example.weatherforecast.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.NetworkOnMainThreadException;

import com.example.weatherforecast.model.Channel;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Name and Matric Number: Hafsah Kamran, S1627179
public class Utility {
    static ParseXml parseXml = new ParseXml();
    public static String getUrlByPosition(String position){
        String url="";

        if(position.equalsIgnoreCase("1")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
        }else if(position.equalsIgnoreCase("2")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
        }else if(position.equalsIgnoreCase("3")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
        }else if(position.equalsIgnoreCase("4")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
        }else if(position.equalsIgnoreCase("5")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
        }else if(position.equalsIgnoreCase("6")){
            url="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
        }

        return  url;
    }


    public static void print(String x){
        System.out.println(x);
    }
    public static void print(int x){
        System.out.println(x);
    }

    static Channel channel;
    public static Channel fetchSingleChannelInfo (String position){
        channel = new Channel();



        channel = parseXml.getData(getUrlByPosition(position)); //Glasgow




        return channel;
    }

    public static String monthName(int monthId){
        String name="";

        if(monthId==1){
            name="Jan";
        }else if(monthId==2){
            name="Feb";
        }else if(monthId==3){
            name="Mar";
        }else if(monthId==4){
            name="Apr";
        }else if(monthId==5){
            name="May";
        }else if(monthId==6){
            name="June";
        }else if(monthId==7){
            name="July";
        }else if(monthId==8){
            name="Aug";
        }else if(monthId==9){
            name="Sep";
        }else if(monthId==10){
            name="Oct";
        }else if(monthId==11){
            name="Nov";
        }else if(monthId==12){
            name="Dec";
        }


        return name;


    }

}

