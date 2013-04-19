package com.nilhcem.mowitnow.result;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.app.Activity;
import android.webkit.WebView;

import com.nilhcem.mowitnow.core.Mower;
import com.nilhcem.mowitnow.core.instruction.MowerOrientation;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MowerAnimatorTest {
	private InstructionsParser parser;
	private Activity activity;
	private WebView webview;

	@Before
	public void setUp() {
		webview = Mockito.mock(WebView.class);

		activity = Mockito.mock(Activity.class);
		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Runnable runnable = (Runnable) invocation.getArguments()[0];
				runnable.run();
				return null;
			}
		}).when(activity).runOnUiThread((Runnable) Mockito.anyObject());

		parser = Mockito.mock(InstructionsParser.class);
		Mockito.when(parser.getMowers()).thenReturn(new ArrayList<Mower>());
	}

	@Test
	public void testRunJavascript() {
		final String jsCall = "javascript:test(%d)";
		final int jsCallValue = 2;
		final String expected = String.format(Locale.US, jsCall, jsCallValue);

		testRunJavascriptAssertFlag = false;
		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String theString = (String) invocation.getArguments()[0];
				testRunJavascriptAssertFlag = theString.equals(expected);
				return null;
			}
		}).when(webview).loadUrl(Mockito.anyString());

		MowerAnimator animator = new MowerAnimator(activity, webview, parser);
		animator.runJavascript(jsCall, jsCallValue);
		assertThat(testRunJavascriptAssertFlag).isTrue();
	}
	private static boolean testRunJavascriptAssertFlag = false;

	@Test
	public void testGetOrientations() {
		Mower mower = Mockito.mock(Mower.class);
		Mockito.when(mower.getOrientation())
			.thenReturn(MowerOrientation.NORTH)
			.thenReturn(MowerOrientation.EAST)
			.thenReturn(MowerOrientation.SOUTH)
			.thenReturn(MowerOrientation.WEST);

		MowerAnimator animator = new MowerAnimator(activity, webview, parser);
		assertThat(animator.getOrientation(mower)).isEqualTo(0);
		assertThat(animator.getOrientation(mower)).isEqualTo(90);
		assertThat(animator.getOrientation(mower)).isEqualTo(180);
		assertThat(animator.getOrientation(mower)).isEqualTo(270);
	}
}
