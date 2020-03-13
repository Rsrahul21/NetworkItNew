package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.util.ArrayList;

import Models.CalendarEventData;
import Models.EventData;

import static com.example.networkit.R.drawable.round_button_bg1;

public class CalendarEventsListAdapter extends RecyclerView.Adapter<CalendarEventsListAdapter.EventViewHolder> {


    ArrayList<CalendarEventData> data;
    Context context;

    public CalendarEventsListAdapter(ArrayList<CalendarEventData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_event_list_layout, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        CalendarEventData item = data.get(position);


        if (position % 2 == 0) {

            holder.btnIndicator.setBackgroundResource(R.drawable.round_button_bg1);
            holder.btnTime.setBackgroundResource(R.drawable.round_button_bg1);


        } else {
            holder.btnIndicator.setBackgroundResource(R.drawable.round_button_bg);
            holder.btnTime.setBackgroundResource(R.drawable.round_button_bg);
        }

        holder.btnTime.setText(item.getStart_time());
        holder.date.setText(item.getDate());
        holder.event_name.setText(item.getEvent_name());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<CalendarEventData> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        Button btnTime, btnIndicator;
        TextView date, event_name;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            btnTime = itemView.findViewById(R.id.btn_time);
            btnIndicator = itemView.findViewById(R.id.btn_indicator);
            date = itemView.findViewById(R.id.date);
            event_name = itemView.findViewById(R.id.event_name);
        }
    }
}
