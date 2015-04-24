package edu.plu.cs.retagitlocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import edu.plu.cs.retagitlocal.AddNewItemActivity.QRButtonListener;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity queries the database for each of the items.
 * The results are displaying in a clickable list view that 
 * will open the ViewItem activity.
 */
public class BrowseItemActivity extends Activity {

	private static final String TAG = "BrowseItemActivity.java";

	private ListView mItemListView;

	private ArrayList<Item> mItems;

	private LruCache<String,Bitmap> bitmapCache = new LruCache<String,Bitmap>(20);
	private Button QRCode;
	private EditText trackingNum;
	private Button submit;
	private final int ZBAR_REQUEST_ADD_ITEM = 1;
	private String num = null;

	private String dateDB = null;
	private String cat = null;
	private String desc = null;
	private String sellerDB = null;
	//private String min = null;
	private String asking = null;
	private String nameDB = null;
	
	private Button mSortPriceButton;
	private Button mSortCategoryButton;
	private Button mSortDateButton;

	/**
	 * This method instantiates layout on creation of this activity
	 * so that the layout is user responsive.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.browse_item_layout );
		
		Log.d( TAG, "onCreate" );
		
		mSortPriceButton = ( Button ) findViewById( R.id.sort_price_button );
        mSortPriceButton.setOnClickListener( new SortPriceButtonListener() );
        
        mSortDateButton = ( Button ) findViewById( R.id.sort_date_button );
        mSortDateButton.setOnClickListener( new SortDateButtonListener() );
        
        mSortCategoryButton = ( Button ) findViewById( R.id.sort_category_button );
        mSortCategoryButton.setOnClickListener( new SortCategoryButtonListener() ); 

		mItemListView = ( ListView )this.findViewById( R.id.itemsListView );
		
		mItems = new ArrayList<Item>();
		loadItems();
		
		mItemListView.setAdapter( new ItemListArrayAdapter(mItems) );
		
		mItemListView.setOnItemClickListener( new ItemClickListener() );
	}

	/**
	 * This method sorts the items in this list by price, with lowest cost
	 * being displayed first.
	 */
	public class SortPriceButtonListener implements OnClickListener{
		
		@Override
		public void onClick( View v ){
			
			Log.d( TAG, "Sorting by price" );
			
			//Sort by ascending
			Collections.sort( mItems, Item.itemPriceComparator );
			
			//Refresh the list
			( ( ArrayAdapter )mItemListView.getAdapter() ).notifyDataSetChanged();
		}	
	}
	
	/** 
	 * This method sorts the items in the list by date, with the
	 * earliest date of addition to the database being displayed
	 * first.
	 */
	public class SortDateButtonListener implements OnClickListener{
		
		@Override
		public void onClick( View v ){
			
			Log.d( TAG, "Sorting by date" );
			
			//Sort by ascending
			Collections.sort( mItems, Item.itemDateComparator );
			
			//Refresh the list
			( ( ArrayAdapter )mItemListView.getAdapter() ).notifyDataSetChanged();
		}
	}
	
	/**
	 * This method sorts the items in the list by category in 
	 * alphabetical order.
	 */
	public class SortCategoryButtonListener implements OnClickListener{
		
		@Override
		public void onClick( View v ){
			
			Log.d( TAG, "Sorting by category" );
			
			//Sort by ascending
			Collections.sort( mItems, Item.itemCategoryComparator );
			
			//Refresh the list
			( ( ArrayAdapter )mItemListView.getAdapter() ).notifyDataSetChanged();
		}
	}
	
	/** 
	 * Private method that queries the database for a list of items.
	 */
	 private void loadItems(){
		ItemDataResponseHandler handler = new ItemDataResponseHandler();
	 	MyHTTPclient.get( MyHTTPclient.BROWSE_ITEM_URL, null, handler );
	 }

	/**
	 * This class responds when an item is clicked. The tracking number
	 * of the item is used to query the database for more information about the item,
	 * which is sent to the ViewItem Activity.
	 */
	private class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick( AdapterView<?>parent, View view, int position, long id ){
			
			Item i = ( Item )mItemListView.getAdapter().getItem( position );
			
			Log.d( TAG, i.toString() );
			
			RequestParams params = new RequestParams();
			params.put( "tracking_number", i.getmTrackingNum() );
			num = i.getmTrackingNum();
			
			Log.d( TAG, "Sending tracking_number: " + i.getmTrackingNum() );
			
			FillFieldsHandler handler = new FillFieldsHandler();
			MyHTTPclient.post( MyHTTPclient.GET_ITEM_INFO, params, handler );
			
			Log.d( TAG, "Post" );
		}
	}
	
	/**
	 * This handler processes the data received from the database and populates
	 * the list view with it.
	 */
	private class ItemDataResponseHandler extends JsonHttpResponseHandler{
		
		@Override
		public void onSuccess( JSONArray response ){
			
			for( int i = 0; i < response.length(); i++ ){
				try{
					Item item = new Item( response.getJSONObject( i ) );
					mItems.add( item );
				} catch( JSONException e ){
					Log.e( TAG, "Failed to grab response: " + e.getMessage() );
				}
			}
			( ( ArrayAdapter )mItemListView.getAdapter() ).notifyDataSetChanged();
		}
		
		@Override
		public void onFailure( Throwable e, JSONObject errorResponse ){
			super.onFailure( e, errorResponse );
			Log.e( TAG, "Failed data retrival: " + e.getMessage() );
		}
	}
	 
	/**
	 * This class links the browse_item_list_item_layout with the browse_item_layout,
	 * and formats how the information from the items should be displayed.
	 */
	public class ItemListArrayAdapter extends ArrayAdapter<Item>{

		public ItemListArrayAdapter( ArrayList<Item> items ){
			super( BrowseItemActivity.this, 0, items );
		}

		@Override
		public View getView( int position, View convertView, ViewGroup parent ){

			if( convertView == null ){
				convertView = getLayoutInflater().inflate( R.layout.browse_item_list_item_layout, null );
			}

			Item i = getItem( position );

			TextView dtv = ( TextView )convertView.findViewById( R.id.dateTextView );
			TextView ctv = ( TextView )convertView.findViewById( R.id.categoryTextView );
			TextView atv = ( TextView )convertView.findViewById( R.id.priceTextView );

			dtv.setText( i.getmDateAdded() );
			ctv.setText( i.getmCategory() );
			atv.setText( "$" + i.getmAskingPrice() );

			/*
			ImageView iv = ( ImageView )convertView.findViewById( R.id.itemThumbView );

			String relativeUrl = MyHttpClient.PHOTO_REL_URL + "/" + i.getFileName();

			Bitmap bmap = bitmapCache.get( relativeUrl );

			if( bmp == null ){

				iv.setImageResource( R.drawable.fox );

				ReceiveThumbnailHandler handler = new ReceiveThumbnailHandler( relativeUrl, iv );
				MyHTTPClient.get( relativeUrl, null, handler );
			}
			else{
				iv.setImageBitmap( bmap );
			}
			 */
			return convertView;
		}
		
		/*
		private class ReceiveThumbnailHandler extends AsyncHttpResponseHandler {
			
			private String url;
			private ImageView iv;
			
			public ReceiveThumbnailHandler( String relativeUrl, ImageView view ) {
				this.url = relativeUrl;
				this.iv = view;
			}
			
			@Override
			public void onSuccess( int code, Header[] header, byte[] data ) {

				// Decode the image data into a Bitmap object
				Bitmap bmap = BitmapFactory.decodeByteArray( data, 0, data.length );

				// Put the Bitmap into the cache
				bitmapCache.put( url, bmap );

				Log.d( TAG,"Recieved bitmap: " + bmap.getWidth() + " " + bmap.getHeight() );

				// Since the data has changed, ask the view to update
				notifyDataSetChanged();
			}
		}
		*/
	}

	/**
	 * Initialize the contents of the Activity's standard options menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * You can safely hold on to menu (and any items created from it), making modifications to it as desired, 
	 * until the next time onCreateOptionsMenu() is called. When you add items to the menu,
	 * you can implement the Activity's onOptionsItemSelected(MenuItem) method to handle them there.
	 * 
	 * @param menu The options menu in which you place your items.
	 * 
	 * Javadoc comment courtesy of http://developer.android.com/reference/android/app/Activity.html
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.browse_item, menu );
		return true;
	}
	
	
	/**
	 * Queries the database for fields of an item when an item is clicked on in the browseItemsView.
	 * This information is placed into an intent and sent to the view item activity
	 */
	private class FillFieldsHandler extends JsonHttpResponseHandler{

		@Override
		public void onSuccess( JSONArray response ) {
			
			try {
				Log.d( TAG, "Let's Fill Stuff" );
				
				JSONObject item = response.getJSONObject( 0 );
				
				nameDB = item.getString( "name" );
				Toast.makeText( BrowseItemActivity.this, "name is " + nameDB, Toast.LENGTH_SHORT ).show();
				desc = item.getString( "description" );
				cat = item.getString( "category" );
				asking = item.getString( "asking_price" );
				sellerDB = item.getString( "seller" );
				dateDB = item.getString( "date_added" );

				Toast.makeText( BrowseItemActivity.this, "seller = " + sellerDB, Toast.LENGTH_SHORT ).show();
				
				Intent i = new Intent( BrowseItemActivity.this, ViewItemActivity.class );
				i.putExtra( "tracking_num", num );
				i.putExtra( "name", nameDB );
				i.putExtra( "description", desc );
				i.putExtra( "category", cat );
				i.putExtra( "asking_price", asking );
				i.putExtra( "seller", sellerDB );
				i.putExtra( "date_added", dateDB );
				
				Log.d( TAG, "I'm gonna wreck it" );
				
				startActivity(i);
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