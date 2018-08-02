package ua.com.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.com.company.entity.Person;
import ua.com.company.serviceInterface.IpersonService;

@Service
public class CustomUserDetaolService implements UserDetailsService {

	@Autowired
	private IpersonService persService;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		Person person = persService.findByName(arg0);
		UserBuilder builder = null;
		if (person == null) {
			throw new UsernameNotFoundException("user not found");
		}
		builder = org.springframework.security.core.userdetails.User.withUsername(arg0);
		builder.password(person.getNickname());
		builder.roles("USER");

		return builder.build();
	}

}
