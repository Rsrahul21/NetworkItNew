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

import Adapter.NetworkGroupAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Response.EventDataResponse;
import Models.GroupData;
import Response.GroupDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkFragment extends Fragment {

    RecyclerView recyclerView;
    private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};
    ArrayList<GroupData> dataList;
    NetworkGroupAdapter groupAdapter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.network_fragment_layout, container, false);

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
        networkGroupData();
    }

    public void networkGroupData() {


        Call<GroupDataResponse> call = RetrofitClient.getInstance().getApi().groupData();
        call.enqueue(new Callback<GroupDataResponse>() {
            @Override
            public void onResponse(Call<GroupDataResponse> call, Response<GroupDataResponse> response) {
                GroupDataResponse dr = response.body();
                dataList = dr.getData();
                Log.d("GroupDetailResponse", "" + dr.getStatus() + dr.getResponse());
                groupAdapter = new NetworkGroupAdapter(getActivity(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(groupAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GroupDataResponse> call, Throwable t) {
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


    public void filterGroup(String text) {

        ArrayList<GroupData> filteredList = new ArrayList<>();
        for (GroupData item : dataList) {
            if (item.getBussiness().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (groupAdapter != null)
            groupAdapter.filterList(filteredList);
    }
}
