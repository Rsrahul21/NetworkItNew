package Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {

    private static String LOGIN_REQUEST_URL = "https://techpyton.000webhostapp.com/NetworkIt/groups.php";
    private Map<String, String> params;

    public LoginRequest( Response.Listener<String> listener) {
        super(Method.GET, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();

/*
        params.put("email", email);
        params.put("password", password);*/


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
