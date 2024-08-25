package entity;

public class Event{
    private String EventName;
    private String EventLocation;
    private String EventTime;
    private String EventDate;
    private String volunteer;

    private String eventId;
    private String eventName;


    public Event(String EventName, String EventLocation, String EventTime, String EventDate) {
        this.EventName = EventName;
        this.EventLocation = EventLocation;
        this.EventTime = EventTime;
        this.EventDate = EventDate;
    }

    public Event(String eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public String geteventId() {
        return eventId;
    }

    public String geteventName() {
        return eventName;
    }


    // Getters and Setters

    public String getEventName() {
        return EventName;
    }


    public void setEventName(String eventName) {
        EventName = eventName;
    }


    public String getEventLocation() {
        return EventLocation;
    }


    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }


    public String getEventTime() {
        return EventTime;
    }


    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }


    public String getEventDate() {
        return EventDate;
    }


    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public void assignVolunteer(String volunteerName) {
        this.volunteer = volunteerName;
    }

    public String getVolunteer() {
        return volunteer;
    }

    @Override
    public String toString() {
        return "Event{" +
                " Tittle='" + EventName + '\'' +
                ", Event Location ='" + EventLocation + '\'' +
                ", Event Time='" + EventTime + '\'' +
                ", Event Date='" + EventDate + '\'' +
                '}';
    }
}
