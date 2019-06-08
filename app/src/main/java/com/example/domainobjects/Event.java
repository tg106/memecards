package com.example.domainobjects;
import java.util.List;

public class Event {

    private String name;
    private String desc;
    private String tag;
    private int prio;
    private boolean positiveEvent;

    public Event(String name, String desc, String tag, int prio, boolean b){
        this.name = name;
        this.desc = desc;
        this.tag = tag;
        this.prio = prio;
        positiveEvent = b;
    }

    public Event(String name, String desc, String tag, boolean b)
    {
        this.name = name;
        this.desc = desc;
        this.tag = tag;
        positiveEvent = b;

        this.prio = (int) (Math.random()*2);
    }

    public String getName() {
        return name;
    }

    public boolean checkEventPositive()
    {
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
