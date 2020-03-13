package Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {

    private static String REGISTER_REQUEST_URL = "https://techpyton.000webhostapp.com/NetworkIt/groups.php";
    private Map<String, String> params;

    public RegisterRequest(String email, String password, String username, String status, String device_id, String token_id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();


        params.put("email", email);
        params.put("password", password);
        params.put("username", username);
        params.put("status", status);
        params.put("device_id", device_id);
        params.put("token_id", token_id);

        //yehi ek parameter h

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
