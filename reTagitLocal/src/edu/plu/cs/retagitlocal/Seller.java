package edu.plu.cs.retagitlocal;

/**This class stores information about each seller in our database
 * for use in EditItemActivity, BrowseItemActivity, SellItemActivity,
 * and ViewItemActivity
*/
public class Seller {
	
	private String fname;
	private String lname;
	private String addr;
	private String phoneNo;
	
	public Seller( String firstName, String lastName, String address, String phoneNum ) {
		fname = firstName;
		lname = lastName;
		addr = address;
		phoneNo = phoneNum;
	}

	/**
	 * Returns the seller's first name
	 * 
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * Set's the seller's first name
	 * 
	 * @param fname the fname to set
	 */
	public void setFname( String fname ) {
		this.fname = fname;
	}

	/**
	 * Returns the seller's last name
	 * 
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * Set's the seller's last name
	 * 
	 * @param lname the lname to set
	 */
	public void setLname( String lname ) {
		this.lname = lname;
	}

	/**
	 * Returns the seller's address
	 * 
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * Set's the seller's address
	 * 
	 * @param addr the addr to set
	 */
	public void setAddr( String addr ) {
		this.addr = addr;
	}

	/**
	 * Returns the seller's phone number
	 * 
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * Set's the seller's phone number
	 * 
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo( String phoneNo ) {
		this.phoneNo = phoneNo;
	}		
}