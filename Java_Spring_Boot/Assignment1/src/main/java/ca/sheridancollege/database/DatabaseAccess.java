package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//Contact section
	public void addContact(Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO agenda(name, phone, address, email, role) VALUES (:name, :phone, :address, :email, :role)";
		parameters.addValue("name", contact.getName());
		parameters.addValue("phone", contact.getPhone());
		parameters.addValue("address", contact.getAddress());
		parameters.addValue("email", contact.getEmail());
		parameters.addValue("role", contact.getRole());
		jdbc.update(query, parameters);
	}

	public ArrayList<Contact> getContacts(){
		String query = "SELECT * FROM agenda";
		ArrayList<Contact> agenda = (ArrayList<Contact>) jdbc.query(query, new BeanPropertyRowMapper<Contact>(Contact.class));

		return agenda;
	}

	public Contact getContactById(int id) {
		ArrayList<Contact> contacts = new ArrayList<>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM agenda WHERE id=:id";
		parameters.addValue("id", id);
		List<Map<String,Object>> rows = jdbc.queryForList(query,parameters);
		for (Map<String,Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name"));
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		if (contacts.size() >0)
			return contacts.get(0);
		return null;
	}
	
	public Contact modifyContact(@ModelAttribute Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE agenda SET name=:name, phone=:phone, address=:address, email=:email, role=:role WHERE id=:id";
		parameters.addValue("id", contact.getId());
		parameters.addValue("name", contact.getName());
		parameters.addValue("phone", contact.getPhone());
		parameters.addValue("address", contact.getAddress());
		parameters.addValue("email", contact.getEmail());
		parameters.addValue("role", contact.getRole());
		jdbc.update(query, parameters);
		return contact;
	}
	
	public void deleteContact(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM agenda WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	//User section
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user WHERE userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query,parameters, new BeanPropertyRowMapper<User>(User.class));
		if (users.size()>0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	public List<String> getRolesById(long userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		ArrayList<String> roles = new ArrayList<>();
		String query = "SELECT user_role.userId, sec_role.roleName "
				+ "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId "
				+ "AND userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String,Object>> rows = jdbc.queryForList(query,parameters);
		for (Map<String,Object> row: rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}
	
	public void createNewUser(String name, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("pass", passwordEncoder.encode(password));
		parameters.addValue("name", name);
		String query = "INSERT INTO sec_user (userName, encryptedPassword, ENABLED) " + 
				"VALUES (:name,:pass, 1)";
		jdbc.update(query, parameters);
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role (userId, roleId) " + 
				"VALUES (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}
	
	public ArrayList<Contact> getAdminContacts(){
		String query = "SELECT * FROM agenda WHERE role='Admin'";
		ArrayList<Contact> contacts = (ArrayList<Contact>) jdbc.query(query, new BeanPropertyRowMapper<Contact>(Contact.class));
		return contacts;
	}
	
	public ArrayList<Contact> getMemberContacts(){
		String query = "SELECT * FROM agenda WHERE role='Member'";
		ArrayList<Contact> contacts = (ArrayList<Contact>) jdbc.query(query, new BeanPropertyRowMapper<Contact>(Contact.class));
		return contacts;
	}
	
	public ArrayList<Contact> getGuestContacts(){
		String query = "SELECT * FROM agenda WHERE role='Guest'";
		ArrayList<Contact> contacts = (ArrayList<Contact>) jdbc.query(query, new BeanPropertyRowMapper<Contact>(Contact.class));
		return contacts;
	}
}
