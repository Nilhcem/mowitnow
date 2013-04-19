package com.nilhcem.mowitnow.result;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.nilhcem.mowitnow.App;
import com.nilhcem.mowitnow.core.Compatibility;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;

/**
 * Displays a webview handling all the mower animation GUI.
 */
public final class ResultActivity extends Activity {
	private static final String JS_INTERFACE_NAME = "android";
	private static final String LAWN_URL = "file:///android_asset/web-gui/index.html";
	private static final int SPRITE_SIZE = 34;

	private WebView mLawnview;
	private InstructionsParser mInstructions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lockScreen();
		initInstructions();
		if (!isFinishing()) {
			initWebview();
			setContentView(mLawnview);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isFinishing()) {
			startAnimation();
		}
	}

	private void lockScreen() {
		int orientation;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		} else {
			orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		setRequestedOrientation(orientation);
	}

	private void initInstructions() {
		mInstructions = ((App) getApplication()).getInstructions();
		if (mInstructions == null) {
			finish();
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebview() {
		mLawnview = new WebView(this);
		MowerAnimator animator = new MowerAnimator(this, mLawnview, mInstructions);

		mLawnview.addJavascriptInterface(animator, JS_INTERFACE_NAME);
		mLawnview.setPadding(0, 0, 0, 0);
		mLawnview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		WebSettings settings = mLawnview.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
	}

	private void startAnimation() {
		mLawnview.setInitialScale(getPercentScale(mInstructions));
		mLawnview.loadUrl(LAWN_URL);
	}

	private int getPercentScale(InstructionsParser instrs) {
		int w = ((instrs.getField().getWidth() + 1) * SPRITE_SIZE);
		int width = Compatibility.getScreenDimensions(this).x;
		Double val = Double.valueOf(width) / Double.valueOf(w);
		val = val * 100d;
		return val.intValue();
	}
}
