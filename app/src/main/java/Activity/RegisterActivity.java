package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText name, bussiness, city, phone, zipcode, address, email, password;

    String userName, userBussiness, userCity, userZipcode, userAddress, userEmail, userPassword, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

            }
        });
        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Device.isNetworkConnected(getApplicationContext())) {

                    //  startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    detailsUser();

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
        btnRegister = findViewById(R.id.btn_register);
        name = findViewById(R.id.name);
        bussiness = findViewById(R.id.bussiness);
        city = findViewById(R.id.city);
        phone = findViewById(R.id.phone);
        zipcode = findViewById(R.id.zipcode);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        userEmail = getIntent().getStringExtra("email");
        userPassword = getIntent().getStringExtra("password");
        userName = getIntent().getStringExtra("name");
        email.setText(userEmail);
        password.setText(userPassword);
        name.setText(userName);

        // SharedPreferences authPref = getSharedPreferences("auth", MODE_PRIVATE);
        // userName = authPref.getString("name", "N/A");


    }


    public void detailsUser() {
        //get the device mac address

        if (validateFeild()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading details....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().userDetails("" + userName, "" + userBussiness, "" + userCity, "" + userAddress, "" + userPhone, "" + userZipcode, "" + userEmail, "" + userPassword);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String sResponse = null;

                    try {
                        sResponse = response.body().string();
                        Log.d("Response", sResponse);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (sResponse != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(sResponse);
                            String status = jsonObject.optString("status");
                            String message = jsonObject.optString("message");


                            switch (status) {
                                case "200":
                                    progressDialog.dismiss();
                                    Message.toast(getApplicationContext(), "Details entered Successfully");
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));


                                    SharedPreferences preferences = getSharedPreferences("detail", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("name", userName);
                                    editor.putString("email", userEmail);
                                    editor.putString("contact", userPhone);
                                    editor.putString("bussiness", userBussiness);
                                    editor.putString("city", userCity);
                                    editor.putString("address", userAddress);
                                    editor.apply();

                                    email.setText("");
                                    password.setText("");
                                    name.setText("");
                                    zipcode.setText("");
                                    city.setText("");
                                    address.setText("");
                                    phone.setText("");
                                    bussiness.setText("");

                                    finish();
                                    break;

                                case "400":
                                    progressDialog.dismiss();
                                    Message.toast(getApplicationContext(), "mobile number already used...");
                                    break;

                                case "401":
                                    progressDialog.dismiss();
                                    Message.toast(getApplicationContext(), "Something went wrong...please try again");
                                    break;

                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t instanceof SocketTimeoutException)
                    {
                        // "Connection Timeout";
                        System.out.println("Connection Timeout");
                        progressDialog.dismiss();
                    }
                    else if (t instanceof IOException)
                    {
                        // "Timeout";
                        System.out.println("Timeout");
                        progressDialog.dismiss();
                    }
                    else
                    {
                        //Call was cancelled by user
                        if(call.isCanceled())

                        {
                            System.out.println("Call was cancelled forcefully");
                            progressDialog.dismiss();
                        }
                        else
                        {
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
        userBussiness = bussiness.getText().toString().trim();
        userAddress = address.getText().toString().trim();
        userZipcode = zipcode.getText().toString().trim();
        userCity = city.getText().toString().trim();
        userPhone = phone.getText().toString().trim();


        if (userName.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter name");
            return false;
        }
        if (userBussiness.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter Bussiness name");
            return false;
        }
        if (userCity.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter city");
            return false;
        }
        if (userAddress.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter address");
            return false;
        }
        if (userPhone.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter phone number");
            return false;
        }
        if (userZipcode.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter zipcode");
            return false;
        }
        if (userEmail.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter email");
            return false;
        }
        if (userPassword.isEmpty()) {
            Message.toast(getApplicationContext(), "Enter password");
            return false;
        } else
            return true;
    }

}
