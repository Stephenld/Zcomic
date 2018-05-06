package ldh.com.zcomic.ui;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import ldh.com.zcomic.MainActivity;
import ldh.com.zcomic.R;

/**
 * Created by allen liu on 2018/5/2.
 */

public class SplashActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(3000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher_round); //or any other drawable
        configSplash.setAnimLogoSplashDuration(500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("My Comic App");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("fonts/volatire.ttf"); //provide string to your font located in assets/fonts/

    }
    /**
     * 监听动画完成事件
     */
    @Override
    public void animationsFinished() {
        //transit to another activity here
        //or do whatever you want
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
