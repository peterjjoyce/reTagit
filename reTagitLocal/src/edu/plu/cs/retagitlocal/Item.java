package edu.plu.cs.retagitlocal;

import java.io.Serializable;
import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class stores information about each item
 * in our database for use in EditItemActivity,
 * BrowseItemActivity, SellItemActivity, and
 * ViewItemActivity
 */
public class Item implements Serializable {

	private String mTrackingNum;
	private String mItemName;
	private String mDescription;
	private String mCategory;
	private double mMinPrice;
	private double mAskingPrice;
	private int mSeller;
	private int mAddedBy;
	private String mDateAdded;
	private String mStatus;
	//private photo **How to store a photo?**
	
	/**
	 * This method stores information about each item into
	 * our database that is then accessed through several
	 * activities.
	 * 
	 * @param json the item that is passed through to get the
	 * 		  stored information about it
	 * @throws JSONException
	 */
	public Item( JSONObject json ) throws JSONException{

		mTrackingNum = json.getString( "tracking_number" );
		mItemName = json.getString( "name" );
		mDescription = json.getString( "description" );
		mCategory = json.getString( "category" );
		
		try{
			mAskingPrice = json.getDouble( "asking_price" );
		}
		catch( JSONException e ){
			mAskingPrice = -1.0;
		}
		
		try{
			mSeller = json.getInt( "seller" );
		}
		catch( JSONException e ){
			mSeller = -1;
		}
		
		try{
			mAddedBy = json.getInt( "added_by" );
		}
		catch( JSONException e ){
			mSeller = -1;
		}
		
		mDateAdded = json.getString( "date_added" );
		mStatus = json.getString( "status" );	
	}
	
	public static Comparator<Item> itemPriceComparator = new Comparator<Item>(){
		
		//Compare price by ascending order
		//TODO Need to fix for decimal difference and nulls
		public int compare( Item it1, Item it2 ){
			
			double iPrice1 = it1.getmAskingPrice();
			double iPrice2 = it2.getmAskingPrice();
			
			return (int)( iPrice1-iPrice2 );
		}
	};
	
	public static Comparator<Item> itemCategoryComparator = new Comparator<Item>(){
		
		//Compare category by ascending order
		public int compare( Item it1, Item it2 ){
			
			String iString1 = it1.getmCategory().toUpperCase();
			String iString2 = it2.getmCategory().toUpperCase();
			
			return iString1.compareTo(iString2);
		}
	};

	public static Comparator<Item> itemDateComparator = new Comparator<Item>(){
		
		//Compare date by ascending order
		public int compare( Item it1, Item it2 ){
			
			String iString1 = it1.getmDateAdded();
			String iString2 = it2.getmDateAdded();
			
			return iString1.compareTo( iString2 );
		}	
	};
	
	/**
	 * Overrides the default toString
	 * 
	 * @return returns the item's tracking number and name
	 */
	@Override
	public String toString(){
		return "Item: " + mTrackingNum + " " + mItemName;
	}

	/**
	 * Returns the item's tracking number
	 * 
	 * @return the mTrackingNum
	 */
	public String getmTrackingNum() {
		return mTrackingNum;
	}

	/**
	 * Sets the item's tracking number
	 * 
	 * @param mTrackingNum the mTrackingNum to set
	 */
	public void setmTrackingNum( String mTrackingNum ) {
		this.mTrackingNum = mTrackingNum;
	}

	/**
	 * Returns the item's name
	 * 
	 * @return the mItemName
	 */
	public String getmItemName() {
		return mItemName;
	}

	/**
	 * Sets the item's name
	 *
	 * @param mItemName the mItemName to set
	 */
	public void setmItemName( String mItemName ) {
		this.mItemName = mItemName;
	}

	/**
	 * Returns the item's description
	 * 
	 * @return the mDescription
	 */
	public String getmDescription() {
		return mDescription;
	}

	/**
	 * Sets the item's description
	 *
	 * @param mDescription the mDescription to set
	 */
	public void setmDescription( String mDescription ) {
		this.mDescription = mDescription;
	}

	/**
	 * Returns the item's category
	 * 
	 * @return the mCategory
	 */
	public String getmCategory() {
		return mCategory;
	}

	/**
	 * Sets the item's category
	 *
	 * @param mCategory the mCategory to set
	 */
	public void setmCategory( String mCategory ) {
		this.mCategory = mCategory;
	}

	/**
	 * Returns the item's minimum price
	 * 
	 * @return the mMinPrice
	 */
	public double getmMinPrice() {
		return mMinPrice;
	}

	/**
	 * Sets the item's minimum price
	 *
	 * @param mMinPrice the mMinPrice to set
	 */
	public void setmMinPrice( double mMinPrice ) {
		this.mMinPrice = mMinPrice;
	}

	/**
	 * Returns the item's asking price
	 * 
	 * @return the mAskingPrice
	 */
	public double getmAskingPrice() {
		return mAskingPrice;
	}

	/**
	 * Sets the item's asking price
	 *
	 * @param mAskingPrice the mAskingPrice to set
	 */
	public void setmAskingPrice( double mAskingPrice ) {
		this.mAskingPrice = mAskingPrice;
	}

	/**
	 * Returns the item's seller
	 * 
	 * @return the mSeller
	 */
	public int getmSeller() {
		return mSeller;
	}

	/**
	 * Sets the item's seller
	 *
	 * @param mSeller the mSeller to set
	 */
	public void setmSeller( int mSeller ) {
		this.mSeller = mSeller;
	}

	/**
	 * Returns the employee that added the item
	 * 
	 * @return the mAddedBy
	 */
	public int getmAddedBy() {
		return mAddedBy;
	}

	/**
	 * Sets the employee who added the item
	 *
	 * @param mAddedBy the mAddedBy to set
	 */
	public void setmAddedBy( int mAddedBy ) {
		this.mAddedBy = mAddedBy;
	}

	/**
	 * Returns the date the item was added
	 * 
	 * @return the mDateAdded
	 */
	public String getmDateAdded() {
		return mDateAdded;
	}

	/**
	 * Sets the date the item was added
	 *
	 * @param mDateAdded the mDateAdded to set
	 */
	public void setmDateAdded( String mDateAdded ) {
		this.mDateAdded = mDateAdded;
	}

	/**
	 * Returns the item's status
	 * 
	 * @return the mStatus
	 */
	public String getmStatus() {
		return mStatus;
	}

	/**
	 * Sets the item's status
	 *
	 * @param mStatus the mStatus to set
	 */
	public void setmStatus( String mStatus ) {
		this.mStatus = mStatus;
	}	
}