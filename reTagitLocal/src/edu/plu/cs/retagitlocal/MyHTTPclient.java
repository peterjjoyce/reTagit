package edu.plu.cs.retagitlocal;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/** 
 * This is a central location for using the AsyncHttpClient to make GET or 
 * POST requests.  We store here convenient shortcuts to commonly accessed
 * base (relative) URLs.
 * 
 * The AsyncHttpClient here is essentially treated as a singleton, but is
 * not exposed to outside code.
 */
public class MyHTTPclient {

	public static final String BASE_URL = "http://YOURURLHERE.com";
	//public static final String PHOTOS_REL_URL = "/photos";
	//public static final String THUMB_REL_URL = PHOTOS_REL_URL + "/128x128";
	//public static final String LARGE_REL_URL = PHOTOS_REL_URL + "/600x600";
	//public static final String PHOTO_JSON_REL_URL = "/~reTagit320/photos.php";
	//public static final String SET_CAPTION_REL_URL = "/~reTagit320/setCaption.php";
	public static final String SET_TRACKING_NUMBER_REL_URL = "/~retagit320/addInventory.php";
	public static final String SET_ID_NUMBER_REL_URL = "/~retagit320/addEmployee.php";
	public static final String SET_SELLER_REL_URL = "/~retagit320/addSeller.php";
	public static final String VIEW_ITEM_URL = "/~retagit320/viewItem.php";
	public static final String GET_ITEM_INFO = "/~retagit320/getItemForSelling.php";
	public static final String CHECK_USER_URL = "/~retagit320/logOn.php";
	public static final String DELETE_ITEM_URL = "/~retagit320/deleteItem.php";
	public static final String ADD_TO_SALE_URL = "/~retagit320/addToSaleTable.php";
	public static final String BROWSE_ITEM_URL = "/~retagit320/browseItems.php";
	public static final String EDIT_ITEM_URL = "/~retagit320/updateInventory.php";
	
	
	/**
	 * The AsyncHttpClient singleton, used for all GET/POST requests.
	 */
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	/**
	 * Send a GET request to the client at the given relative URL.
	 * 
	 * @param url a relative URL based on BASE_URL
	 * @param params any parameters to be sent along with the request
	 * @param responseHandler an AsyncHttpResponseHandler to be called when the results
	 *        are returned from the server.
	 */
	public static void get( String url, RequestParams params, AsyncHttpResponseHandler responseHandler ) {
		
		client.get( getAbsoluteUrl( url ), params, responseHandler );
	}
	
	/**
	 * Send a POST request to the client at the given relative URL.
	 * 
	 * @param url a relative URL based on BASE_URL
	 * @param params any parameters to be sent along with the request
	 * @param responseHandler an AsyncHttpResponseHandler to be called when the results
	 *        are returned from the server.
	 */
	public static void post( String url, RequestParams params, AsyncHttpResponseHandler responseHandler ) {
		
		client.post( getAbsoluteUrl( url ), params, responseHandler );
	}
	
	/**
	 * Get the url of a webpage from the url of our server and path of resource
	 * 
	 * @param relativeUrl the path of resource
	 * @return the entire url of the server and the path of resource
	 */
	private static String getAbsoluteUrl( String relativeUrl ) {
		
		return BASE_URL + relativeUrl;
	}
}