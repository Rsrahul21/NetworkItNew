package Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Adapter.CalendarEventsListAdapter;
import Constants.DBHelper;
import Models.CalendarEventData;

public class CalendarFragment extends Fragment {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-YYYY", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        recyclerView = view.findViewById(R.id.recycler_calendar_events);

        toolbar = view.findViewById(R.id.toolbar_meetings);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
       // activity.getSupportActionBar().setTitle("CALENDAR");


        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        String[] dayColumnNames = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setDayColumnNames(dayColumnNames);
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));


            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        getEventData();

    }

    public void getEventData() {

        DBHelper dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.getEventData();
        ArrayList<CalendarEventData> data = new ArrayList<>();

        while (cursor.moveToNext()) {

            CalendarEventData item = new CalendarEventData();
            item.setEvent_name(cursor.getString(2));
            item.setDate(cursor.getString(7));
            item.setStart_time(cursor.getString(8));
            data.add(item);
        }

        CalendarEventsListAdapter adapter = new CalendarEventsListAdapter(data, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_meeting_calendar, menu);
        MenuItem item = menu.findItem(R.id.menuSearchMeeting);
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

                return false;
            }
        });


    }


}
