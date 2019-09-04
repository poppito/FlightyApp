package io.embry.flighty.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.embry.flighty.R;
import io.embry.flighty.util.AnimationUtil;

public class LauncherActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;

    //animation duration for logo animation
    private static final int ANIMATION_DURATION = 500;

    //launch main activity
    private static final int LAUNCH_DELAY = 600;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        AnimationUtil.scaleUpAnimation(logo, ANIMATION_DURATION, 2.0f, 2.0f);
        AnimationUtil.translateAnimation(logo, ANIMATION_DURATION, -150f, 1000f);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                        LauncherActivity.this.finish();
                    }
                });
            }
        }, LAUNCH_DELAY);
    }
}