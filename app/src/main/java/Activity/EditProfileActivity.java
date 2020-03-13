package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

import Api.RetrofitClient;
import Constants.Device;
import Constants.Message;
import Fragments.ProfileFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    EditText name, email, bussiness, contact, city, address;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_signin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked



            }
        });*/


        initViews();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void initViews() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        bussiness = findViewById(R.id.bussiness);
        contact = findViewById(R.id.contact);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        save = findViewById(R.id.btn_save);


        final String mName = getIntent().getStringExtra("name");
        final String mEmail = getIntent().getStringExtra("email");
        final String mBussiness = getIntent().getStringExtra("bussiness");
        final String mContact = getIntent().getStringExtra("contact");
        final String mAddress = getIntent().getStringExtra("address");
        final String mCity = getIntent().getStringExtra("city");


        email.setText(mEmail);
        contact.setText(mContact);
        name.setText(mName);
        city.setText(mCity);
        bussiness.setText(mBussiness);
        address.setText(mAddress);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Device.isNetworkConnected(getApplicationContext())) {
                    Intent intent = new Intent();
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("contact", contact.getText().toString());
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("city", city.getText().toString());
                    intent.putExtra("bussiness", bussiness.getText().toString());
                    intent.putExtra("address", address.getText().toString());


                    SharedPreferences sharedPreferences = getSharedPreferences("detail", MODE_PRIVATE);

                    sharedPreferences.edit().remove("name").commit();
                    sharedPreferences.edit().remove("email").commit();
                    sharedPreferences.edit().remove("contact").commit();
                    sharedPreferences.edit().remove("bussiness").commit();
                    sharedPreferences.edit().remove("address").commit();


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name.getText().toString());
                    editor.putString("email", email.getText().toString());
                    editor.putString("contact", contact.getText().toString());
                    editor.putString("bussiness", bussiness.getText().toString());
                    editor.putString("city", city.getText().toString());
                    editor.putString("address", address.getText().toString());
                    setResult(ProfileFragment.RESULT_CODE, intent);
                    updateDetail();
                    finish();
                } else {
                    Message.toast(getApplicationContext(), "No Internet...Please Try later");
                }
            }
        });

    }


    public void updateDetail() {

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateDetail(name.getText().toString(), bussiness.getText().toString(), city.getText().toString(), address.getText().toString(), contact.getText().toString(), email.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String sResponse = null;
                try {
                    sResponse = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("update", sResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    // "Connection Timeout";
                    System.out.println("Connection Timeout");
                }
                else if (t instanceof IOException)
                {
                    // "Timeout";
                    System.out.println("Timeout");
                }
                else
                {
                    //Call was cancelled by user
                    if(call.isCanceled())
                    {
                        System.out.println("Call was cancelled forcefully");
                    }
                    else
                    {
                        //Generic error handling
                        System.out.println("Network Error :: " + t.getLocalizedMessage());
                    }
                }
            }
        });

    }


}
