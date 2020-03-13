package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import Adapter.AllFeaturedEventAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Models.EventData;
import Response.EventDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFeaturedEventActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<EventData> dataList;
    AllFeaturedEventAdapter eventAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_event);
        Toolbar toolbar = findViewById(R.id.toolbar_events);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });


        recyclerView = findViewById(R.id.feature_event_recycler);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        featuredEventData();

    }

    public void featuredEventData() {

        Call<EventDataResponse> call = RetrofitClient.getInstance().getApi().featuredEventData();
        call.enqueue(new Callback<EventDataResponse>() {
            @Override
            public void onResponse(Call<EventDataResponse> call, Response<EventDataResponse> response) {
                EventDataResponse eventData = response.body();
                dataList = eventData.getData();
                Log.d("EventdataResponse", eventData.getResponse() + eventData.getStatus());
                eventAdapter = new AllFeaturedEventAdapter(getApplicationContext(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(eventAdapter);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<EventDataResponse> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_meeting, menu);
        MenuItem item = menu.findItem(R.id.menuSearchMeeting);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //   adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterEvent(newText);
                return false;
            }
        });
        //   return super.onCreateOptionsMenu(menu);
        return true;

    }

    private void filterEvent(String text) {

        ArrayList<EventData> filteredList = new ArrayList<>();
        for (EventData item : dataList) {
            if (item.getEvent_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        eventAdapter.filterList(filteredList);
    }

  /*  @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}
