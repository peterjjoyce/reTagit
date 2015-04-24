package edu.plu.cs.retagitlocal.test;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import edu.plu.cs.retagitlocal.BrowseItemActivity;
import edu.plu.cs.retagitlocal.ViewItemActivity;
import junit.framework.TestCase;

public class TestBrowseItem extends ActivityInstrumentationTestCase2<BrowseItemActivity> {

	private Solo solo;
	
	public TestBrowseItem() {
		super(BrowseItemActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() );
	}

	public void testA() {
		solo.assertCurrentActivity("Check on first activity", BrowseItemActivity.class);
	}
	
	public void testBrowseItems() {
		solo.clickOnButton( "Sort by Price" );
		solo.clickOnButton( "Sort by Date" );
		solo.clickOnButton( "Sort by Category" );
		solo.clickInList(3);
		solo.assertCurrentActivity("Now on view item screen", ViewItemActivity.class);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
