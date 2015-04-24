package edu.plu.cs.retagitlocal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The Main Activity for the reTagit application. This is where the other activities
 * and view are accessed when the Employee is logged in.
 */
public class MainActivity extends Activity {
	
	private Button mAddNewItemButton;
	private Button mBrowseItemsButton;
	private Button mSellItemButton;
	private Button mAccountSettingsButton;
	private Button mLogOffButton;
	private Button mManageAccountsButton;
	
	//A persistent boolean variable to see if a user is already logged on 
	public static boolean loggedOn = false;

	/**
	 * Listener for the AddNewItem button. When the button is clicked, this listener
	 * changes the view to the AddNewItem view.
	 */
	public class AddNewButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			Intent i = new Intent( MainActivity.this, AddNewItemActivity.class );
			startActivity( i );
		}
	}
	
	/**
	 * Listener for the BrowseItems button. When the button is clicked, this
	 * listener changes the view to the BrowseItems view.
	 */
	public class BrowseButtonListener implements OnClickListener{
		
		@Override
		public void onClick( View v ){
			Intent i = new Intent( MainActivity.this, BrowseItemActivity.class );
			startActivity( i );
		}
	}
	
	/**
	 * Listener for the SellItem button. When the button is clicked, this
	 * listener changes the view to the SellItem view.
	 */
	public class SellButtonListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			Intent i = new Intent( MainActivity.this, GetSellingItem.class );
			startActivity( i );
		}
	}
	
	/**
	 * Listener for the AccountSettings button. When the button is clicked, 
	 * this listener changes the view to the AccountSettings view.
	 */
	public class ManageAccountsListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			Intent i = new Intent( MainActivity.this, AddNewClerkActivity.class );
			startActivity( i );
		}
	}
	
	/**
	 * Listener for the LogOff button. When the button is clicked, this
	 * listener changes the view to the LogOn view.
	 */
	public class LogOffButtonListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			
			Intent i = new Intent( MainActivity.this, LogOnActivity.class );
			startActivity( i );
			
			LogOnActivity.logOut();
			finish();
		}
	}
	
	/**
	 * Called when the activity is first created. This is where the buttons for
	 * the main menu are declared. This method also provides a Bundle containing
	 * the activity's previously frozen state, if there was one. Always followed by onStart().
	 * 
	 * Javadoc comment courtesy of http://developer.android.com/reference/android/app/Activity.html
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_manager_layout );
		
		// Get Buttons from their IDs
		mAddNewItemButton = ( Button ) findViewById( R.id.add_new_item_button );
		mAccountSettingsButton = ( Button ) findViewById( R.id.account_settings_button );
		mManageAccountsButton = ( Button ) findViewById( R.id.manage_accounts_button );
		mSellItemButton = ( Button ) findViewById( R.id.sell_item_button );
		mBrowseItemsButton = ( Button ) findViewById( R.id.browse_items_button );
		mLogOffButton = ( Button ) findViewById( R.id.log_off_button );
		
		// Set listeners for the buttons
		mSellItemButton.setOnClickListener( new SellButtonListener() );
		mAddNewItemButton.setOnClickListener( new AddNewButtonListener() );
		
		//mAccountSettingsButton.setOnClickListener( new AccountSettingsListener() );
		mManageAccountsButton.setOnClickListener( new ManageAccountsListener() );
		mBrowseItemsButton.setOnClickListener( new BrowseButtonListener() );
		mLogOffButton.setOnClickListener( new LogOffButtonListener() );
	}
	
	/**
	 * Initialize the contents of the Activity's standard options menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * You can safely hold on to menu (and any items created from it), making
	 * modifications to it as desired, until the next time onCreateOptionsMenu() is called.
	 * When you add items to the menu, you can implement the Activity's
	 * onOptionsItemSelected(MenuItem) method to handle them there.
	 * 
	 * @param menu The options menu in which you place your items.
	 *
	 * Javadoc comment courtesy of http://developer.android.com/reference/android/app/Activity.html
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}
}