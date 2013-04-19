package com.nilhcem.mowitnow.instructions;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.nilhcem.mowitnow.App;
import com.nilhcem.mowitnow.R;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;
import com.nilhcem.mowitnow.core.instruction.parser.ParserException;
import com.nilhcem.mowitnow.instructions.InstructionsActivity.InstructionsParserAsync;
import com.nilhcem.mowitnow.result.ResultActivity;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class InstructionsActivityTest {
	private InstructionsActivity activity;
	private Button mainButton;
	private EditText editText;

	@Before
	public void setUp() {
		activity = new InstructionsActivity();
		activity.onCreate(null);
		mainButton = (Button) activity.findViewById(R.id.instrButton);
		editText = (EditText) activity.findViewById(R.id.instrInstructions);
	}

	@Test
	public void shouldHaveAButtonThatMowItNow() throws Exception {
		assertThat((String) mainButton.getText()).isEqualTo("Mow it now!");
	}

	@Test
	public void contentShouldNotBeEmpty() {
		assertThat(TextUtils.isEmpty(editText.getText())).isFalse();
	}

	@Test
	public void shouldDisplayErrorWhenContentIsInvalid() {
		editText.setText("__INVALID__");
		mainButton.performClick();
		AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
		ShadowAlertDialog sAlert = Robolectric.shadowOf(alert);
		assertThat(sAlert.getTitle()).isEqualTo(activity.getString(R.string.instr_error_title));
		assertThat(sAlert.getMessage()).isEqualTo(activity.getString(R.string.instr_error_msg));
	}

	@Test
	public void shouldStartResultActivityWhenOK() throws ParserException {
		InstructionsParserAsync async = activity.new InstructionsParserAsync();
		async.onPostExecute(Mockito.mock(InstructionsParser.class));

		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);
		assertThat(shadowIntent.getComponent().getClassName()).isEqualTo(ResultActivity.class.getName());
	}

	@Test
	public void shouldSaveInstructionsInMainAppObject() {
		App app = (App) activity.getApplication();
		assertThat(app.getInstructions()).isNull();

		InstructionsParser instructions = Mockito.mock(InstructionsParser.class);
		InstructionsParserAsync async = activity.new InstructionsParserAsync();
		async.onPostExecute(instructions);

		assertThat(app.getInstructions()).isEqualTo(instructions);
	}
}
