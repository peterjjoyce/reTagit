package edu.plu.cs.retagitlocal;

import net.sourceforge.zbar.Symbol;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import edu.plu.cs.retagitlocal.AddNewItemActivity.CancelButtonListener;

/** 
 * This activity adds a new seller to the database. This activity should be
 * called from the AddNewInventory activity at some point.
 */
public class AddNewSellerActivity extends Activity {
	
	private static final String TAG = "AddNewSellerActivity.java";

	private Button mSubmitButton;

	/**
	 * The submit button listener. If clicked, collects information from the fields
	 * and sends information to the server, creating a new item in the database
	 */
	public class SubmitButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {

			//Get text from fields
			EditText editText_firstName = ( EditText ) findViewById( R.id.seller_first_name );
			String firstNameInput = editText_firstName.getText().toString();

			EditText editText_lastName = ( EditText ) findViewById( R.id.seller_last_name );
			String lastNameInput = editText_lastName.getText().toString();

			EditText editText_address = ( EditText ) findViewById( R.id.seller_address );
			String addressInput = editText_address.getText().toString();

			EditText editText_phone = ( EditText ) findViewById( R.id.seller_phone );
			String phoneInput = editText_phone.getText().toString();

			RequestParams params = new RequestParams();
			params.put( "first_name", firstNameInput );
			params.put( "last_name", lastNameInput );
			params.put( "address", lastNameInput );
			params.put( "phone", phoneInput );

			InputMethodManager imm = ( InputMethodManager )AddNewSellerActivity.this.getSystemService( Activity.INPUT_METHOD_SERVICE );
			imm.hideSoftInputFromWindow( editText_firstName.getWindowToken(), 0 );

			Log.d( TAG, "sending seller: " + editText_lastName.getText().toString() );
			
			AfterSellerAddHandler handler = new AfterSellerAddHandler();
			MyHTTPclient.post( MyHTTPclient.SET_TRACKING_NUMBER_REL_URL, params, handler );
		}

		/**
		 * Class that extends the AsyncHttpResponseHandler. Displays a toast
		 * to indicate that the item was successfully added to the database.
		 */
		private class AfterSellerAddHandler extends AsyncHttpResponseHandler {
			
			@Override
			public void onSuccess( int code, Header[] headers, byte[] data ) {
				
				Log.d( TAG,"onSuccess code: " + code );

				// Show a Toast to indicate that the caption was successfully changed
				Toast.makeText( AddNewSellerActivity.this, "Seller Added to DB.", Toast.LENGTH_SHORT ).show();
			}
		}
	}

	/**
	 * Starts when this activity starts. Instantiates each of the buttons.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		mSubmitButton = ( Button ) findViewById( R.id.submit_seller );
		mSubmitButton.setOnClickListener( new SubmitButtonListener() );
	}

	/**
	 * Initialize the contents of the Activity's standard options menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * You can safely hold on to menu (and any items created from it), making modifications to it as desired, 
	 * until the next time onCreateOptionsMenu() is called. When you add items to the menu, you
	 * can implement the Activity's onOptionsItemSelected(MenuItem) method to handle them there.
	 * 
	 * @param menu The options menu in which you place your items.
	 * 
	 * Javadoc comment courtesy of http://developer.android.com/reference/android/app/Activity.html
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.add_new_item, menu );
		return true;
	}
}