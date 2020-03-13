package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import java.util.ArrayList;

import Activity.NetworkGroupDetailsActivity;
import Constants.Message;
import Models.FavoriteListModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder> {


    Context context;
    ArrayList<FavoriteListModel> data;

    public FavoriteListAdapter(Context context, ArrayList<FavoriteListModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fav_list_layout, parent, false);
        return new FavoriteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder, int position) {

        FavoriteListModel item = data.get(position);
        holder.title.setText(item.getTitle());
        //  Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.iv);
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.profileImage);

        final String title = item.getTitle();
        final String bussiness = item.getBussiness();
        final String group_name = item.getGroup_name();
        final String rating = item.getRating();
        final String address = item.getAddress();
        final String organizer = item.getOrganizer();
        final String meeting_days = item.getMeeting_days();
        final String image = item.getImage();
        final String detail = item.getDetail();
        final String meeting_time = item.getMeeting_time();
        final String group_id = item.getId();
        //  final String[] group_images = item.getGroup_images();

        holder.ratingBar.setRating(Float.parseFloat(rating));
        holder.location.setText(address);
        holder.group_name.setText(group_name);


        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message.toast(context, "item clicked");
                Intent intent = new Intent(context, NetworkGroupDetailsActivity.class);
                intent.putExtra("group_name", group_name);
                intent.putExtra("bussiness", bussiness);
                intent.putExtra("title", title);
                intent.putExtra("rating", rating);
                intent.putExtra("address", address);
                intent.putExtra("orgaizer", organizer);
                intent.putExtra("meeting_days", meeting_days);
                intent.putExtra("image", image);
                intent.putExtra("detail", detail);
                intent.putExtra("meeting_time", meeting_time);
                // intent.putExtra("group_images", group_images);
                intent.putExtra("id", group_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FavoriteListViewHolder extends RecyclerView.ViewHolder {

        CardView iv;
        TextView title, group_name, location;
        CircleImageView profileImage;
        RatingBar ratingBar;
        ImageView favt;

        public FavoriteListViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            group_name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.txt_title);
            profileImage = itemView.findViewById(R.id.image);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            location = itemView.findViewById(R.id.location);
            favt = itemView.findViewById(R.id.favt);
        }
    }
}
