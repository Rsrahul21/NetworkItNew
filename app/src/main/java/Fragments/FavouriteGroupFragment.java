package Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkit.R;

import java.util.ArrayList;

import Adapter.FavoriteListAdapter;
import Constants.DBHelper;
import Models.FavoriteListModel;

public class FavouriteGroupFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favt_list, container, false);
        recyclerView = view.findViewById(R.id.favt_group_recycler);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getFavtList();
    }

    public void getFavtList() {

        ArrayList<FavoriteListModel> data = new ArrayList<>();

        Cursor cursor = new DBHelper(getActivity()).getData();

        while (cursor.moveToNext()) {

            if (cursor.getString(6).equals("1")) {
                FavoriteListModel item = new FavoriteListModel();
                item.setId(cursor.getString(0));
                item.setGroup_name(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setAddress(cursor.getString(3));
                item.setRating(cursor.getString(4));
                item.setImage(cursor.getString(5));
                item.setBussiness(cursor.getString(7));
                item.setOrganizer(cursor.getString(8));
                item.setDetails(cursor.getString(9));
                item.setMeeting_days(cursor.getString(10));
                item.setMeeting_time(cursor.getString(11));
                data.add(item);
            }
        }

        FavoriteListAdapter favoriteListAdapter = new FavoriteListAdapter(getActivity(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteListAdapter);
    }
}
