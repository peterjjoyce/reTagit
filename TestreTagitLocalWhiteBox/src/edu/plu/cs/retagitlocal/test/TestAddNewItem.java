package edu.plu.cs.retagitlocal.test;

import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import edu.plu.cs.retagitlocal.AddNewItemActivity;
import junit.framework.TestCase;

public class TestAddNewItem extends ActivityInstrumentationTestCase2<AddNewItemActivity> {

	private Solo solo;
	
	public TestAddNewItem() {
		super( AddNewItemActivity.class );
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() );
	}

	public void testA() {
		solo.assertCurrentActivity( "Check on first activity", AddNewItemActivity.class );
	}
	
	public void testEditText() {
		solo.enterText( 0, "0000000095" );
		solo.enterText( 1, "Hammer Pants" );
		solo.pressSpinnerItem(0, 0);
		solo.enterText( 2, "HAMMER TIME!" );
		solo.enterText( 3,  "6969696969" );
		solo.enterText( 4, "19.80" );
		solo.enterText( 5, "19.99" );
		solo.clickOnButton( "Submit" );
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
