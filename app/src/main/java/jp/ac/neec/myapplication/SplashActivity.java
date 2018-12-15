package jp.ac.neec.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashActivity extends Activity {

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        ImageView imageView = (ImageView) findViewById(R.id.gifView);
        imageView.setImageResource(R.drawable.splash1);
        handler.postDelayed(new splashHandler(), 4000);
    }
    class splashHandler implements Runnable {
        public void run() {
            Intent inte = new Intent(getApplication(), SearchActivity.class);
            startActivity(inte);
            SplashActivity.this.finish();
        }
    }
}