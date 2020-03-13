package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import Adapter.FeatureEventAdapter;
import Adapter.FeatureNetwokAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Models.EventData;
import Response.EventDataResponse;
import Models.GroupData;
import Response.GroupDataResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};

    LinearLayout event, meeting, network;
    TextView viewAllGroups, viewAllEvents;

    RecyclerView recyclerView, recyclerView1;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        // Window window = getWindow();

        setContentView(R.layout.activity_search);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_search);
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

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_statusbar_color));
        }*/

        recyclerView = findViewById(R.id.feature_recycler);
        recyclerView1 = findViewById(R.id.feature_event_recycler);

        //FeatureNetwokAdapter featureNetwokAdapter = new FeatureNetwokAdapter(this, IMAGES);
        /*recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(featureNetwokAdapter);*/

        /*FeatureEventAdapter featureEventAdapter = new FeatureEventAdapter(this, IMAGES);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(featureEventAdapter);*/

        initView();


        recyclerView1.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                startActivity(new Intent(getApplicationContext(), NetworkGroupDetailsActivity.class));

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    public void initView() {

        event = findViewById(R.id.event);
        meeting = findViewById(R.id.meeting);
        network = findViewById(R.id.network);
        viewAllGroups = findViewById(R.id.view_all);
        viewAllEvents = findViewById(R.id.view_all_events);
        meeting.setOnClickListener(this);
        event.setOnClickListener(this);
        network.setOnClickListener(this);
        viewAllGroups.setOnClickListener(this);
        viewAllEvents.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(new Intent(getApplicationContext(), ExploreActivity.class));
        String itemSelected;
        switch (v.getId()) {
            case R.id.event:
                itemSelected = "event";
                intent.putExtra("item", itemSelected);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.meeting:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Message.toast(this, "Under Construction");
                break;
            case R.id.network:
                itemSelected = "network";
                intent.putExtra("item", itemSelected);
                startActivity(intent);
                break;
            case R.id.view_all:
                startActivity(new Intent(getApplicationContext(), AllFeaturedNetworkActivity.class));
                break;
            case R.id.view_all_events:
                startActivity(new Intent(getApplicationContext(), AllFeaturedEventActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        featuredGroupData();
        // sendGroupJoinRequest();
        //joinedGroupData();

        featuredEventData();

    }

    public void featuredGroupData() {
       /* Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GroupDetailResponse", response);

            }
        };

        LoginRequest loginRequest = new LoginRequest(  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);
*/

        Call<GroupDataResponse> call = RetrofitClient.getInstance().getApi().featuredGroupData();
        call.enqueue(new Callback<GroupDataResponse>() {
            @Override
            public void onResponse(Call<GroupDataResponse> call, Response<GroupDataResponse> response) {
                GroupDataResponse dr = response.body();
                ArrayList<GroupData> dataList = dr.getData();
                Log.d("GroupDetailResponse", "" + dr.getStatus() + dr.getResponse());

/*
                for (GroupData data : dataList) {

                    Log.d("Group name", data.getGroup_name());
                    Log.d("Group id", data.getId());
                    Log.d("Group title", data.getTitle());
                    Log.d("address", data.getAddress());
                    Log.d("status", data.getStatus());
                    Log.d("rating", data.getRating());
                    Log.d("organizer", data.getOrganizer());
                    Log.d("meeting time", data.getMeeting_time());
                    Log.d("meeting days", data.getMeeting_days());
                    Log.d("bussiness", data.getBussiness());

                    *//*String[] images = data.getImage();
                    for (int i = 0; i < images.length; i++) {
                        Log.d("images", images[i]);
                    }*//*
                }*/

                FeatureNetwokAdapter featureNetwokAdapter = new FeatureNetwokAdapter(getApplicationContext(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(featureNetwokAdapter);

            }

            @Override
            public void onFailure(Call<GroupDataResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    // "Connection Timeout";
                    System.out.println("Connection Timeout");
                } else if (t instanceof IOException) {
                    // "Timeout";
                    System.out.println("Timeout");
                } else {
                    //Call was cancelled by user
                    if (call.isCanceled()) {
                        System.out.println("Call was cancelled forcefully");
                    } else {
                        //Generic error handling
                        System.out.println("Network Error :: " + t.getLocalizedMessage());
                    }
                }
            }
        });

     /*   Call<ResponseBody> call = RetrofitClient.getInstance().getApi().groupData();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String sResponse = null;
                try {
                    sResponse = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("GroupDetailResponse", sResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/
    }


    public void joinedGroupData() {
       /* Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GroupDetailResponse", response);

            }
        };

        LoginRequest loginRequest = new LoginRequest(  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);
*/
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().groupJoined("rahul@gmail.com");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String sResponse = null;
                try {
                    sResponse = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("joinGroupDataResponse", sResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("joinGroupDataResponse", "failed");
            }
        });


    }


    public void featuredEventData() {

        Call<EventDataResponse> call = RetrofitClient.getInstance().getApi().featuredEventData();
        call.enqueue(new Callback<EventDataResponse>() {
            @Override
            public void onResponse(Call<EventDataResponse> call, Response<EventDataResponse> response) {
                EventDataResponse eventData = response.body();
                ArrayList<EventData> dataList = eventData.getData();
                Log.d("EventdataResponse", eventData.getResponse() + eventData.getStatus());
                FeatureEventAdapter featureEventAdapter = new FeatureEventAdapter(getApplicationContext(), dataList);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView1.setAdapter(featureEventAdapter);

            }

            @Override
            public void onFailure(Call<EventDataResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //do search operation here
                //countryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //   return super.onCreateOptionsMenu(menu);
        return true;

    }

}
