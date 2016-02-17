package com.example.weather.utils;

import com.example.weather.MainActivity;

import SystemBarTint.SystemBarTintManager;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class SystemBartint {

	public static void setTranslucentStatus(boolean on,Activity activity) {
	        Window win = activity.getWindow();
	        WindowManager.LayoutParams winParams = win.getAttributes();
	        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
	        if (on) {
	            winParams.flags |= bits;
	        } else {
	            winParams.flags &= ~bits;
	        }
	        win.setAttributes(winParams);
	    }

	public static void setSystemBarTint(Activity acitivity) {
		// ³Á½þÊ½×´Ì¬À¸
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					SystemBartint.setTranslucentStatus(true, acitivity);
				}
				SystemBarTintManager tintManager = new SystemBarTintManager(acitivity);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintColor(Color.parseColor("#05B1FE"));
	}
}
