package com.example.domainobjects;

public class Event {

    private String name;
    private String desc;
    private String tag;
    private int prio;
    private boolean positiveEvent;

    public Event(String name, String desc, String tag, int prio, boolean b) {
        this.name = name;
        this.desc = desc;
        this.tag = tag;
        this.prio = prio;
        this.positiveEvent = b;
    }

    public Event(String name, String desc, String tag, boolean b) {
        this.name = name;
        this.desc = desc;
        this.tag = tag;
        this.positiveEvent = b;

        this.prio = (int) (Math.random()*3);        //For 3 priority in this game (Hot, New, Fluff)
    }

    public String getName() {
        return name;
    }

    public boolean checkEventPositive() {
        return positiveEvent;
    }

    public int getPrio() {
        return prio;
    }

    public String getDesc() {
        return desc;
    }

    public String getTag() {
        return tag;
    }

}
