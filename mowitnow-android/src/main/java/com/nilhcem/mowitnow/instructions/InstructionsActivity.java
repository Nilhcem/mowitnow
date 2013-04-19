package com.nilhcem.mowitnow.instructions;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nilhcem.mowitnow.App;
import com.nilhcem.mowitnow.R;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;
import com.nilhcem.mowitnow.core.instruction.parser.ParserException;
import com.nilhcem.mowitnow.result.ResultActivity;

/**
 * Main activity displaying a form to input instructions.
 */
public class InstructionsActivity extends Activity {
	private EditText mInstructions;
	private Button mMainButton;
	private AlertDialog mErrorAlert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions_layout);
		injectViews();

		mMainButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new InstructionsParserAsync().execute();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mErrorAlert != null) {
			mErrorAlert.dismiss();
		}
	}

	/**
	 * @return a list of instructions - each element is a line from the EditView.
	 */
	List<String> getInstructions() {
		return Arrays.asList(mInstructions.getText().toString().split("\\n+"));
	}

	private void injectViews() {
		mInstructions = (EditText) findViewById(R.id.instrInstructions);
		mMainButton = (Button) findViewById(R.id.instrButton);
	}

	final class InstructionsParserAsync extends AsyncTask<Void, Void, InstructionsParser> {
		private static final String TAG = "InstructionsParserAsync";

		@Override
		protected InstructionsParser doInBackground(Void... params) {
			InstructionsParser instructions = null;

			try {
				instructions = new InstructionsParser(getInstructions());
			} catch (ParserException e) {
				Log.e(TAG, "Error while parsing instructions", e);
			}

			if (instructions != null
					&& (instructions.getField() == null || instructions.getMowers().size() == 0)) {
				instructions = null;
			}
			return instructions;
		}

		@Override
		protected void onPostExecute(InstructionsParser result) {
			super.onPostExecute(result);

			if (result == null) {
				mErrorAlert = new AlertDialog.Builder(InstructionsActivity.this)
						.setTitle(R.string.instr_error_title)
						.setMessage(R.string.instr_error_msg)
						.setCancelable(true).show();
			} else {
				((App) getApplication()).setInstructions(result);

				Intent intent = new Intent(InstructionsActivity.this,
						ResultActivity.class);
				startActivity(intent);
			}
		}
	}
}
