package com.example.memedatabase.dbinterface;

import com.example.domainobjects.Event;

import java.util.ArrayList;

public interface EventListInterface {
    /**
     * inserts an event to the DB
     * @param name : event name
     * @param desc : event desctiption
     * @param tag : event tag
     * @param positiveEvent : event type
     * @return returns true if insertion is successful
     */
    public boolean insertEvent(String name, String desc, String tag, boolean positiveEvent);

    /**
     * retrieves an event from the db given the event name
     * @param name
     * @return return an event object if the event exists, null otherwise
     */
    public Event retrieveEvent(String name);

    /**
     * retrieves all event names in the db
     * @return returns an ArrayList of Strings. returns empty list if db is empty
     */
    public ArrayList<String> retrieveAllEventNames();
}
