package com.nilhcem.mowitnow.result;

import java.util.Locale;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;

import com.nilhcem.mowitnow.core.Coordinate;
import com.nilhcem.mowitnow.core.Field;
import com.nilhcem.mowitnow.core.Mower;
import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;

/**
 * Provides a Javascript interface to communicate with the webview in order to animate the mowers.
 */
final class MowerAnimator {
	private static final String TAG = "MowerAnimator";

	// Javascript methods to call
	private static final String JS_PREFIX = "javascript:";
	private static final String JS_INIT_LAWN = JS_PREFIX + "initLawn(%d,%d)";
	private static final String JS_INIT_MOWER = JS_PREFIX + "initMower(%d,%d,%d)";
	private static final String JS_MOVE_FORWARD = JS_PREFIX + "moveMower(%d,%d,%d,%d)";
	private static final String JS_ROTATE_MOWER = JS_PREFIX + "rotateMower(%d)";
	private static final String JS_APPEND_RESULT = JS_PREFIX + "appendResult('%s')";

	private Mower mCurMower;
	private int mCurMowerIdx;
	private int mCurInstructionIdx;
	private int mLastMowerIdx;
	private int mLastInstructionIdx; // for the current mower

	private Activity mActivity;
	private WebView mWebview;
	private InstructionsParser mInstructions;

	MowerAnimator(Activity activity, WebView webview, InstructionsParser instructions) {
		mActivity = activity;
		mWebview = webview;
		mInstructions = instructions;
		mLastMowerIdx = instructions.getMowers().size() - 1;
	}

	/**
	 * Starts the animation.
	 * <p>
	 * Called once the page is fully loaded.
	 * </p>
	 */
	public void start() {
		mCurMowerIdx = -1;
		initField(mInstructions.getField());
		placeNextMowerInField();
	}

	/**
	 * Sends the next instructions (if any) to run to Javascript.
	 */
	public void processNextInstruction() {
		if (mCurInstructionIdx > mLastInstructionIdx) {
			appendResult();
			placeNextMowerInField();
		} else {
			MowerInstruction instr = mInstructions.getInstructionsForMower(mCurMower).get(mCurInstructionIdx++);
			Coordinate coords = mCurMower.getCoordinates();
			if (mCurMower.process(instr)) {
				sendInstruction(instr, coords, mCurMower.getCoordinates());
			} else {
				processNextInstruction();
			}
		}
	}

	void runJavascript(String jsMethod, Object... params) {
		final String url = String.format(Locale.US, jsMethod, params);

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mWebview.loadUrl(url);
			}
		});
	}

	private void initField(Field field) {
		Log.d(TAG, "Init field");
		runJavascript(JS_INIT_LAWN,
				field.getWidth(), field.getHeight());
	}

	private void initMower(Mower mower) {
		Log.d(TAG, "Init mower");
		runJavascript(JS_INIT_MOWER,
				mower.getCoordinates().getX(), mower.getCoordinates().getY(),
				getOrientation(mower));
	}

	private void sendInstruction(MowerInstruction instr, Coordinate prevCoords, Coordinate newCoords) {
		if (instr.equals(MowerInstruction.FORWARD)) {
			sendForwardInstruction(prevCoords, newCoords);
		} else {
			sendRotateInstruction(instr);
		}
	}

	private void sendForwardInstruction(Coordinate prevCoords, Coordinate newCoords) {
		Log.d(TAG, "Send forward instruction");
		runJavascript(JS_MOVE_FORWARD,
				prevCoords.getX(), prevCoords.getY(),
				newCoords.getX(), newCoords.getY());
	}

	private void sendRotateInstruction(MowerInstruction instr) {
		Log.d(TAG, "Send rotate instruction");
		runJavascript(JS_ROTATE_MOWER,
				(instr.equals(MowerInstruction.RIGHT) ? 90 : -90));
	}

	private void appendResult() {
		Mower mower = mInstructions.getMowers().get(mCurMowerIdx);
		runJavascript(JS_APPEND_RESULT, mower.getPosition());
	}

	private void placeNextMowerInField() {
		if (++mCurMowerIdx > mLastMowerIdx) {
			Log.d(TAG, "All instructions have been executed. Finishing.");
			mCurMower = null;
			return ;
		} else {
			mCurMower = mInstructions.getMowers().get(mCurMowerIdx);
			mCurInstructionIdx = 0;
			mLastInstructionIdx = mInstructions.getInstructionsForMower(mCurMower).size() -1;
			initMower(mCurMower);
			Log.d(TAG, "Mower #" + mCurMowerIdx + " is ready and waiting for instructions.");
		}
	}

	int getOrientation(Mower mower) {
		int result;
		switch (mower.getOrientation()) {
			case EAST:
				result = 90;
				break;
			case SOUTH:
				result = 180;
				break;
			case WEST:
				result = 270;
				break;
			default: // NORTH
				result = 0;
				break;
		}
		return result;
	}
}
