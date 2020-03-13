package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.networkit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragments.CalendarFragment;
import Fragments.NotificationFragment;
import Fragments.FavouriteGroupFragment;
import Fragments.HomeFragment;
import Fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {


    Fragment homeFragment = new HomeFragment();
    Fragment calendarFragment = new CalendarFragment();
    Fragment profileFragment = new ProfileFragment();
    Fragment favouriteGroupFragment = new FavouriteGroupFragment();
    Fragment chatFragment = new NotificationFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = homeFragment;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager.beginTransaction().add(R.id.main_container, profileFragment, "5").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, chatFragment, "4").hide(chatFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, calendarFragment, "3").hide(calendarFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, favouriteGroupFragment, "2").hide(favouriteGroupFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_Home:

                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;

                case R.id.calendar:
                    fragmentManager.beginTransaction().hide(active).show(calendarFragment).commit();
                    active = calendarFragment;
                    return true;

                case R.id.profile:
                    fragmentManager.beginTransaction().hide(active).show(profileFragment).commit();
                    active = profileFragment;
                    return true;

                case R.id.chat:
                    fragmentManager.beginTransaction().hide(active).show(chatFragment).commit();
                    active = chatFragment;
                    return true;

                case R.id.favt_list:
                    fragmentManager.beginTransaction().hide(active).show(favouriteGroupFragment).commit();
                    active = favouriteGroupFragment;
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (navigationView.getSelectedItemId() == R.id.action_Home) {
            super.onBackPressed();
            finish();
        } else {
            navigationView.setSelectedItemId(R.id.action_Home);
        }
    }
}
