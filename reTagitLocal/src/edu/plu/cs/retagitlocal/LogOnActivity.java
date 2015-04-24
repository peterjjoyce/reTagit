package edu.plu.cs.retagitlocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Activity for handling the Log On View
 */
public class LogOnActivity extends Activity {

	private static final String TAG = "LogOnActivity.java";

	private Button mLogOnButton;
	private Button mClearButton;
	String pass;
	String passwordInput;
	static String userNameInput;
	private static boolean loggedIn = false;
	
	// Set the loggedIn status to false so logOnView will load
	public static void logOut() {
		loggedIn = false;
	}

	/**
	 * ClearButton listener. When the "clear" button is pressed
	 * both the userName and password text fields are cleared by
	 * setting the fields to an empty string.
	 */
	public class ClearButtonListener implements OnClickListener{

		@Override
		public void onClick( View v ){

			Log.d( TAG, "Clear Button Pressed" );

			//Clears user name text field
			EditText edittext_userName = ( EditText )findViewById( R.id.userNameText );
			edittext_userName.setText( "" );

			//Clears password text field
			EditText edittext_password = ( EditText )findViewById( R.id.passwordText );
			edittext_password.setText( "" );
		}
	}

	/**
	 * Logon button listener. When the "log on" button is pressed it takes the user name
	 * and password from the text fields and checks the credentials based on server response.
	 * If successful it will to the appropriate main menu view based on authorization.
	 */
	public class LogOnButtonListener implements OnClickListener{

		@Override
		public void onClick( View v ){

			Log.d( TAG, "Log On Button Pressed" );

			//Grab the text in the text fields
			EditText edittext_userName = ( EditText )findViewById( R.id.userNameText );
			userNameInput = edittext_userName.getText().toString();
			EditText edittext_password = ( EditText )findViewById( R.id.passwordText );
			passwordInput = edittext_password.getText().toString();

			//Pack it up to ship to the server
			RequestParams params = new RequestParams();
			params.put( "phone", userNameInput );
			params.put( "password", passwordInput );

			//Make the keyboard disappear
			InputMethodManager imm = ( InputMethodManager )LogOnActivity.this.getSystemService( Activity.INPUT_METHOD_SERVICE );
			imm.hideSoftInputFromWindow( edittext_password.getWindowToken(), 0 );

			Log.d( TAG, "Sending User Name: " + edittext_userName.getText().toString() );
			Log.d( TAG, "Sending Password: " + edittext_password.getText().toString() );

			AfterLogOnHandler handler = new AfterLogOnHandler();
			MyHTTPclient.post( MyHTTPclient.CHECK_USER_URL, params, handler );
		}

		/**
		 * This handles post-server communication. Should move user, if valid, to appropriate
		 * main menu based on authorization
		 */
		private class AfterLogOnHandler extends JsonHttpResponseHandler{

			@Override
			public void onSuccess( JSONArray response ) {
				
				try {
					String hashedPassword = response.getJSONObject(0).getString( "password" );
					String hashedInput = response.getJSONObject(1).getString( "hashword" );
					
					Log.d( TAG, hashedPassword );
					Log.d( TAG, hashedInput );
					
					if ( hashedInput.equals(hashedPassword) ) {
						Log.d( TAG, "Check Successful" );
						
						loggedIn = true;
						Intent i = new Intent( LogOnActivity.this, MainActivity.class );
						startActivity( i );
						finish();
					}
					else{
						// Log invalid password
						Log.d( TAG, "Invalid Password" ); 

						//Show toast for incorrect input
						Toast.makeText( LogOnActivity.this, "Invalid Password", Toast.LENGTH_SHORT ).show();
					}
				} catch ( JSONException e ) {
					Log.e( TAG, "Failed to get JSON object from response: " + e.getMessage() );
					Toast.makeText( LogOnActivity.this, "Error: Invalid login info", Toast.LENGTH_SHORT ).show();
				}
			}

			@Override
			public void onFailure( Throwable e, JSONObject errorResponse ) {
				super.onFailure( e, errorResponse );
				Log.e( TAG, "Failed to retrieve JSON data: " + e.getMessage() );
			}
		}
	}
	
	/**
	 * Called when the application is started. This is where
	 * we instantiate all of the buttons.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.log_on_layout );
		
		if ( loggedIn == true ) {
			Intent i = new Intent( LogOnActivity.this, MainActivity.class );
			startActivity( i );
			finish();
		}
		
		// Set Button Listeners
		mLogOnButton = ( Button ) findViewById( R.id.logon_button );
		mLogOnButton.setOnClickListener( new LogOnButtonListener() );

		mClearButton = ( Button ) findViewById( R.id.clear_button );
		mClearButton.setOnClickListener( new ClearButtonListener() );
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
		getMenuInflater().inflate( R.menu.log_on, menu );
		return true;
	}
}