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
 * This activity allows the user to edit an item after clicking on it in
 * browse items and navigating here from view item. The form for the item is filled in by
 * current item information, most of which is editable.
 */
public class EditItemActivity extends Activity {
	
	private ImageView photoImage;
	private TextView trackingNum;
	private TextView date;
	private EditText category;
	private EditText description;
	private TextView seller;
	private EditText minPrice;
	private EditText askingPrice;
	private EditText name;
	private Button submit;
	private String track_num;
	private final String TAG = "EditItemActivity";
	private String dateDB = null;
	private String cat = null;
	private String desc = null;
	private String sellerDB = null;
	//private String min = null;
	private String asking = null;
	private String nameDB = null;

	/**
	 * In this method, we instantiate the components, set the onClick listener
	 * for the Submit button, and unpack information from the intent that
	 * began this activity.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.edit_item_layout) ;
		
		//name = ( TextView ) findViewById( R.id.name );
		trackingNum = ( TextView ) findViewById( R.id.item_number );
		//photoImage = ( ImageView ) findViewById( R.id.photo );
		date = ( TextView ) findViewById( R.id.date );
		category = ( EditText ) findViewById( R.id.category );
		description = ( EditText ) findViewById( R.id.description );
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
		//name.setText( " " + nameDB, TextView.BufferType.EDITABLE );
		date.setText( " " + dateDB );
		category.setText( " " + cat, TextView.BufferType.EDITABLE );
		description.setText( " " + desc, TextView.BufferType.EDITABLE );
		seller.setText( sellerDB );
		askingPrice.setText( asking, TextView.BufferType.EDITABLE );
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
		getMenuInflater().inflate( R.menu.sell_item, menu );
		return true;
	}

	/**
	 * This listener takes information contained in the form
	 * and sends it to the database. The information in the database is updated to reflect what
	 * was in the form.
	 */
	public class SubmitButtonListener implements OnClickListener {

		@Override
		public void onClick( View v ) {
			
			Log.d( TAG, "deleting item: " + track_num );
			
			RequestParams params = new RequestParams();
			params.put( "tracking_number", track_num );
			params.put( "description", description.getText().toString() );
			params.put( "asking_price", askingPrice.getText().toString() );
			params.put( "category", category.getText().toString() );

			EditItemHandler handler = new EditItemHandler();
			MyHTTPclient.post( MyHTTPclient.EDIT_ITEM_URL, params, handler );
		}
	}

	/**
	 * Private handler that specifies whether the update of the item information succeeded
	 */
	private class EditItemHandler extends AsyncHttpResponseHandler{
		
		@Override
		public void onSuccess( int code, Header[] headers, byte[] data ) {
			Log.d( TAG, "onSuccess code: " + code );
			Toast.makeText( EditItemActivity.this, "item editted", Toast.LENGTH_SHORT ).show();
		}
	}	
}