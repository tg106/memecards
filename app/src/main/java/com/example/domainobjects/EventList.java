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

    //For testing, will be deleted later
    public void generatingTestEventList() {
        eventList.add(new Event("weebs","Breaking new: all weebs are pedo", "Anime", 2, true));
        eventList.add(new Event("movies","Oscar Award is coming soon", "Movie", 1, true));
        eventList.add(new Event("cartoons123","CartoonNetwork joined Disney", "Cartoon", 0, true));
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
}
