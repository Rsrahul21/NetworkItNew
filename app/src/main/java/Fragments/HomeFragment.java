package Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import Activity.AllFeaturedEventActivity;
import Activity.AllFeaturedNetworkActivity;
import Activity.ExploreActivity;
import Activity.MainActivity;
import Adapter.FeatureEventAdapter;
import Adapter.FeatureNetwokAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Models.EventData;
import Models.GroupData;
import Response.GroupDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Response.EventDataResponse;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};

    LinearLayout event, meeting, network;
    TextView viewAllGroups, viewAllEvents;

    RecyclerView recyclerView, recyclerView1;
    EditText search;
    ArrayList<GroupData> groupDataList;
    FeatureNetwokAdapter featureNetwokAdapter;
    FeatureEventAdapter featureEventAdapter;
    ArrayList<EventData> eventDataList;
    // Toolbar toolbar;

    ProgressDialog progressDialog;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        recyclerView = view.findViewById(R.id.feature_recycler);
        recyclerView1 = view.findViewById(R.id.feature_event_recycler);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar_search);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");


        search = view.findViewById(R.id.search_edit_text);
        event = view.findViewById(R.id.event);
        meeting = view.findViewById(R.id.meeting);
        network = view.findViewById(R.id.network);
        viewAllGroups = view.findViewById(R.id.view_all);
        viewAllEvents = view.findViewById(R.id.view_all_events);
        meeting.setOnClickListener(this);
        event.setOnClickListener(this);
        network.setOnClickListener(this);
        viewAllGroups.setOnClickListener(this);
        viewAllEvents.setOnClickListener(this);


        searchItem();

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(new Intent(getActivity(), ExploreActivity.class));
        String itemSelected;
        switch (v.getId()) {
            case R.id.event:
                itemSelected = "event";
                intent.putExtra("item", itemSelected);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.meeting:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.network:
                itemSelected = "network";
                intent.putExtra("item", itemSelected);
                startActivity(intent);
                break;
            case R.id.view_all:
                startActivity(new Intent(getActivity(), AllFeaturedNetworkActivity.class));
                break;
            case R.id.view_all_events:
                startActivity(new Intent(getActivity(), AllFeaturedEventActivity.class));
                break;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();

        featuredGroupData();
        featuredEventData();

    }

    public void featuredGroupData() {


        Call<GroupDataResponse> call = RetrofitClient.getInstance().getApi().featuredGroupData();
        call.enqueue(new Callback<GroupDataResponse>() {
            @Override
            public void onResponse(Call<GroupDataResponse> call, Response<GroupDataResponse> response) {
                GroupDataResponse dr = response.body();
                groupDataList = dr.getData();
                Log.d("GroupDetailResponse", "" + dr.getStatus() + dr.getResponse());


                featureNetwokAdapter = new FeatureNetwokAdapter(getActivity(), groupDataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(featureNetwokAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GroupDataResponse> call, Throwable t) {
                if (t.toString().contains("SocketTimeoutException")) {

                    Message.toast(getActivity(), "Poor Internet Connection..");
                    progressDialog.dismiss();
                } else {
                    Message.toast(getActivity(), "Something went wrong..");
                    progressDialog.dismiss();
                }
            }
        });


    }


    public void featuredEventData() {

        Call<EventDataResponse> call = RetrofitClient.getInstance().getApi().featuredEventData();
        call.enqueue(new Callback<EventDataResponse>() {
            @Override
            public void onResponse(Call<EventDataResponse> call, Response<EventDataResponse> response) {
                EventDataResponse eventData = response.body();
                eventDataList = eventData.getData();
                Log.d("EventdataResponse", eventData.getResponse() + eventData.getStatus());
                featureEventAdapter = new FeatureEventAdapter(getActivity(), eventDataList);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView1.setAdapter(featureEventAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<EventDataResponse> call, Throwable t) {
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //do search operation here
                //countryAdapter.getFilter().filter(newText);
                filterGroup(newText);
                filterEvent(newText);
                return false;
            }
        });
    }


    //code to search the item


    public void searchItem() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //filter the text

                filterGroup(s.toString());
            }
        });
    }

    private void filterGroup(String text) {

        ArrayList<GroupData> filteredList = new ArrayList<>();
        for (GroupData item : groupDataList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        featureNetwokAdapter.filterList(filteredList);
    }

    private void filterEvent(String text) {

        ArrayList<EventData> filteredList = new ArrayList<>();
        for (EventData item : eventDataList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        featureEventAdapter.filterList(filteredList);
    }

}
