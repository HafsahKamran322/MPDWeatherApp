package com.example.weatherforecast.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Name and Matric Number: Hafsah Kamran, S1627179
public class DummyContent {


    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    public static DummyItem createDummyItem(String id, String content, String details) {
        return new DummyItem(id,content,details);
    }
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static ArrayList<DummyItem> createDummyList(int numContacts) {
        ArrayList<DummyItem> dummyItems = new ArrayList<DummyItem>();

        for (int i = 1; i <= numContacts; i++) {
            dummyItems.add(new DummyItem(i+"","a","b"));
        }

        return dummyItems;
    }


    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getDetails() {
            return details;
        }

        @Override
        public String toString() {
            return "{" +
                    "id='" + id + '\'' +
                    ", content='" + content + '\'' +
                    ", details='" + details + '\'' +
                    '}';
        }
    }
}