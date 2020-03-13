package Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import Models.GroupData;
import Models.MeetingData;

public class MeetingDataResponse {

    String status, response;
    @SerializedName("data")
    ArrayList<MeetingData> data = null;

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<MeetingData> getData() {
        return data;
    }


}
