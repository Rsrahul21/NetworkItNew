package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import com.example.networkit.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import Adapter.EventAdapter;
import Api.RetrofitClient;
import Constants.Message;
import Fragments.EventFragment;
import Fragments.HomeFragment;
import Fragments.NetworkFragment;
import Models.EventData;
import Response.EventDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;

    int selectedTab, tabPosition;
    RecyclerView recyclerView;
    private static final Integer[] IMAGES = {R.drawable.group_three, R.drawable.sample, R.drawable.group_four, R.drawable.grp};

   /* Fragment fragment;
    FragmentManager manager;//create an instance of fragment manager
    FragmentTransaction transaction;*/

    final Fragment fragment1 = new EventFragment();
    final Fragment fragment2 = new NetworkFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);


        toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tabs);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();

            }
        });


        //initialize view for fragment
        tabLayout.addTab(tabLayout.newTab().setText("Events"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("Networks"), 1);
        final String itemSelected = getIntent().getStringExtra("item");

        if (itemSelected.equals("event")) {
            fm.beginTransaction().add(R.id.explore_container, fragment2, "2").hide(fragment2).commit();
            fm.beginTransaction().add(R.id.explore_container, fragment1, "1").commit();
            active = fragment1;
            tabLayout.getTabAt(0).select();


        } else {
            fm.beginTransaction().add(R.id.explore_container, fragment1, "1").hide(fragment1).commit();
            fm.beginTransaction().add(R.id.explore_container, fragment2, "2").commit();
            active = fragment2;
            tabLayout.getTabAt(1).select();


        }


/*
        manager = getSupportFragmentManager();//create an instance of fragment manager
        fragment = new EventFragment();
        transaction = manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.explore_container, fragment, "explore_frag");
        transaction.addToBackStack(null);
        transaction.commit();
        Toast.makeText(ExploreActivity.this, "tab1 selected", Toast.LENGTH_SHORT).show();*/


        recyclerView = findViewById(R.id.explore_recycler);


     /*   EventAdapter exploreAdapter = new EventAdapter(this, IMAGES);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exploreAdapter);*/


        selectedTab = tabLayout.getSelectedTabPosition();
        Log.d("selectedTab", "" + selectedTab);

        int tabCount = tabLayout.getTabCount();
        // tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabTextColors(Color.WHITE, Color.parseColor("#5cd7ad")); // set the tab text colors for the both states of the tab.
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#5cd7ad"));
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // set the behaviour mode for the tabs
        tabLayout.setTabMode(TabLayout.MODE_FIXED); // set the behaviour mode for the tabs
        //tabLayout.setBackgroundColor(Color.BLACK);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // called when tab selected


                Log.d("itemselected", "" + tab.getPosition());
                tabPosition = tab.getPosition();


                switch (tab.getPosition()) {


                    case 0:

                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;

              /*        fragment = new EventFragment();
                        transaction = manager.beginTransaction();//create an instance of Fragment-transaction
                        transaction.replace(R.id.explore_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();*/
                        // Toast.makeText(ExploreActivity.this, "tab1 selected", Toast.LENGTH_SHORT).show();
                        break;


                    case 1:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;


                        //  Toast.makeText(ExploreActivity.this, "tab2 selected", Toast.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // called when a tab is reselected
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
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

                if (tabPosition == 0 || selectedTab == 0)
                    ((EventFragment) fragment1).filterEvent(newText);


                if (tabPosition == 1 || selectedTab == 1)
                    ((NetworkFragment) fragment2).filterGroup(newText);


                return false;
            }
        });
        //   return super.onCreateOptionsMenu(menu);
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        //eventData();
        
    }

    public void eventData() {

        Call<EventDataResponse> call = RetrofitClient.getInstance().getApi().eventData();
        call.enqueue(new Callback<EventDataResponse>() {
            @Override
            public void onResponse(Call<EventDataResponse> call, Response<EventDataResponse> response) {
                EventDataResponse eventData = response.body();
                ArrayList<EventData> dataList = eventData.getData();
                Log.d("EventdataResponse", eventData.getResponse() + eventData.getStatus());
                EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(eventAdapter);

            }

            @Override
            public void onFailure(Call<EventDataResponse> call, Throwable t) {
                if (t.toString().contains("SocketTimeoutException")) {
                    // set your custom message here
                    //view.showToast(R.string.poor_internet_connection);
                    Message.toast(getApplicationContext(), "Poor Internet Connection..");
                } else {
                    Message.toast(getApplicationContext(), "Something went wrong..");
                }
            }
        });

    }

    /*@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
