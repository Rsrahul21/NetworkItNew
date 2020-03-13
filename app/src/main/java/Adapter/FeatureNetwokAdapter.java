package Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.networkit.R;

import java.util.ArrayList;

import Activity.NetworkGroupDetailsActivity;
import Constants.DBHelper;
import Constants.Message;
import Models.GroupData;

public class FeatureNetwokAdapter extends RecyclerView.Adapter<FeatureNetwokAdapter.FeatureViewHolder> {


    Context context;
    //Integer[] images;
    private final int limit = 6;


    ArrayList<GroupData> data;
    GroupData item;
    boolean isFavt = false;
    DBHelper dbHelper;

    public FeatureNetwokAdapter(Context context, ArrayList<GroupData> data) {
        this.context = context;
        //this.images = images;
        this.data = data;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feature_network_layout, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeatureViewHolder holder, int position) {

        item = data.get(position);

        holder.title.setText(item.getTitle());
        holder.bussiness.setText(item.getBussiness());
        Glide.with(context).load("https://techpyton.000webhostapp.com/NetworkIt/group_images/" + item.getImage()).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(holder.iv);

        //   holder.iv.setImageResource(images[position]);


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
        final String id = item.getId();
        final String group_id = item.getId();


        holder.ratingBar.setRating(Float.parseFloat(rating));
        holder.location.setText(address);

        final String[] group_images = item.getGroup_images();
        for (int i = 0; i < group_images.length; i++) {

            Log.d("images", group_images[i]);
        }


        //  dbHelper.insert(group_id, group_name, title, address, rating, image, isFavt);


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("title", title);
                Log.d("bussiness", bussiness);


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


     /*   Cursor cursor = dbHelper.getfavt();
        while (cursor.moveToNext()) {
            Log.d("data", cursor.getString(2));

              isFavt = Boolean.parseBoolean(cursor.getString(7));

            if (isFavt == true) {
                holder.favt.setImageResource(R.drawable.heart_fav);
            } else {
                holder.favt.setImageResource(R.drawable.heart);

            }
        }*/


    }


    @Override
    public int getItemCount() {

        if (data.size() > limit) {
            return limit;
        } else {
            return data.size();
        }
        // return data.size();
    }

    public void filterList(ArrayList<GroupData> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv, favt;
        TextView title, bussiness, location;
        RatingBar ratingBar;
        CardView container;


        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.txt_title);
            bussiness = itemView.findViewById(R.id.bussiness);
            location = itemView.findViewById(R.id.location);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            //favt = itemView.findViewById(R.id.favt);
            container = itemView.findViewById(R.id.container);


        }

        @Override
        public void onClick(View v) {

            /*switch (v.getId()) {
                case R.id.img:

                    break;
            }*/
        }
    }


}
