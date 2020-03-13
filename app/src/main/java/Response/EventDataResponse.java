package Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import Models.EventData;

public class EventDataResponse {

    String status, response;

    @SerializedName("data")
    ArrayList<EventData> data=null;

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<EventData> getData() {
        return data;
    }
}
