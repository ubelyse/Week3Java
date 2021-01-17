
package com.example.eventreserve.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Event implements Parcelable
{

    @SerializedName("attending_count")
    @Expose
    private Integer attendingCount;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cost")
    @Expose
    private Object cost;
    @SerializedName("cost_max")
    @Expose
    private Object costMax;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("event_site_url")
    @Expose
    private String eventSiteUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("interested_count")
    @Expose
    private Integer interestedCount;
    @SerializedName("is_canceled")
    @Expose
    private Boolean isCanceled;
    @SerializedName("is_free")
    @Expose
    private Boolean isFree;
    @SerializedName("is_official")
    @Expose
    private Boolean isOfficial;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tickets_url")
    @Expose
    private String ticketsUrl;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    public final static Parcelable.Creator<Event> CREATOR = new Creator<Event>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return (new Event[size]);
        }

    }
    ;

    protected Event(Parcel in) {
        this.attendingCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.cost = ((Object) in.readValue((Object.class.getClassLoader())));
        this.costMax = ((Object) in.readValue((Object.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.eventSiteUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.interestedCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isCanceled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isFree = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isOfficial = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.latitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.ticketsUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.timeEnd = ((String) in.readValue((String.class.getClassLoader())));
        this.timeStart = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.businessId = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Event() {
    }

    /**
     * 
     * @param eventSiteUrl
     * @param timeEnd
     * @param cost
     * @param isCanceled
     * @param latitude
     * @param businessId
     * @param description
     * @param costMax
     * @param isOfficial
     * @param interestedCount
     * @param ticketsUrl
     * @param timeStart
     * @param isFree
     * @param imageUrl
     * @param name
     * @param location
     * @param attendingCount
     * @param id
     * @param category
     * @param longitude
     */
    public Event(Integer attendingCount, String category, Object cost, Object costMax, String description, String eventSiteUrl, String id, String imageUrl, Integer interestedCount, Boolean isCanceled, Boolean isFree, Boolean isOfficial, Double latitude, Double longitude, String name, String ticketsUrl, String timeEnd, String timeStart, Location location, String businessId) {
        super();
        this.attendingCount = attendingCount;
        this.category = category;
        this.cost = cost;
        this.costMax = costMax;
        this.description = description;
        this.eventSiteUrl = eventSiteUrl;
        this.id = id;
        this.imageUrl = imageUrl;
        this.interestedCount = interestedCount;
        this.isCanceled = isCanceled;
        this.isFree = isFree;
        this.isOfficial = isOfficial;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.ticketsUrl = ticketsUrl;
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.location = location;
        this.businessId = businessId;
    }


    public Integer getAttendingCount() {
        return attendingCount;
    }

    public void setAttendingCount(Integer attendingCount) {
        this.attendingCount = attendingCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getCost() {
        return cost;
    }

    public void setCost(Object cost) {
        this.cost = cost;
    }

    public Object getCostMax() {
        return costMax;
    }

    public void setCostMax(Object costMax) {
        this.costMax = costMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventSiteUrl() {
        return eventSiteUrl;
    }

    public void setEventSiteUrl(String eventSiteUrl) {
        this.eventSiteUrl = eventSiteUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(Integer interestedCount) {
        this.interestedCount = interestedCount;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(attendingCount);
        dest.writeValue(category);
        dest.writeValue(cost);
        dest.writeValue(costMax);
        dest.writeValue(description);
        dest.writeValue(eventSiteUrl);
        dest.writeValue(id);
        dest.writeValue(imageUrl);
        dest.writeValue(interestedCount);
        dest.writeValue(isCanceled);
        dest.writeValue(isFree);
        dest.writeValue(isOfficial);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(name);
        dest.writeValue(ticketsUrl);
        dest.writeValue(timeEnd);
        dest.writeValue(timeStart);
        dest.writeValue(location);
        dest.writeValue(businessId);
    }

    public int describeContents() {
        return  0;
    }

}
