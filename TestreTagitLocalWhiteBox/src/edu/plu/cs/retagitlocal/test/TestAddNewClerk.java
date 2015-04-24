package edu.plu.cs.retagitlocal.test;

import com.robotium.solo.Solo;

import edu.plu.cs.retagitlocal.AddNewClerkActivity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class TestAddNewClerk extends ActivityInstrumentationTestCase2<AddNewClerkActivity> {

	private Solo solo;
	
	public TestAddNewClerk() {
		super(AddNewClerkActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() ); 
	}

	public void testA() {
		solo.assertCurrentActivity( "Check on first activity", AddNewClerkActivity.class );
	}
	
	public void testEditText() {
		solo.enterText( 0, "2536669999" );
		solo.enterText( 1, "TheMuffin" );
		solo.enterText( 2, "Man" );
		solo.enterText( 3, "muffinsmuffins" );
		solo.clickOnButton( "OK" );
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
