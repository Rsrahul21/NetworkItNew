package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.networkit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import Api.RetrofitClient;
import Constants.Device;
import Constants.Message;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignIn;
    Button btnSignup;
    EditText name, email, password;

    String userName, userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar_signup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();

            }
        });

        initViews();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   com.android.volley.Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);

                    }
                };

                RegisterRequest registerRequest=new RegisterRequest("@gmail.com","123","rahul","1","12","1",responseListener);
                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                queue.add(registerRequest);*/


                if (Device.isNetworkConnected(getApplicationContext())) {
                    registerUser();
                } else {
                    Message.toast(getApplicationContext(), "Please Check Internet");
                }

            }


        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void initViews() {
        txtSignIn = findViewById(R.id.txt_sign_in);
        btnSignup = findViewById(R.id.btn_signup);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


    }

    public void registerUser() {
        //get the device mac address
        if (validateFeild()) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing up....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String device_id = Device.getMacAddress(getApplicationContext());


            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().userRegistration("" + userEmail, "" + userPassword, "" + userName, "1", "" + device_id, "1");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String sResponse = null;

                    try {
                        sResponse = response.body().string();
                        Log.d("Response", sResponse + userEmail + userPassword + userName);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (sResponse != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(sResponse);
                            String status = jsonObject.optString("status");
                            String message = jsonObject.optString("message");

                            if (status.equals("200")) {
                                progressDialog.dismiss();
                                Message.toast(getApplicationContext(), "Registered Successfully");
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));

                                SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("name", userName);
                                editor.apply();
                                finish();
                            }
                            if (status.equals("400")) {
                                progressDialog.dismiss();
                                Message.toast(getApplicationContext(), "Email already exists...");
                            }
                            if (status.equals("401")) {
                                progressDialog.dismiss();
                                Message.toast(getApplicationContext(), "Something went wrong...please try again");
                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        // "Connection Timeout";
                        System.out.println("Connection Timeout");
                        progressDialog.dismiss();
                    } else if (t instanceof IOException) {
                        // "Timeout";
                        System.out.println("Timeout");
                        progressDialog.dismiss();
                    } else {
                        //Call was cancelled by user
                        if (call.isCanceled()) {
                            System.out.println("Call was cancelled forcefully");
                            progressDialog.dismiss();
                        } else {
                            //Generic error handling
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                            progressDialog.dismiss();
                        }
                    }
                }
            });
        }
    }


    public boolean validateFeild() {

        userName = name.getText().toString().trim();
        userPassword = password.getText().toString().trim();
        userEmail = email.getText().toString().trim();


        if (userName.isEmpty()) {
            Message.toast(getApplicationContext(), "Please Enter name");
            return false;
        } else if (userEmail.isEmpty()) {
            Message.toast(getApplicationContext(), "Please Enter Email");
            return false;
        } else if (userPassword.isEmpty()) {
            Message.toast(getApplicationContext(), "Please Enter Password");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Message.toast(getApplicationContext(), "Enter Valid Email");
            return false;
        } else
            return true;


    }


}
