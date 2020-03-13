package Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import Models.GroupData;

public class GroupDataResponse {

    String status, response;

    @SerializedName("data")
    ArrayList<GroupData> data = null;
/*    public GroupDataResponse(String status, String response, ArrayList<GroupData> data) {
        this.status = status;
        this.response = response;
        this.data = data.toArray(new GroupData[0]);
    }*/

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<GroupData> getData() {
        return data;
    }
}
