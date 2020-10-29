package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class ContactController {
	
	@Autowired
	public DatabaseAccess access;
	
	@GetMapping("/") //localhost:8080/
	public String goContact(Model model) {
		model.addAttribute("contact", new Contact());
		return "contact.html";
	}
	
	@GetMapping("/add")
	public String addContact(@ModelAttribute Contact contact) {
		access.addContact(contact);
		return "redirect:/";
	}
	
	@GetMapping("/usr/view")
	public String viewContact(Model model, Authentication authentication) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		List<String> roleNames = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleNames.add(ga.getAuthority());
		}
		for (String role: roleNames) {
			if (role.equals("ROLE_ADMIN"))
				contacts.addAll(access.getAdminContacts());
			if (role.equals("ROLE_MEMBER"))
				contacts.addAll(access.getMemberContacts());
			if (role.equals("ROLE_GUEST"))
				contacts.addAll(access.getGuestContacts());				
		}
		model.addAttribute("agenda", contacts);
		
		return "usr/viewContact.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editContact(Model model, @PathVariable int id) {
		Contact c = access.getContactById(id);
		model.addAttribute("contact", c);
		return "usr/edit.html";
	}
	
	@GetMapping("/modify")
	public String modifyContact(@ModelAttribute Contact contact) {
		access.modifyContact(contact);
		return "redirect:/usr/view";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable int id) {
		access.deleteContact(id);
		return "redirect:/usr/view";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	
	@GetMapping("/access-denied")
	public String deniedPage() {
		return "/error/access-denied.html";
	}
	
	@GetMapping("/register")
	public String registrationrPage() {
		return "registration.html";
	}
	
	@PostMapping("/register")
	public String registrationProcess(@RequestParam String name, @RequestParam String password, 
			@RequestParam(required=false,defaultValue="false")boolean admin,
			@RequestParam(required=false,defaultValue="false")boolean member,
			@RequestParam(required=false,defaultValue="false")boolean guest) {
		access.createNewUser(name, password);
		long userId = access.findUserAccount(name).getUserId();
		if (admin==true) 
			access.addRole(userId, 1);
		if (member==true) 
			access.addRole(userId, 2);
		if (guest==true)
			access.addRole(userId, 3);
		
		return "redirect:/";
	}
}
