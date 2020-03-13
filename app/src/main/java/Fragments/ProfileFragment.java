package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Activity.EditProfileActivity;
import Activity.HomeActivity;
import Activity.RegisterActivity;
import Activity.SignInActivity;
import Api.RetrofitClient;
import Constants.Device;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.networkit.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

import Response.GetDetailResponse;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView name, email, bussiness, contact, city, address;
    ImageView edit;
    Intent intent;
    Button btnLogout;

    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        bussiness = view.findViewById(R.id.bussiness);
        contact = view.findViewById(R.id.contact);
        city = view.findViewById(R.id.city);
        address = view.findViewById(R.id.address);
        edit = view.findViewById(R.id.edit);
        btnLogout = view.findViewById(R.id.btn_logout);


        if (!Device.isNetworkConnected(getActivity())) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("detail", MODE_PRIVATE);
            final String mEmail = sharedPreferences.getString("email", "N/A");
            final String mContact = sharedPreferences.getString("contact", "N/A");
            final String mName = sharedPreferences.getString("name", "N/A");
            final String mBussiness = sharedPreferences.getString("bussiness", "N/A");
            final String mCity = sharedPreferences.getString("city", "N/A");
            final String mAddress = sharedPreferences.getString("address", "N/A");

            email.setText(mEmail);
            contact.setText(mContact);
            name.setText(mName);
            city.setText(mCity);
            bussiness.setText(mBussiness);
            address.setText(mAddress);
        } else {

            getUserDetail();
        }
        edit.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        {
            //edit the texts
            if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {


                email.setText(data.getStringExtra("email"));
                contact.setText(data.getStringExtra("contact"));
                name.setText(data.getStringExtra("name"));
                city.setText(data.getStringExtra("city"));
                bussiness.setText(data.getStringExtra("bussiness"));
                address.setText(data.getStringExtra("address"));
            }

        }
    }

    public void getUserDetail() {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth", MODE_PRIVATE);
        final String userEmail = sharedPreferences.getString("email", "N/A");

        Call<GetDetailResponse> call = RetrofitClient.getInstance().getApi().getDetail(userEmail);
        call.enqueue(new Callback<GetDetailResponse>() {
            @Override
            public void onResponse(Call<GetDetailResponse> call, Response<GetDetailResponse> response) {
                GetDetailResponse getDetailResponse = response.body();
                Log.d("profile", getDetailResponse.getStatus() + getDetailResponse.getAddress() + getDetailResponse.getCity() + getDetailResponse.getName());
                if (getDetailResponse.getStatus().equals("1")) {
                    email.setText(getDetailResponse.getEmail());
                    contact.setText(getDetailResponse.getPhone());
                    name.setText(getDetailResponse.getName());
                    city.setText(getDetailResponse.getCity());
                    bussiness.setText(getDetailResponse.getBussiness());
                    address.setText(getDetailResponse.getAddress());


                }


            }

            @Override
            public void onFailure(Call<GetDetailResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    // "Connection Timeout";
                    System.out.println("Connection Timeout");

                }
                else if (t instanceof IOException)
                {
                    // "Timeout";
                    System.out.println("Timeout");

                }
                else
                {
                    //Call was cancelled by user
                    if(call.isCanceled())

                    {
                        System.out.println("Call was cancelled forcefully");

                    }
                    else
                    {
                        //Generic error handling
                        System.out.println("Network Error :: " + t.getLocalizedMessage());
                    }
                }
            }
        });
    }


    public void logout() {
        SharedPreferences preferences = getActivity().getSharedPreferences("auth", 0);
        preferences.edit().remove("email").commit();
        preferences.edit().remove("password").commit();
        preferences.edit().remove("status").commit();
        startActivity(new Intent(getActivity(), SignInActivity.class));
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                logout();
                break;
            case R.id.edit:
                intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("email", email.getText());
                intent.putExtra("contact", contact.getText());
                intent.putExtra("name", name.getText());
                intent.putExtra("city", city.getText());
                intent.putExtra("bussiness", bussiness.getText());
                intent.putExtra("address", address.getText());
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }
}
