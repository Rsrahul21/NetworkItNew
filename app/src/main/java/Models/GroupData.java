package Models;

public class GroupData {

    String id;
    String group_name;
    String status;
    String rating;
    String address;
    String image;
    String organizer;
    String meeting_days;
    String title;
    String bussiness;
    String details;

    String[] group_images = null;

    public String[] getGroup_images() {
        return group_images;
    }

    public String getDetail() {
        return details;
    }

    public String getBussiness() {
        return bussiness;
    }

    public String getMeeting_days() {
        return meeting_days;
    }

    public void setMeeting_days(String meeting_days) {
        this.meeting_days = meeting_days;
    }

    public String getMeeting_time() {
        return meeting_time;
    }

    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
    }

    String meeting_time;

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public String getOrganizer() {
        return organizer;
    }


    public String getTitle() {
        return title;
    }


    public String getId() {
        return id;
    }

    public String getGroup_name() {
        return group_name;
    }


}
