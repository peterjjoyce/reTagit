package edu.plu.cs.retagitlocal.test;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import edu.plu.cs.retagitlocal.LogOnActivity;
import edu.plu.cs.retagitlocal.MainActivity;
import junit.framework.TestCase;

public class TestLogOn extends ActivityInstrumentationTestCase2<LogOnActivity> {

	private Solo solo;
	
	public TestLogOn() {
		super(LogOnActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() );
	}

	public void testA() {
		solo.assertCurrentActivity( "Check on first activity", LogOnActivity.class );
	}
	
	public void testLogOn() {
		solo.enterText( 0, "2323232323" );
		/*solo.enterText( 1, "hashmebro" );
		solo.clickOnButton( "Log on" );
		solo.assertCurrentActivity( "Main activity screen", MainActivity.class );*/
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
