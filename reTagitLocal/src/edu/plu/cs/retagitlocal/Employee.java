package edu.plu.cs.retagitlocal;

import org.json.JSONObject;

/**
 * This class holds information about employees
 */
public class Employee {

	private String fname;
	private String lname;
	private String phone;
	private String password;
	private boolean is_manager;
	
	/**
	 * Constructor
	 * 
	 * @param empID
	 * @param empName
	 * @param phone
	 * @param password
	 */
	public Employee( String fname, String lname, String phone, String password, boolean is_manager ) {
		
		super();
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.password = password;
		this.is_manager = is_manager;
	}
	
	/**
	 * First name getter method
	 * 
	 * @return the empID
	 */
	public String getfname() {
		return fname;
	}
	
	/**
	 * Last name getter method
	 * 
	 * @return the empName
	 */
	public String getlname() {
		return lname;
	}
	
	/**
	 * Phone number getter method
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * Password getter method
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Is manager getter method
	 * 
	 * @return the is_manager
	 */
	public boolean getIs_manager() {
		return is_manager;
	}
}