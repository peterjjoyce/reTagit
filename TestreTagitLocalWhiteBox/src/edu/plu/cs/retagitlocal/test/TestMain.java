/**
 * 
 */
package edu.plu.cs.retagitlocal.test;

import com.robotium.solo.Solo;

import edu.plu.cs.retagitlocal.AddNewClerkActivity;
import edu.plu.cs.retagitlocal.AddNewItemActivity;
import edu.plu.cs.retagitlocal.BrowseItemActivity;
import edu.plu.cs.retagitlocal.GetSellingItem;
import edu.plu.cs.retagitlocal.LogOnActivity;
import edu.plu.cs.retagitlocal.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

/**
 *
 */
public class TestMain extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	
	public TestMain() {
		super( MainActivity.class );
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo( getInstrumentation(), getActivity() );
	}
	
	public void testA() {
		solo.assertCurrentActivity( "Check on first activity", MainActivity.class );
	}
		
	public void testAddButton(){	
		solo.clickOnButton( "Add New Item" );
		solo.assertCurrentActivity( "On add new item screen", AddNewItemActivity.class );
		solo.clickOnButton( "Cancel" );
		solo.assertCurrentActivity("Add new item activity closed", MainActivity.class);
	}
		
	public void testBrowseButton() {
		solo.clickOnButton( "Browse Items" );
		solo.assertCurrentActivity( "On browse items screen", BrowseItemActivity.class );
	}
	
	public void testSellButton() {
		solo.clickOnButton( "Sell Item" );
		solo.assertCurrentActivity( "On sell item screen", GetSellingItem.class );
	}
	
	public void testManageButton() {
		solo.clickOnButton( "Manage Accounts" );
		solo.assertCurrentActivity( "On add new clerk screen", AddNewClerkActivity.class );
		solo.clickOnButton( "Cancel" );
		solo.assertCurrentActivity("Add new clerk activity closed", MainActivity.class);
	}
	
	public void testLogOffButton() {
		solo.clickOnButton("Log Off");
		solo.assertCurrentActivity( "On log on screen", LogOnActivity.class );
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
