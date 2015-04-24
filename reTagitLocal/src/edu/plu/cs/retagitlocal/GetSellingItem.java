package edu.plu.cs.retagitlocal;

import net.sourceforge.zbar.Symbol;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This activity queries the database for a single item that the user enters
 * the id number for or scans the QR code. The result is displayed
 * by opening the ViewItem activity.
 */
public class GetSellingItem extends Activity {
	
	private Button QRCode;
	private EditText trackingNum;
	private Button submit;
	private final int ZBAR_REQUEST_ADD_ITEM = 1;
	private String num = null;
	private final String TAG = "GetSellingItem";
	private String dateDB = null;
	private String cat = null;
	private String desc = null;
	private String sellerDB = null;
	//private String min = null;
	private String asking = null;
	private String nameDB = null;

	/**
	 * This class instantiates the components in our get_item_selling_layout
	 * and sets the click listeners for the QR and submit buttons
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.get_item_selling_layout );

		trackingNum = ( EditText ) findViewById( R.id.item_number );
		QRCode = ( Button ) findViewById( R.id.qr_button );
		submit = ( Button ) findViewById( R.id.submit );

		submit.setOnClickListener( new SubmitButtonListener() );
		QRCode.setOnClickListener( new QRCodeListener() );
	}

	/**
	 * Initialize the contents of the Activity's standard options menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * You can safely hold on to menu (and any items created from it), making modifications to it as desired, 
	 * until the next time onCreateOptionsMenu() is called. When you add items to the menu, you can
	 * implement the Activity's onOptionsItemSelected(MenuItem) method to handle them there.
	 * 
	 * @param menu The options menu in which you place your items.
	 * 
	 * Javadoc comment courtesy of http://developer.android.com/reference/android/app/Activity.html
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.sell_item, menu );
		return true;
	}

	/**
	 * Listener for the submit button. This listener sends information
	 * to the server to get information about an item. The information is
	 * packaged up and then sent to the sell item activity.
	 */
	public class SubmitButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {

			if ( num == null ) {
				num = trackingNum.getText().toString();
			}

			RequestParams params = new RequestParams();
			params.put( "tracking_number", num );

			Log.d( TAG, "Sending tracking_number: " + num );
			
			FillFieldsHandler handler = new FillFieldsHandler();
			MyHTTPclient.post( MyHTTPclient.GET_ITEM_INFO, params, handler );
		}
	}

	/**
	 * Listener for the QRButton. This class communicates with the ZBar classes
	 * to get the QR number that is associated with an item.
	 */
	public class QRCodeListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			
			// New intent to call the ZBarScannerActivity from the ZBarScanner Library
			Intent intent = new Intent( GetSellingItem.this, ZBarScannerActivity.class );
			
			// Specify ZBar to only scan for QR Codes (not barcodes or others).
			intent.putExtra( ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE} );
			
			// Makes sure result of QR scan is sent to onActivityResult() later.
			startActivityForResult( intent, ZBAR_REQUEST_ADD_ITEM ); 
		}
	}

	/**
	 * Handles data being returned to the GetSellingItem activity from the  ZBarScanner intent
	 * by updating the text field.
	 * 
	 * @param requestCode Code used for identifying whether the ZBarScanner was called
	 * @param resultCode Code that returns the success status of the intent. RESULT_OK or RESULT_CANCELED.
	 * @param data The Intent associated with the completed scan.
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		
		if ( requestCode == ZBAR_REQUEST_ADD_ITEM && resultCode == RESULT_OK ) {
			// Scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT)
			num = data.getStringExtra( ZBarConstants.SCAN_RESULT );
			trackingNum.setText( num );
		}
	}

	/**
	 * This handler sends information to the php file that queries the
	 * database for a specific is tracking_number. That information is placed
	 * in an intent and then sent to the SellItem activity.
	 */
	private class FillFieldsHandler extends JsonHttpResponseHandler{

		@Override
		public void onSuccess( JSONArray response ) {
			
			try {
				JSONObject item = response.getJSONObject( 0 );
				
				nameDB = item.getString( "name" );
				Toast.makeText( GetSellingItem.this, "name is " + nameDB, Toast.LENGTH_SHORT ).show();
				desc = item.getString( "description" );
				cat = item.getString( "category" );
				asking = item.getString( "asking_price" );
				sellerDB = item.getString( "seller" );
				dateDB = item.getString( "date_added" );

				Intent i = new Intent( GetSellingItem.this, SellItemActivity.class );
				i.putExtra( "tracking_num", num );
				i.putExtra( "name", nameDB );
				i.putExtra( "description", desc );
				i.putExtra( "category", cat );
				i.putExtra( "asking_price", asking );
				i.putExtra( "seller", sellerDB );
				i.putExtra( "date_added", dateDB) ;
				
				startActivity( i );
			} catch ( JSONException e ) {
				Log.e( TAG, "Failed to get JSON object from response: " + e.getMessage() );
			}
		}

		@Override
		public void onFailure( Throwable e, JSONObject errorResponse ) {
			super.onFailure( e, errorResponse );
			Log.e( TAG, "Failed to retrieve JSON data: " + e.getMessage() );
		}
	}
}