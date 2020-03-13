package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import java.util.ArrayList;

import Models.MeetingData;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {

    ArrayList<MeetingData> data;
    Context context;

    public MeetingAdapter(Context context, ArrayList<MeetingData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meeting_list_layout, parent, false);
        return new MeetingAdapter.MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

        MeetingData item = data.get(position);

        holder.group_name.setText(item.getGroup_name());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder {


        TextView group_name, date, time;
        ImageView image;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            group_name = itemView.findViewById(R.id.group_name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.img);
        }
    }
}
