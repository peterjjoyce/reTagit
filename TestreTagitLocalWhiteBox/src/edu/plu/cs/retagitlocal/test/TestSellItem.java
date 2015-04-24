package edu.plu.cs.retagitlocal.test;

import com.robotium.solo.Solo;
import edu.plu.cs.retagitlocal.GetSellingItem;
import edu.plu.cs.retagitlocal.SellItemActivity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class TestSellItem extends ActivityInstrumentationTestCase2<SellItemActivity> {

	private Solo solo;
	
	public TestSellItem() {
		super( SellItemActivity.class );
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() );
	}

	public void testA() {
		solo.assertCurrentActivity( "Check on first activity", SellItemActivity.class );
	}
	
	public void testSellItem() {
		solo.enterText( 0,  "0000000002" );
		solo.clickOnButton( "Submit" );
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
