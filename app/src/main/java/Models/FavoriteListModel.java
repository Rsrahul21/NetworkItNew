package Models;

public class FavoriteListModel {


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

    public void setId(String id) {
        this.id = id;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setGroup_images(String[] group_images) {
        this.group_images = group_images;
    }
}
