package ca.sheridancollege.security;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import ca.sheridancollege.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Find user based on username
		ca.sheridancollege.beans.User user = da.findUserAccount(username);
		//If the user doesn't exit throw exception
		if(user==null) {
			System.out.println("User not found:"+ username);
			throw new UsernameNotFoundException("User "+ username+ " was not found in the database.");
		}
		//Get list of roles
		List<String> roleNames = da.getRolesById(user.getUserId()); 
		//Change list of roles to a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for(String role: roleNames) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		UserDetails userDetails = (UserDetails) new User(user.getUserName(),
				user.getEncryptedPassword(), grantList);
		
		return userDetails;
	}

}
