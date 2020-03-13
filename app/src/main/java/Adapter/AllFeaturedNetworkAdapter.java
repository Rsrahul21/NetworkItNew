package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.networkit.R;

import java.util.ArrayList;

import Activity.EventDetailsActivity;
import Activity.NetworkGroupDetailsActivity;
import Constants.DBHelper;
import Constants.Message;
import Models.EventData;
import Models.GroupData;
import de.hdodenhof.circleimageview.CircleImageView;

public class AllFeaturedNetworkAdapter extends RecyclerView.Adapter<AllFeaturedNetworkAdapter.FeatureViewHolder> {


    Context context;
    //  Integer[] images;

    ArrayList<GroupData> data;
    boolean isFavt = false;
    ;

    public AllFeaturedNetworkAdapter(Context context, ArrayList<GroupData> data) {
        this.context = context;
        //this.images = images;
        this.data = data;
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_feature_network_list_layout, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeatureViewHolder holder, int position) {
        //holder.iv.setImageResource(images[position]);
        GroupData item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.event_name.setText(item.getBussiness());
        // Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.iv);
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).into(holder.profileImage);


        final String group_id = item.getId();
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
        final String[] group_images = item.getGroup_images();
        final String id = item.getId();

        holder.ratingBar.setRating(Float.parseFloat(rating));
        holder.location.setText(address);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Message.toast(context,"item clicked");
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
                intent.putExtra("group_images", group_images);
                intent.putExtra("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

   /*     holder.favt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFavt == false) {

                    DBHelper dbHelper = new DBHelper(context);
                    //Boolean isInserted = dbHelper.insert(group_id, group_name, title, address, rating, image);


                    // if (isInserted) {
                    holder.favt.setImageResource(R.drawable.heart_fav);
                    Message.toast(context, "Added to favourite");
                    isFavt = true;
                    //       }
                } else {
                    holder.favt.setImageResource(R.drawable.heart);
                    Message.toast(context, "Removed from favourite");
                    isFavt = false;
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<GroupData> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {
        CardView iv;
        TextView title, event_name, location;
        CircleImageView profileImage;
        RatingBar ratingBar;
        ImageView favt;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            event_name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.txt_title);
            profileImage = itemView.findViewById(R.id.image);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            location = itemView.findViewById(R.id.location);
            favt = itemView.findViewById(R.id.favt);
        }

    }
}
