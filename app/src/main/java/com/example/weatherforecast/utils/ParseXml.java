package com.example.weatherforecast.utils;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.StrictMode;
import android.util.Log;

import com.example.weatherforecast.model.Channel;
import com.example.weatherforecast.model.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Name and Matric Number: Hafsah Kamran, S1627179
public class ParseXml {
    ArrayList<HashMap<String, String>> items ;

    List<Item> itemList= new ArrayList<>();
    Item item = null;
    Channel channel =null;

    public Channel getData(String URL ) {
        List<Channel> channelList = new ArrayList<>();
        Channel channel2 = new Channel();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("background process started--- in HOME and date = "+URL);
        HttpUtil http = new HttpUtil();
        String xml = http.sendPostRequest(URL);
        //System.out.println(xml);

        try {
            channel = null;
            item = null;
            itemList = null;
            if(xml!=null && !xml.equalsIgnoreCase("")) {
                xmlPullParser(xml);
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        channel2 = channel;
        System.out.println(" list returned = "+itemList.size());
        return channel2;

    }

    public void xmlPullParser(String stringToConvert) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        parser.setInput( new StringReader( stringToConvert ) );
        int eventType = parser.getEventType();
        boolean done = false;

        while (eventType != XmlPullParser.END_DOCUMENT && !done) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("channel")) {

                        channel = new Channel();
                        itemList = new ArrayList<>();
                    } else if (name.equalsIgnoreCase("item")) {

                        item = new Item();
                    } else if (item == null && channel!=null) {
                         if (name.equalsIgnoreCase("title")) {
                             channel.setTitle(parser.nextText().trim());
                             System.out.println(" i am setting channel title = " + channel.getTitle());
                         }else if (name.equalsIgnoreCase("link")) {
                             channel.setLink(parser.nextText());

                         }
                    } else if (item != null && channel!=null) {
                        if (name.equalsIgnoreCase("link")) {
                           item.setLink(parser.nextText());
                        } else if (name.equalsIgnoreCase("description")) {

                            item.setDescription(parser.nextText().trim());
                        } else if (name.equalsIgnoreCase("pubDate")) {
                            item.setPubDate(parser.nextText());
                        } else if (name.equalsIgnoreCase("title")) {
                            String s = parser.nextText().trim();
                                item.setTitle(s);

                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("item") && item != null && channel!=null) {
                        itemList.add(item);
                        System.out.println("size of list itemList = "+itemList.size());
                    } else if (name.equalsIgnoreCase("channel")) {
                        done = true;
                        channel.setItemList(itemList);
                    }
                    break;
            }
            eventType = parser.next();
        }
        System.out.println("End document");
    }
}
