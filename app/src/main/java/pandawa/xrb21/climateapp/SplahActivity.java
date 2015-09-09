package pandawa.xrb21.climateapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Starts the About Screen Activity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        }, 3000L);
    }
}
