
package com.example.eventreserve.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventSearch {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;

    public EventSearch() {
    }

    public EventSearch(Integer total, List<Event> events) {
        super();
        this.total = total;
        this.events = events;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
