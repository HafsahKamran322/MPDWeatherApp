package com.example.weatherforecast.model;

import java.util.List;

//Name and Matric Number: Hafsah Kamran, S1627179
public class Channel {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String dcDate;
    private String dcCopyRights;

    private List<Item> itemList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    public String getDcCopyRights() {
        return dcCopyRights;
    }

    public void setDcCopyRights(String dcCopyRights) {
        this.dcCopyRights = dcCopyRights;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", dcDate='" + dcDate + '\'' +
                ", dcCopyRights='" + dcCopyRights + '\'' +
                ", itemList size =" + itemList.size() +
                '}';
    }


}
