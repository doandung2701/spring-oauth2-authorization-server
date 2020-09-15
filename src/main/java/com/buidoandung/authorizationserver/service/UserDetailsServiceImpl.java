package com.buidoandung.authorizationserver.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buidoandung.authorizationserver.model.User;
import com.buidoandung.authorizationserver.model.UserAuthority;
import com.buidoandung.authorizationserver.model.dto.CustomGrantedAuthority;
import com.buidoandung.authorizationserver.model.dto.CustomUserDetails;
import com.buidoandung.authorizationserver.repository.UserRepository;

@Service
@Transactional	
public class UserDetailsServiceImpl implements UserDetailsService{
	private final UserRepository userRepository;
	public UserDetailsServiceImpl(@Autowired UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userRepository.findByUsername(username);
		if(user!=null) {
			CustomUserDetails customUserDetails = new CustomUserDetails();
			customUserDetails.setUserName(user.getUserName());
			customUserDetails.setPassword(user.getPassword());
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			for (UserAuthority authority : user.getUserAuthorities()) {
				authorities.add(new CustomGrantedAuthority(authority.getAuthority().getName()));
			}
			customUserDetails.setGrantedAuthorities(authorities);
			return customUserDetails;
		}
		throw new UsernameNotFoundException(username);
	}
}
