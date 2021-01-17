
package com.example.eventreserve.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventSearch implements Parcelable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    public final static Parcelable.Creator<EventSearch> CREATOR = new Creator<EventSearch>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EventSearch createFromParcel(Parcel in) {
            return new EventSearch(in);
        }

        public EventSearch[] newArray(int size) {
            return (new EventSearch[size]);
        }

    }
    ;

    protected EventSearch(Parcel in) {
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.events, (Event.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public EventSearch() {
    }

    /**
     * 
     * @param total
     * @param events
     */
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(total);
        dest.writeList(events);
    }

    public int describeContents() {
        return  0;
    }

}
