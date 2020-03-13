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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import java.util.ArrayList;

import Activity.EventDetailsActivity;
import Models.EventData;
import Models.GroupData;
import de.hdodenhof.circleimageview.CircleImageView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.FeatureViewHolder> {


    Context context;
    //  Integer[] images;

    ArrayList<EventData> data;

    public EventAdapter(Context context, ArrayList<EventData> data) {
        this.context = context;
        //this.images = images;
        this.data = data;
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_list_layout_new, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        //holder.iv.setImageResource(images[position]);
        EventData item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.event_name.setText(item.getEvent_name());
//        holder.date.setText(item.getDate());
        // Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.iv);
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.profileImage);

        final String title = item.getTitle();
        final String event_name = item.getEvent_name();
        final String detail = item.getDetail();
        final String address = item.getAddress();
        final String date = item.getDate();
        final String start_time = item.getStart_time();
        final String end_time = item.getEnd_time();
        final String organizer = item.getOrganizer();
        final String image = item.getImage();
        final String event_id = item.getId();


        holder.organizer.setText(organizer);
        holder.address.setText(address);
        holder.date.setText(date);
        holder.time.setText(start_time);

        //final String[] event_images = item.getEvent_images();


        holder.iv.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("event_id", event_id);
                intent.putExtra("choice","1");
                //    intent.putExtra("event_images", event_images);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void filterList(ArrayList<EventData> filteredList){
        data= filteredList;
        notifyDataSetChanged();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {

        LinearLayout iv;
        TextView title, event_name, date, organizer, address, time;
        CircleImageView profileImage;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            event_name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            profileImage = itemView.findViewById(R.id.image);
            organizer = itemView.findViewById(R.id.organizer);
            address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
        }
    }
}
