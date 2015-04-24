package edu.plu.cs.retagitlocal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This is the activity for selling an existing item. The item's information
 * is retrieved from the database then sent back marked as sold.
 */
public class SellItemActivity extends Activity {
	
	private ImageView photoImage;
	private TextView trackingNum;
	private TextView date;
	private TextView category;
	private TextView description;
	private TextView seller;
	private EditText minPrice;
	private EditText askingPrice;
	private TextView name;
	private Button submit;
	private String track_num;
	private final String TAG = "SellItemActivity";
	private String dateDB = null;
	private String cat = null;
	private String desc = null;
	private String sellerDB = null;
	//private String min = null;
	private String asking = null;
	private String nameDB = null;

	/**
	 * Starts when this activity starts. Instantiates each of the buttons.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.sell_item_layout );

		name = ( TextView ) findViewById( R.id.name );
		trackingNum = ( TextView ) findViewById( R.id.item_number );
		photoImage = ( ImageView ) findViewById( R.id.photo );
		date = ( TextView ) findViewById( R.id.date );
		category = ( TextView ) findViewById( R.id.category );
		description = ( TextView ) findViewById( R.id.description );
		seller = ( TextView ) findViewById( R.id.seller );
		minPrice = ( EditText ) findViewById( R.id.minPrice );
		askingPrice = ( EditText ) findViewById( R.id.askingPrice );
		submit = ( Button ) findViewById( R.id.submit );

		submit.setOnClickListener( new SubmitButtonListener() );

		Bundle extras = getIntent().getExtras();
		if ( extras != null ) {
			track_num = extras.getString( "tracking_num" );
			nameDB = extras.getString( "name" );
			dateDB = extras.getString( "date_added" );
			cat = extras.getString( "category" );
			desc = extras.getString( "description" );
			sellerDB = extras.getString( "seller" );
			asking = extras.getString( "asking_price" );
		}

		trackingNum.setText( " " + track_num );
		name.setText( " " + nameDB );
		date.setText( " " + dateDB );
		category.setText( " " + cat );
		description.setText( " " + desc );
		seller.setText( sellerDB );
		askingPrice.setText( asking );
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
		getMenuInflater().inflate( R.menu.sell_item, menu );
		return true;
	}

	/**
	 * The submit button listener. If clicked, collects information from the
	 * fields and sends information to the server, marking an item from the
	 * database as sold
	 */
	public class SubmitButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {

			Log.d( TAG, "deleting item: " + track_num );

			RequestParams params = new RequestParams();
			params.put( "tracking_number", track_num );

			DeleteItemHandler handler = new DeleteItemHandler();
			MyHTTPclient.post( MyHTTPclient.DELETE_ITEM_URL, params, handler );
		}
	}

	/**
	 * Class that extends the AsyncHttpResponseHandler. Displays a toast to
	 * indicate that the item was successfully marked as sold in the database.
	 */
	private class DeleteItemHandler extends AsyncHttpResponseHandler {

		@Override
		public void onSuccess( int code, Header[] headers, byte[] data ) {
			
			Log.d( TAG, "onSuccess code: " + code );

			Toast.makeText( SellItemActivity.this, "item sold", Toast.LENGTH_SHORT ).show();

			String timeStamp = new SimpleDateFormat( "yyyy:MM:dd HH:mm:ss" ).format( Calendar.getInstance().getTime() );
			
			RequestParams params = new RequestParams();
			params.put( "tracking_number", track_num );
			params.put( "date", timeStamp );
			
			AddToSaleTableHandler handler = new AddToSaleTableHandler();
			MyHTTPclient.post( MyHTTPclient.ADD_TO_SALE_URL, params, handler );
		}
	}

	/**
	 * Class that extends the AsyncHttpResponseHandler. Displays a toast to
	 * indicate that the item was successfully added to the SALE table in the database.
	 */
	private class AddToSaleTableHandler extends AsyncHttpResponseHandler {
		
		@Override
		public void onSuccess( int code, Header[] headers, byte[] data ) {
			
			Log.d( TAG, "onSuccess code: " + code );

			Toast.makeText( SellItemActivity.this, "item added to sale table", Toast.LENGTH_SHORT ).show();
		}
	}
}