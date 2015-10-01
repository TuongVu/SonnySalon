package com.tvswitch.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;
import android.view.Window;

/**
 * Created by Tuong on 5/30/15.
 */
public class AnimationUtils {

    public static final void setupWindowAnimations(Window window){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Explode explode = new Explode();
        explode.setDuration(1000);
            window.setEnterTransition(explode);
            window.setExitTransition(explode);
//        Fade fade = new Fade();
            window.setReenterTransition(explode);
        }
    }

//    public static final Bundle getWindowsAnimationBundle(Activity activity){
//        Bundle bundle = null;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
//            bundle = options.toBundle();
//        }
//        return bundle;
//    }

    public static final void startWindowsAnimationBundle(Activity activity, Intent intent){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
            activity.startActivity(intent, options.toBundle());
        }else {
            activity.startActivity(intent);
        }
    }
}
