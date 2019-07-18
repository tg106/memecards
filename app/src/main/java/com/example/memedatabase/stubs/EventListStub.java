package com.example.memedatabase.stubs;

import com.example.domainobjects.Event;
import com.example.memedatabase.dbinterface.EventListInterface;

import java.util.ArrayList;

public class EventListStub implements EventListInterface {
    private static ArrayList<Event> mock = new ArrayList<>();

    @Override
    public boolean insertEvent(String name, String desc, String tag, boolean positiveEvent) {
        if (this.retrieveEvent(name) == null)
            mock.add(new Event(name, desc, tag, positiveEvent));
        else
            return false;
        return true;
    }

    @Override
    public Event retrieveEvent(String name) {
        Event event =  null;
        for (Event e : mock){
            if (e.getName().equals(name))
                event = e;
        }
        return event;
    }

    @Override
    public ArrayList<String> retrieveAllEventNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Event e : mock)
            names.add(e.getName());
        return names;
    }

    // helper method to reset stub db for tests
    public void resetStub(){
        EventListStub.mock = new ArrayList<>();
    }
}
