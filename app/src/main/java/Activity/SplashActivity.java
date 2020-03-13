package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.networkit.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        t1.start();


    }

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);

                SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
                String status = sharedPreferences.getString("status", "N/A");
                if (status.equals("login")) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                } else {

                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

}
