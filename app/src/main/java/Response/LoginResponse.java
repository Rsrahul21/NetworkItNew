package Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("email")
    String email;

    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
