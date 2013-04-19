package com.nilhcem.mowitnow.core;

import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Provides methods that work on all devices.
 */
public final class Compatibility {

	private Compatibility() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if current SDK is compatible with the desired API level.
	 *
	 * @param apiLevel the required API level.
	 * @return {@code true} if current OS is compatible.
	 */
	public static boolean isCompatible(int apiLevel) {
		return android.os.Build.VERSION.SDK_INT >= apiLevel;
	}

	/**
	 * Returns the screen dimensions in px.
	 *
	 * @param context context.
	 * @return the screen dimensions (width + height).
	 */
	@SuppressWarnings("deprecation")
	public static Point getScreenDimensions(Context context) {
		boolean oldWay = true;
		Point size = new Point();

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		if (Compatibility.isCompatible(Build.VERSION_CODES.HONEYCOMB_MR2)) {
			try {
				Method method = Display.class.getDeclaredMethod("getSize",
						new Class[] { Point.class });
				method.invoke(display, size);
				oldWay = false;
			} catch (Exception e) {
				oldWay = true;
			}
		}
		if (oldWay) {
			size.set(display.getWidth(), display.getHeight());
		}
		return size;
	}
}
