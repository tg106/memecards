package com.example.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private List<Event> eventList;

    public EventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public EventList() {
        eventList = new ArrayList<Event>();
    }

    public void setEventList(ArrayList<Event> db) {
        eventList = db;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public Event getEventByPos(int pos) {
        Event temp = eventList.get(pos);
        return temp;
    }

    public int getEventListLength() {
        return eventList.size();
    }
}
