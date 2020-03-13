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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

import Api.RetrofitClient;
import Constants.Device;
import Constants.Message;
import Response.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Response.GetDetailResponse;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSignUp;
    Button btnSignIn;
    EditText email, password;
    String userEmail, userPassword, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,+
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = findViewById(R.id.toolbar_signin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(false);
        /*toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

            }
        });*/
        initView();


    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void initView() {
        txtSignUp = findViewById(R.id.txt_sign_up);
        btnSignIn = findViewById(R.id.btn_sign_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

                if (vaildate()) {
/*
                    userEmail = email.getText().toString().trim();
                    userPassword = password.getText().toString().trim();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("LoginResponse", response);

                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(userEmail, userPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(loginRequest);*/


                    if (Device.isNetworkConnected(getApplicationContext())) {

                        userLogin();
                    } else {
                        Message.toast(getApplicationContext(), "Please Check Internet");
                    }

                }

                break;

            case R.id.txt_sign_up:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
                break;

        }
    }

    public boolean vaildate() {
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Message.toast(getApplicationContext(), "Email or password is missing");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Message.toast(getApplicationContext(), "Enter Valid Email");
            return false;
        } else
            return true;
    }


    private void userLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin("" + userEmail, "" + userPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                LoginResponse dr = response.body();
                String status = dr.getSuccess();
                String message = dr.getMessage();
                Log.d("LoginResponse", status + dr.getMessage() + dr.getEmail() + dr.getName());

                if (status.equals("true") && message.equals("login successfully")) {

                    SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", userEmail);
                    editor.putString("password", userPassword);
                    editor.putString("status", "login");
                    editor.apply();
                    userName = dr.getName();
                    checkDetail();
                    progressDialog.dismiss();

                    //check details here


                }
                if (status.equals("false") && message.equals("please check email or password")) {
                    progressDialog.dismiss();
                    Message.toast(getApplicationContext(), "please check email or password");


                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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


    public void checkDetail() {
        Call<GetDetailResponse> call = RetrofitClient.getInstance().getApi().getDetail(userEmail);
        call.enqueue(new Callback<GetDetailResponse>() {
            @Override
            public void onResponse(Call<GetDetailResponse> call, Response<GetDetailResponse> response) {
                GetDetailResponse getDetailResponse = response.body();
                Log.d("GetResponseDetail", getDetailResponse.getAddress() + getDetailResponse.getCity() + getDetailResponse.getName());
                if (getDetailResponse.getStatus().equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    SharedPreferences preferences = getSharedPreferences("detail", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", getDetailResponse.getName());
                    editor.putString("email", getDetailResponse.getEmail());
                    editor.putString("contact", getDetailResponse.getPhone());
                    editor.putString("bussiness", getDetailResponse.getBussiness());
                    editor.putString("city", getDetailResponse.getCity());
                    editor.putString("address", getDetailResponse.getAddress());
                    editor.apply();
                    startActivity(intent);
                    finish();
                }

                if (getDetailResponse.getStatus().equals("0")) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra("email", userEmail);
                    intent.putExtra("password", userPassword);
                    intent.putExtra("name", userName);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GetDetailResponse> call, Throwable t) {

            }
        });
    }

}
