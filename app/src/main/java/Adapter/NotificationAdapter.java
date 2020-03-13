package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.util.ArrayList;

import Models.ApprovedEventData;
import Response.ApprovedEventResponse;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    ArrayList<ApprovedEventData> data;
    Context context;

    public NotificationAdapter(ArrayList<ApprovedEventData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_list_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        ApprovedEventData item = data.get(position);
        holder.event_name.setText(item.getEvent_name());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView event_name, date, time,request;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            request=itemView.findViewById(R.id.request);

        }
    }
}
