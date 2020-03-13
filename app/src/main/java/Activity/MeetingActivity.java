package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.networkit.R;

import java.util.ArrayList;

import Adapter.MeetingAdapter;
import Api.RetrofitClient;
import Models.MeetingData;
import Response.MeetingDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar_signin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

            }
        });

        recyclerView = findViewById(R.id.meeting_recylcer);

        meetingData();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void meetingData() {

        Call<MeetingDataResponse> call = RetrofitClient.getInstance().getApi().meetingData();
        call.enqueue(new Callback<MeetingDataResponse>() {
            @Override
            public void onResponse(Call<MeetingDataResponse> call, Response<MeetingDataResponse> response) {
                MeetingDataResponse meetingDataResponse = response.body();
                ArrayList<MeetingData> dataList = meetingDataResponse.getData();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                MeetingAdapter adapter = new MeetingAdapter(getApplicationContext(), dataList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MeetingDataResponse> call, Throwable t) {

            }
        });
    }
}
