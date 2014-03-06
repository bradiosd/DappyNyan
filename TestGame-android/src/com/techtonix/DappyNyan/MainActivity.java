package com.techtonix.DappyNyan;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.apptonix.TestGame.TestGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AndroidApplication {
	
	private AdView adView;
	private String adMobID = "ca-app-pub-8698417695749351/1445474828";
	
	private String testDeviceID = "26D76D4DC6A083CEE333293DD7E7B6E5";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	RelativeLayout layout = new RelativeLayout(this);
    	
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    	
    	adView = new AdView(this);
    	adView.setAdUnitId(adMobID);
    	adView.setAdSize(AdSize.SMART_BANNER);

    	AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
    	
        View gameView = initializeForView(new TestGame(), false);

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        RelativeLayout.LayoutParams adParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        adView.loadAd(new AdRequest.Builder()
        	.addTestDevice(testDeviceID)
        	.build());

        layout.addView(gameView, adParams);
        layout.addView(adView, adParams1);

        setContentView(layout);
    }
    
    @Override
    public void onPause() {
      adView.pause();
      super.onPause();
    }

    @Override
    public void onResume() {
      super.onResume();
      adView.resume();
    }

    @Override
    public void onDestroy() {
      adView.destroy();
      super.onDestroy();
    }
    
}