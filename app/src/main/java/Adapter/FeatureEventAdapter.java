package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.networkit.R;

import java.util.ArrayList;

import Activity.EventDetailsActivity;
import Models.EventData;
import Models.GroupData;

public class FeatureEventAdapter extends RecyclerView.Adapter<FeatureEventAdapter.FeatureViewHolder> {


    Context context;
    //Integer[] images;
    ArrayList<EventData> data;

    private final int limit = 6;

    public FeatureEventAdapter(Context context, ArrayList<EventData> data) {
        this.context = context;
        //this.images = images;
        this.data = data;
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feature_event_layout, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {

        //  holder.iv.setImageResource(images[position]);

        EventData item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.event_name.setText(item.getEvent_name());
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(holder.iv);

        final String title = item.getTitle();
        final String event_name = item.getEvent_name();
        final String detail = item.getDetail();
        final String address = item.getAddress();
        final String date = item.getDate();
        final String start_time = item.getStart_time();
        final String end_time = item.getEnd_time();
        final String organizer = item.getOrganizer();
        final String image = item.getImage();
        final String[] event_images = item.getEvent_images();
        final String event_id = item.getId();

       holder.date.setText(date);
        holder.time.setText(start_time);
        holder.location.setText(address);


        holder.event_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("event_name", event_name);
                intent.putExtra("detail", detail);
                intent.putExtra("address", address);
                intent.putExtra("date", date);
                intent.putExtra("start_time", start_time);
                intent.putExtra("end_time", end_time);
                intent.putExtra("organizer", organizer);
                intent.putExtra("image", image);
                intent.putExtra("event_images", event_images);
                intent.putExtra("event_id", event_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        // return images.length;

        if (data.size() > limit) {
            return limit;
        } else {
            return data.size();
        }
        //return data.size();
    }


    public void filterList(ArrayList<EventData> filteredList){
        data= filteredList;
        notifyDataSetChanged();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv;
        TextView title, event_name, date, time, location;
        CardView event_container;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            event_name = itemView.findViewById(R.id.event_name);

            event_container = itemView.findViewById(R.id.event_container);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.img:
                    //    context.startActivity(new Intent(context, NetworkGroupDetailsActivity.class));
                    break;
            }
        }
    }
}



