package edu.plu.cs.retagitlocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is the activity for viewing an item's information. It is accessed
 * through the BrowseItemActivity and displays the chosen item's information
 * that is stored in the database.
 */
public class ViewItemActivity extends Activity {

	private static final String TAG = "ViewItemActivity.java";
	
	private ImageView photoImage;
	private TextView trackingNum;
	private TextView date;
	private TextView category;
	private TextView description;
	private TextView seller;
	private TextView minPrice;
	private TextView askingPrice;
	private Button sellItem;
	private Button editItem;
	
	private String trackingNumStr;
	private String dateStr;
	private String categoryStr;
	private String descriptionStr;
	private String sellerStr;
	private String minPriceStr;
	private String askingPriceStr;
	private String nameStr;
	
	/**
	 * Starts when this activity starts. Instantiates each of the buttons.
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.view_item_layout );
		
		trackingNum = ( TextView ) findViewById( R.id.item_number );
		photoImage = ( ImageView ) findViewById( R.id.photo );
		date = ( TextView ) findViewById( R.id.date );
		category = ( TextView ) findViewById( R.id.category );
		description = ( TextView ) findViewById( R.id.description );
		seller = ( TextView ) findViewById( R.id.seller );
		minPrice = ( TextView ) findViewById( R.id.minPrice );
		askingPrice = ( TextView ) findViewById( R.id.askingPrice );
		sellItem = ( Button ) findViewById( R.id.sell_button );
		editItem = ( Button ) findViewById( R.id.edit_button );
		sellItem.setOnClickListener( new SellClickListener() );
		editItem.setOnClickListener( new EditItemClickListener() );
		
		Bundle extras = getIntent().getExtras();
		if ( extras != null ) {
			trackingNumStr = extras.getString( "tracking_num" );
			nameStr = extras.getString( "name" );
			dateStr = extras.getString( "date_added" );
			categoryStr = extras.getString( "category" );
			descriptionStr = extras.getString( "description" );
			sellerStr = extras.getString( "seller" );
			askingPriceStr = extras.getString( "asking_price" );
		}
		
		trackingNum.setText( " " + trackingNumStr );
		//name.setText( " " + nameStr );
		date.setText( " " + dateStr );
		category.setText( " " + categoryStr );
		description.setText( " " + descriptionStr );
		seller.setText( sellerStr );
		askingPrice.setText( askingPriceStr ); 	
	}
	
	/**
	 * Listener for the AccountSettings button. When the button is clicked, 
	 * this listener changes the view to the AccountSettings view
	 */
	public class SellClickListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			Intent i = new Intent( ViewItemActivity.this, SellItemActivity.class );
			i.putExtra( "tracking_num", trackingNumStr);
			i.putExtra( "name", nameStr );
			i.putExtra( "description", descriptionStr );
			i.putExtra( "category", categoryStr );
			i.putExtra( "asking_price", askingPriceStr );
			i.putExtra( "seller", sellerStr );
			i.putExtra( "date_added", dateStr );
			startActivity( i );
		}
	}
	
	/**
	 * Listener for the AccountSettings button. When the button is clicked, 
	 * this listener changes the view to the AccountSettings view
	 */
	public class EditItemClickListener implements OnClickListener {
		
		@Override
		public void onClick( View v ) {
			Intent i = new Intent( ViewItemActivity.this, EditItemActivity.class );
			i.putExtra( "tracking_num", trackingNumStr );
			i.putExtra( "name", nameStr );
			i.putExtra( "description", descriptionStr );
			i.putExtra( "category", categoryStr );
			i.putExtra( "asking_price", askingPriceStr );
			i.putExtra( "seller", sellerStr );
			i.putExtra( "date_added", dateStr );
			startActivity( i );
		}
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
		getMenuInflater().inflate( R.menu.browse_item, menu );
		return true;
	}
}