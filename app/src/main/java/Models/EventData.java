package Models;

public class EventData {

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
}
