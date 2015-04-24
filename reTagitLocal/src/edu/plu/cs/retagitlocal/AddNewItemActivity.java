package edu.plu.cs.retagitlocal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.Header;
import net.sourceforge.zbar.Symbol;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This is the activity for adding a new item. The item's information is sent to
 * the server and will later be displayed in the BrowseActivity Activity. 
 */
public class AddNewItemActivity extends Activity {

	private static final String TAG = "AddNewItemActivity.java";

	private Button mCancelButton;
	private Button mSubmitButton;
	private Button mQRButton;
	private ImageButton mPhotoButton;
	private Spinner spinner;
	private String select_cat;
	final static int ZBAR_REQUEST_ADD_ITEM = 1;
	final static int PHOTO_REQUEST_ADD_ITEM = 2;

	/**
	 * The cancel button listener. If clicked, finishes this activity and
	 * returns to the previous activity 
	 */
	public class CancelButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			finish();
		}
	}

	/**
	 * The submit button listener. If clicked, collects information from the
	 * fields and sends information to the server, creating a new item in the
	 * database
	 */
	public class SubmitButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {

			//Get texts from fields
			EditText edittext_trackingNumber = ( EditText ) findViewById( R.id.item_number_edittext );
			String trackingNumberInput = edittext_trackingNumber.getText().toString();

			EditText edittext_name = ( EditText ) findViewById( R.id.name_edittext );
			String nameInput = edittext_name.getText().toString();

			EditText edittext_description = ( EditText ) findViewById( R.id.description_edittext );
			String descriptionInput = edittext_description.getText().toString();

			EditText edittext_seller = ( EditText ) findViewById( R.id.seller_edittext );
			String sellerInput = edittext_seller.getText().toString();

			EditText edittext_minimumPrice = ( EditText ) findViewById (R.id.minimumPrice_edittext );
			String minimumPriceInput = edittext_minimumPrice.getText().toString();

			EditText edittext_askingPrice = ( EditText ) findViewById( R.id.askingPrice_edittext );
			String askingPriceInput = edittext_askingPrice.getText().toString();

			String timeStamp = new SimpleDateFormat( "yyyy:MM:dd HH:mm:ss" ).format( Calendar.getInstance().getTime() );

			select_cat = spinner.getSelectedItem().toString();
			
			Log.d( TAG, "select_cat: " + select_cat );

			RequestParams params = new RequestParams();
			params.put( "tracking_number", trackingNumberInput );
			params.put( "name", nameInput );
			params.put( "description", descriptionInput );
			params.put( "seller", sellerInput );
			params.put( "minimum_price", minimumPriceInput );
			params.put( "asking_price", askingPriceInput );
			params.put( "date_added", timeStamp );
			params.put( "category", select_cat );

			InputMethodManager imm = ( InputMethodManager ) AddNewItemActivity.this.getSystemService( Activity.INPUT_METHOD_SERVICE );
			imm.hideSoftInputFromWindow( edittext_trackingNumber.getWindowToken(), 0 );

			Log.d( TAG, "sending tracking number: "+ edittext_trackingNumber.getText().toString() );
			
			AfterTrackingNumberAddHandler handler = new AfterTrackingNumberAddHandler();
			MyHTTPclient.post( MyHTTPclient.SET_TRACKING_NUMBER_REL_URL, params,handler );
		}

		/**
		 * Class that extends the AsyncHttpResponseHandler. Displays a toast to
		 * indicate that the item was successfully added to the database.
		 */
		private class AfterTrackingNumberAddHandler extends AsyncHttpResponseHandler {
			
			@Override
			public void onSuccess( int code, Header[] headers, byte[] data ) {
				
				Log.d( TAG, "onSuccess code: " + code );

				// Show a Toast to indicate that the caption was successfully changed
				Toast.makeText( AddNewItemActivity.this, "Tracking Number Added to DB.", Toast.LENGTH_SHORT ).show();
			}
		}
	}

	/**
	 * Listener for the QRButton. This class communicates with the ZBar classes
	 * to get the QR number that is associated with the item. 
	 */
	public class QRButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			
			// New intent to call the ZBarScannerActivity from the ZBarScanner Library
			Intent intent = new Intent( AddNewItemActivity.this, ZBarScannerActivity.class );
			
			// Specify ZBar to only scan for QR Codes (not barcodes or others).
			intent.putExtra( ZBarConstants.SCAN_MODES, new int[] { Symbol.QRCODE } );
			
			// Makes sure result of QR scan is sent to onActivityResult() later.
			startActivityForResult( intent, ZBAR_REQUEST_ADD_ITEM );
		}
	}

	/**
	 * Listener for the photo button. Starts the native camera application of
	 * the phone using intents.
	 */
	public class PhotoButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			
			Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
			if ( intent.resolveActivity( getPackageManager() ) != null ) {
				startActivityForResult( intent, PHOTO_REQUEST_ADD_ITEM );
			}
		}
	}

	/**
	 * Handles data being returned to the AddNewItemActivity from the Camera or
	 * the ZBarScanner intents by updating the image button or the text field
	 * respectively.
	 * 
	 * @param requestCode Code used for identifying whether the Camera or ZBarScanner was called
	 * @param resultCode Code that returns the success status of the intent. RESULT_OK or RESULT_CANCELED.
	 * @param data The Intent associated with the completed scan.
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		
		if ( requestCode == PHOTO_REQUEST_ADD_ITEM && resultCode == RESULT_OK ) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = ( Bitmap ) extras.get( "data" );
			ImageButton imagebutton = ( ImageButton ) findViewById( R.id.photo_button );
			imagebutton.setImageBitmap( imageBitmap );
		} else if ( requestCode == PHOTO_REQUEST_ADD_ITEM ) {
			Toast.makeText( this, "Camera unavailable", Toast.LENGTH_SHORT ).show();
		} else if ( requestCode == ZBAR_REQUEST_ADD_ITEM && resultCode == RESULT_OK ) {
			// Scan result is available by making a call to
			// data.getStringExtra(ZBarConstants.SCAN_RESULT)
			EditText edittext = ( EditText ) findViewById( R.id.item_number_edittext );
			edittext.setText( data.getStringExtra( ZBarConstants.SCAN_RESULT ) );
		} else if ( resultCode == RESULT_CANCELED ) {
			Toast.makeText( this, "Camera unavailable", Toast.LENGTH_SHORT ).show();
		} else {
			Toast.makeText( this, "Undefined Camera Error", Toast.LENGTH_LONG ).show();
		}
	}

	/**
	 * Starts when this activity starts. Instantiates each of the buttons.
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.add_new_item_layout );

		mQRButton = ( Button ) findViewById( R.id.qr_button );
		mQRButton.setOnClickListener( new QRButtonListener() );

		mCancelButton = ( Button ) findViewById( R.id.cancel_button );
		mCancelButton.setOnClickListener( new CancelButtonListener() );

		mSubmitButton = ( Button ) findViewById( R.id.submit_button );
		mSubmitButton.setOnClickListener( new SubmitButtonListener() );

		mQRButton = ( Button ) findViewById( R.id.qr_button );
		mQRButton.setOnClickListener( new QRButtonListener() );

		mPhotoButton = ( ImageButton ) findViewById( R.id.photo_button );
		mPhotoButton.setOnClickListener( new PhotoButtonListener() );

		spinner = ( Spinner ) findViewById( R.id.category_spinner );
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.category_array, android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		spinner.setAdapter( adapter );
	}

	/**
	 * Initialize the contents of the Activity's standard options menu. This is
	 * only called once, the first time the options menu is displayed. You can
	 * safely hold on to menu (and any items created from it), making
	 * modifications to it as desired, until the next time onCreateOptionsMenu()
	 * is called. When you add items to the menu, you can implement the
	 * Activity's onOptionsItemSelected(MenuItem) method to handle them there.
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