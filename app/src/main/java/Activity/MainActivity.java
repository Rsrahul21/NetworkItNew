package Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.networkit.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Adapter.CalendarEventsListAdapter;
import Constants.DBHelper;
import Models.CalendarEventData;
import Models.GroupData;

public class MainActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-YYYY", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    Toolbar toolbar;

    CalendarEventsListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CalendarEventData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_meetings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
       /* toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });*/
/*
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        String[] dayColumnNames = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setDayColumnNames(dayColumnNames);
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));


        //set an event

        /*Event ev1 = new Event(Color.parseColor("#63e07b"), 1580186822743L, "social");
        compactCalendarView.addEvent(ev1);
        Event ev2 = new Event(Color.parseColor("#63e07b"), 1433704251000L);
        compactCalendarView.addEvent(ev2);*/

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        initViews();
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
                //countryAdapter.getFilter().filter(newText);

                filterCalendarEvent(newText);

                return false;
            }
        });
        //   return super.onCreateOptionsMenu(menu);
        return true;

    }


    public void initViews() {

        recyclerView = findViewById(R.id.recycler_calendar_events);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEventData();
    }

    @SuppressLint("NewApi")
    public void getEventData() {

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getEventData();
        data = new ArrayList<>();

        while (cursor.moveToNext()) {


            Log.d("Total",""+cursor.getCount());
            String eventdate = cursor.getString(7);
            String time = cursor.getString(8);
            Log.d("time", "" + "" + time);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String gmt = String.valueOf(GetUnixTime());
            Log.d("gmt", "" + "" + gmt);
            Date date = null;
            try {
                date = format.parse(eventdate);


            } catch (ParseException e) {
                e.printStackTrace();
            }


            String dayOfTheWeek = (String) DateFormat.format("EEE", date);
            Log.d("dayOfTheWeek", "" + dayOfTheWeek);
            String day = (String) DateFormat.format("dd", date);
            Log.d("day", "" + day);
            String monthString = (String) DateFormat.format("MMM", date);
            Log.d("monthString", "" + monthString);
            String year = (String) DateFormat.format("yyyy", date);
            Log.d("year", "" + year);

            String monthNumber = (String) DateFormat.format("MM", date);

            Log.d("Date", dayOfTheWeek + day + monthString + year);


            String givenDateString = dayOfTheWeek + " " + monthString + " " + day + " " + time + " " + "GMT+5:30" + " " + year;
          /*  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dateString, Locale.ROOT);
            LocalDateTime localDate = LocalDateTime.parse(dateString, formatter);
            long timeInMilliseconds = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
            Log.d("TAG", "Date in milli :: FOR API >= 26 >>> " + timeInMilliseconds);*/




         /*   DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "EEE MMM dd HH:mm:ss OOOO yyyy", Locale.ROOT);
           // String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
            long timeInMilliseconds = OffsetDateTime.parse(givenDateString, formatter)
                    .toInstant()
                    .toEpochMilli();
            System.out.println("Date in milli :: USING ThreeTenABP >>> " + timeInMilliseconds);*/

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

            try {
                Date mDate = sdf.parse(givenDateString);
                long timeInMilliseconds = mDate.getTime();


                if (cursor.getPosition() % 2 == 0) {
                    Event events = new Event(Color.parseColor("#5286df"), timeInMilliseconds);

                    compactCalendarView.addEvent(events);
                } else {
                    Event events = new Event(Color.parseColor("#63e07b"), timeInMilliseconds);
                    compactCalendarView.addEvent(events);
                }
                System.out.println("Date in milli :: " + timeInMilliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            CalendarEventData item = new CalendarEventData();
            item.setEvent_name(cursor.getString(2));
            item.setDate(eventdate);
            item.setStart_time(time);
            data.add(item);


        }

        adapter = new CalendarEventsListAdapter(data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void filterCalendarEvent(String text) {

        ArrayList<CalendarEventData> filteredList = new ArrayList<>();
        for (CalendarEventData item : data) {
            if (item.getEvent_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (data != null)
            adapter.filterList(filteredList);
    }


    public int GetUnixTime() {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        int utc = (int) (now / 1000);
        return (utc);

    }
}
