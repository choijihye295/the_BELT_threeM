package com.summer.threemforth;

public class NoticeItem {
    private String belt;
    private String date;
    private int id;
    private String link;
    private String name;

    public NoticeItem(int i, String str, String str2, String str3, String str4) {
        this.id = i;
        this.name = str;
        this.link = str2;
        this.belt = str3;
        this.date = str4;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLink() {
        return this.link;
    }

    public String getBelt() {
        return this.belt;
    }

    public String getDate() {
        return this.date;
    }
}
