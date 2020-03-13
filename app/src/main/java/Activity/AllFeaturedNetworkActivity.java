package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import Adapter.AllFeaturedNetworkAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Response.EventDataResponse;
import Models.GroupData;
import Response.GroupDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFeaturedNetworkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GroupData> dataList;
    AllFeaturedNetworkAdapter groupAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_groups_all);
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
        featuredNetworkGroupData();
    }

    public void featuredNetworkGroupData() {


        Call<GroupDataResponse> call = RetrofitClient.getInstance().getApi().featuredGroupData();
        call.enqueue(new Callback<GroupDataResponse>() {
            @Override
            public void onResponse(Call<GroupDataResponse> call, Response<GroupDataResponse> response) {
                GroupDataResponse dr = response.body();
                dataList = dr.getData();
                Log.d("GroupDetailResponse", "" + dr.getStatus() + dr.getResponse());
                groupAdapter = new AllFeaturedNetworkAdapter(getApplicationContext(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(groupAdapter);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GroupDataResponse> call, Throwable t) {
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

                //do search operation here


                filterGroup(newText);
                return false;
            }
        });
        //   return super.onCreateOptionsMenu(menu);
        return true;

    }

    private void filterGroup(String text) {

        ArrayList<GroupData> filteredList = new ArrayList<>();
        for (GroupData item : dataList) {
            if (item.getBussiness().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (dataList != null)
            groupAdapter.filterList(filteredList);
        else
            Message.toast(getApplicationContext(), "no data");
    }


/*    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}
