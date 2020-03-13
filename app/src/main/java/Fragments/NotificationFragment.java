package Fragments;

import android.content.SharedPreferences;
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

import Adapter.NotificationAdapter;
import Api.RetrofitClient;
import Constants.Device;
import Constants.Message;
import Models.ApprovedEventData;
import Response.ApprovedEventResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notification_recycler);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Device.isNetworkConnected(getActivity()))
            getApprovedEvents();
        else
            Message.toast(getActivity(), "Please Check internet");
    }

    private void getApprovedEvents() {

        SharedPreferences preferences = getActivity().getSharedPreferences("auth", MODE_PRIVATE);
        String email = preferences.getString("email", "N/A");

        if (email != null) {
            Call<ApprovedEventResponse> call = RetrofitClient.getInstance().getApi().getApprovedEventList(email);
            call.enqueue(new Callback<ApprovedEventResponse>() {
                @Override
                public void onResponse(Call<ApprovedEventResponse> call, Response<ApprovedEventResponse> response) {
                    ApprovedEventResponse rs = response.body();

                    ArrayList<ApprovedEventData> dataList = rs.getData();
                    if (dataList != null) {
                    /*    for (int i = 0; i < dataList.size(); i++) {
                            Log.d("ApprovedEventResponse", dataList.get(i).getEvent_id());

                        }*/
                        adapter = new NotificationAdapter(dataList, getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);


                    } else {
                        //Message.toast(getActivity(), "No Notification yet");
                    }
                }

                @Override
                public void onFailure(Call<ApprovedEventResponse> call, Throwable t) {
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
}
