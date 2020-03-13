package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.SlidingNetworkImage_Adapter;
import Api.RetrofitClient;
import Constants.Message;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkGroupDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static ViewPager mPager;
    //private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};
    //private static int[] IMAGES = null;
    private static String[] IMAGES = null;
    private ArrayList<String> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    TextView group_name, bussiness, title, organizer, detail, meeting_days, meeting_time, address;
    CircleImageView profileImage;
    RatingBar ratingBar;
    Button btnJoinGroup;


    String mBussiness;
    String mTitle;
    String mGroupName;
    //String[] groupImages = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
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
                    intent.putExtra("item", "network");
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });
        init();

        //  getData();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {


        group_name = findViewById(R.id.group_name);
        bussiness = findViewById(R.id.bussiness);
        title = findViewById(R.id.name);
        organizer = findViewById(R.id.organizer);
        detail = findViewById(R.id.detail);
        profileImage = findViewById(R.id.image);
        ratingBar = findViewById(R.id.ratingBar);
        meeting_days = findViewById(R.id.meeting_days);
        meeting_time = findViewById(R.id.meeting_time);
        address = findViewById(R.id.address);
        btnJoinGroup = findViewById(R.id.join_group);

        btnJoinGroup.setOnClickListener(this);


     /*   groupImages = getIntent().getStringArrayExtra("group_images");

        for(int i=0;i<groupImages.length;i++){
            IMAGES[i]=Integer.parseInt(groupImages[i]);
        }*/


   /*     for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = findViewById(R.id.img);


        mPager.setAdapter(new SlidingNetworkImage_Adapter(NetworkGroupDetailsActivity.this, ImagesArray));


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
        }, 3000, 3000);*/

    /*    // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        //  getGroupImages();
    }

    public void getData() {

       /* intent.putExtra("group_name",group_name);
        intent.putExtra("bussiness",bussiness);
        intent.putExtra("title",title);
        intent.putExtra("rating",rating);
        intent.putExtra("address",address);
        intent.putExtra("orgaizer",organizer);
        intent.putExtra("meeting_days",meeting_days);
        intent.putExtra("image",image);*/


        mGroupName = getIntent().getStringExtra("group_name");
        mBussiness = getIntent().getExtras().getString("bussiness");
        mTitle = getIntent().getStringExtra("title");
        String mRating = getIntent().getStringExtra("rating");
        String mAddress = getIntent().getStringExtra("address");
        String mOrganizer = getIntent().getStringExtra("orgaizer");
        String mMeetingDays = getIntent().getStringExtra("meeting_days");
        String mDetail = getIntent().getStringExtra("detail");
        String mImage = getIntent().getStringExtra("image");
        String mMeetingTime = getIntent().getStringExtra("meeting_time");
        // mGroupImages= getIntent().getStringArrayExtra("group_images");


        group_name.setText(mGroupName);
        bussiness.setText(mBussiness);
        title.setText(mTitle);
        organizer.setText(mOrganizer);
        detail.setText(mDetail);
        ratingBar.setRating(Float.parseFloat(mRating));
        meeting_days.setText(mMeetingDays);
        meeting_time.setText(mMeetingTime);
        address.setText(mAddress);

        Glide.with(this).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + mImage).into(profileImage);

    }


   /* public void getGroupImages() {

        IMAGES = getIntent().getStringArrayExtra("group_images");
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = findViewById(R.id.img);


        mPager.setAdapter(new SlidingNetworkImage_Adapter(NetworkGroupDetailsActivity.this, ImagesArray));


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
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_group:
                sendGroupJoinRequest();
                break;

        }
    }

 /*   @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/


    public void sendGroupJoinRequest() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Request....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("detail", MODE_PRIVATE);


        String group_id = getIntent().getStringExtra("id");
        String email = sharedPreferences.getString("email", "N/A");
        String contact = sharedPreferences.getString("contact", "N/A");
        String name = sharedPreferences.getString("name", "N/A");

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().joinGroup(name, email, contact, "2019-10-05", "03:03:03", group_id, "0", mTitle, mGroupName, mBussiness, "2019-10-04", "03:03:00");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String sResponse = null;
                try {
                    sResponse = response.body().string();
                    JSONObject jsonObject = new JSONObject(sResponse);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("response");

                    if (status.equals("1") && message.equals("Success")) {
                        progressDialog.dismiss();
                        Message.toast(getApplicationContext(), "Your Request Sent Successfully....wait for approval");
                    }
                    if (status.equals("400")) {
                        progressDialog.dismiss();
                        Message.toast(getApplicationContext(), "You already sent request");


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("GroupJoinRequest", sResponse);


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