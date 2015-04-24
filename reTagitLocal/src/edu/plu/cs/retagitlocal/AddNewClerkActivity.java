package edu.plu.cs.retagitlocal;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class adds a new employee to the database.
 */
public class AddNewClerkActivity extends Activity {
	
	private final String TAG = "AddNewClerkActivity.java";
	private Button okButton;
	private Button cancelButton;

	/**
	 * This listener sends the information to the php file,
	 * which adds into the EMPLOYEE table in the database.
	 */
	public class AddOkButtonListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			
			// Get text from fields
			EditText employeeId = ( EditText ) findViewById( R.id.employee_phone );
			String employeePhoneInput = employeeId.getText().toString();
			
			EditText employeeFirstName = ( EditText ) findViewById( R.id.employee_first_name );
			String employeeFirstNameInput = employeeFirstName.getText().toString();
			
			EditText employeeLastName = ( EditText ) findViewById( R.id.employee_last_name );
			String employeeLastNameInput = employeeLastName.getText().toString();
			
			EditText employeePassword = ( EditText ) findViewById( R.id.employee_password );
			String employeePasswordInput = employeePassword.getText().toString();
			
			RequestParams params = new RequestParams();
			params.put( "fname", employeeFirstNameInput );
			params.put( "lname", employeeLastNameInput );
			params.put( "phone", employeePhoneInput );
			params.put( "password", employeePasswordInput );

			InputMethodManager imm = ( InputMethodManager )AddNewClerkActivity.this.getSystemService( Activity.INPUT_METHOD_SERVICE );
			imm.hideSoftInputFromWindow( employeeId.getWindowToken(), 0 );

			Log.d( TAG, "sending employee id number: " + employeeId.getText().toString() );
			
			AfterIdNumberAddHandler handler = new AfterIdNumberAddHandler();
			MyHTTPclient.post( MyHTTPclient.SET_ID_NUMBER_REL_URL, params, handler );
		}

		/**
		 * Class that extends the AsyncHttpResponseHandler. Displays a toast
		 * to indicate that the item was successfully added to the database.
		 */
		private class AfterIdNumberAddHandler extends AsyncHttpResponseHandler {
			
			@Override
			public void onSuccess( int code, Header[] headers, byte[] data ) {
				
				Log.d( TAG,"onSuccess code: " + code );

				// Show a Toast to indicate that the caption was successfully changed
				Toast.makeText( AddNewClerkActivity.this, "New Clerk Added!", Toast.LENGTH_SHORT ).show();
			}
		}
	}

	/**
	 * The cancel button listener. This button only needs to return to the previous view.
	 */
	public class AddCancelButtonListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			finish();
		}
	}

	/**
	 * Called when the application is started. This is where
	 * we instantiate all of the buttons.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.addnewclerk );

		okButton = ( Button ) findViewById( R.id.ok_button );
		okButton.setOnClickListener( new AddOkButtonListener() );

		cancelButton = ( Button ) findViewById( R.id.cancel_button );
		cancelButton.setOnClickListener( new AddCancelButtonListener() );
	}

	/**
	 * Initialize the contents of the Activity's standard options menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * You can safely hold on to menu (and any items created from it), making modifications to
	 * it as desired, until the next time onCreateOptionsMenu() is called.
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
		getMenuInflater().inflate( R.menu.addnewclerk, menu );
		return true;
	}
}