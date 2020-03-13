package Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import Models.ApprovedEventData;

public class ApprovedEventResponse {

    @SerializedName("data")
    ArrayList<ApprovedEventData> data;
    String email,status,response;

    public ArrayList<ApprovedEventData> getData() {
        return data;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
