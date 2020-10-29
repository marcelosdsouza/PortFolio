package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Class Constructor with 0 parameters
@AllArgsConstructor //Class Constructor with 4 parameters
@Data //Getters, Setters, toString, HashMap, canEqual methods
public class Contact {
	
	//Fields required to create a contact in your agenda.
	private int id;
	private String name;
	private String phone;
	private String address;
	private String email;
	private String role;
	
	@Override
	public String toString() {
		String format = String.format("Name: %s\nPhone Number: %s\nAddress: %s\nE-mail: %s", name, phone, address, email);
		return format;
		
	}
}
