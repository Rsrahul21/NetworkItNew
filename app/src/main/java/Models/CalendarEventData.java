package Models;

public class CalendarEventData {

    String id,title,event_name,location,detail,address,date,start_time,end_time,image,organizer;

    public String[] getEvent_images() {
        return event_images;
    }

    String[] event_images=null;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getLocation() {
        return location;
    }

    public String getDetail() {
        return detail;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getImage() {
        return image;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setEvent_images(String[] event_images) {
        this.event_images = event_images;
    }
}
