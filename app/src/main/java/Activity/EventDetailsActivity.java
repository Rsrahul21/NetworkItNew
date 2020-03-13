package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.SlidingEventImage_Adapter;
import Api.RetrofitClient;
import Constants.DBHelper;
import Constants.Device;
import Constants.Message;
import Response.GeneralResponse;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static ViewPager mPager;
    private static String[] IMAGES = null;
    private ArrayList<String> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView title, event_name, location, detail, address, date, start_time, end_time, organizer;
    CircleImageView profileImage;
    Button addToCalendar, requestPass;
    String mEventId, mTitle, mEventName, mStartTime, mDate, mOrganizer, mImage, mAddress, mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_design_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                if (getIntent().getStringExtra("choice").equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), ExploreActivity.class);
                    intent.putExtra("item", "event");
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }

            }
        });

        initViews();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getEventData();
        //   getEventImages();
    }

    public void initViews() {

        title = findViewById(R.id.name);
        event_name = findViewById(R.id.event_name);
        start_time = findViewById(R.id.start_time);
        //end_time = findViewById(R.id.end_time);
        date = findViewById(R.id.date);
        organizer = findViewById(R.id.organizer);
        profileImage = findViewById(R.id.image);
        address = findViewById(R.id.address);
        detail = findViewById(R.id.detail);
        addToCalendar = findViewById(R.id.btn_add_to_calendar);
        requestPass = findViewById(R.id.btn_request_pass);

        addToCalendar.setOnClickListener(this);
        requestPass.setOnClickListener(this);

    }

    private void getEventData() {

        mTitle = getIntent().getStringExtra("title");
        mEventName = getIntent().getStringExtra("event_name");
        mStartTime = getIntent().getStringExtra("start_time");
        // mEndTime = getIntent().getStringExtra("end_time");
        mDate = getIntent().getStringExtra("date");
        mOrganizer = getIntent().getStringExtra("organizer");
        mImage = getIntent().getStringExtra("image");
        mAddress = getIntent().getStringExtra("address");
        mDetail = getIntent().getStringExtra("detail");
        mEventId = getIntent().getStringExtra("event_id");


        title.setText(mTitle);
        event_name.setText(mEventName);
        start_time.setText(mStartTime);
//      end_time.setText(mEndTime);
        date.setText(mDate);
        organizer.setText(mOrganizer);
        address.setText(mAddress);
        detail.setText(mDetail);


        Glide.with(this).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + mImage).into(profileImage);

    }

    public void getEventImages() {

        IMAGES = getIntent().getStringArrayExtra("event_images");
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = findViewById(R.id.img);
        mPager.setAdapter(new SlidingEventImage_Adapter(EventDetailsActivity.this, ImagesArray));
        final float density = getResources().getDisplayMetrics().density;
        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_to_calendar:
                addToCalendar();
                break;

            case R.id.btn_request_pass:
                if (Device.isNetworkConnected(getApplicationContext()))
                    requestPass();
                else
                    Message.toast(getApplicationContext(), "Please Check internet");
                break;


        }
    }


    public void addToCalendar() {

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Boolean isData = dbHelper.checkEventData(mEventId);

        if (!isData) {
            Log.d("isData", String.valueOf(isData));
            boolean inserted = dbHelper.insetEventData(mEventId, mEventName, mTitle, mAddress, mImage, mOrganizer, mDetail, mDate, mStartTime);
            if (inserted) {
                Log.d("Data inseterted", "data chal gaya database mai");
                Message.toast(getApplicationContext(), "Added to calendar");
            } else {
                Log.d("Data inseterted", "panga haiga koi");

            }

        } else {
            Log.d("isData", String.valueOf(isData));
            Log.d("Data inseterted", "add krta ustad pehla hi");
            Message.toast(getApplicationContext(), "Already added");
        }
    }


    private void requestPass() {

        final ProgressDialog progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Sending Request..");
        progressDoalog.setCancelable(false);
        progressDoalog.show();


        SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
        String email = preferences.getString("email", "N/A");

        if (email != null && mEventId != null && mEventName != null && mDate != null && mStartTime != null) {
            Call<GeneralResponse> call = RetrofitClient.getInstance().getApi().requestGuestPass(email, mEventId, "0", mEventName, mDate, mStartTime);
            Log.d("Data", email + mEventId + mEventName + mDate + mStartTime);
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    GeneralResponse rs = response.body();
                    Log.d("RequestGuestPass", rs.getStatus() + rs.getMessage());
                    String status = rs.getStatus();
                    if (status.equals("200")) {
                        Message.toast(getApplicationContext(), rs.getMessage());
                        progressDoalog.dismiss();
                    }
                    if (status.equals("400")) {
                        Message.toast(getApplicationContext(), rs.getMessage());
                        progressDoalog.dismiss();
                    }
                    if (status.equals("401")) {
                        Message.toast(getApplicationContext(), rs.getMessage());
                        progressDoalog.dismiss();
                    }
                    if (status.equals("100")) {
                        Message.toast(getApplicationContext(), rs.getMessage());
                        progressDoalog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        // "Connection Timeout";
                        System.out.println("Connection Timeout");
                        progressDoalog.dismiss();
                    } else if (t instanceof IOException) {
                        // "Timeout";
                        System.out.println("Timeout");
                        progressDoalog.dismiss();
                    } else {
                        //Call was cancelled by user
                        if (call.isCanceled()) {
                            System.out.println("Call was cancelled forcefully");
                            progressDoalog.dismiss();
                        } else {
                            //Generic error handling
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                            progressDoalog.dismiss();
                        }
                    }

                }
            });
        }
    }








 /*   @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}
