package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.scm.entity.User;
import com.scm.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User loadedUser = this.userRepository.loadUserByUsername(username);
		if(loadedUser==null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		UserDetails userDetails = new UserDetailsImpl(loadedUser);
		return userDetails;
	}

}
