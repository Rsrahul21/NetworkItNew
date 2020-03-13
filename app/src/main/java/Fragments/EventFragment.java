package Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import Adapter.EventAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Models.EventData;
import Response.EventDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {

    RecyclerView recyclerView;
    private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};
    ArrayList<EventData> dataList;
    EventAdapter eventAdapter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.explore_recycler);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EventAdapter exploreAdapter = new EventAdapter(getActivity(), IMAGES);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(exploreAdapter);*/


    }

    @Override
    public void onResume() {
        super.onResume();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        eventData();
    }

    public void eventData() {

        Call<EventDataResponse> call = RetrofitClient.getInstance().getApi().eventData();
        call.enqueue(new Callback<EventDataResponse>() {
            @Override
            public void onResponse(Call<EventDataResponse> call, Response<EventDataResponse> response) {
                EventDataResponse eventData = response.body();
                dataList = eventData.getData();
                Log.d("EventdataResponse", eventData.getResponse() + eventData.getStatus());
                eventAdapter = new EventAdapter(getActivity(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(eventAdapter);
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


    public void filterEvent(String text) {

        ArrayList<EventData> filteredList = new ArrayList<>();
        for (EventData item : dataList) {
            if (item.getEvent_name().toLowerCase().contains(text.toLowerCase())) {

                filteredList.add(item);

            }
        }

        if (dataList != null)
            eventAdapter.filterList(filteredList);
        else
            Message.toast(getActivity(), "No Data Available");
    }
}
