package com.example.game.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.game.Repository.UserRepository;
import com.example.game.entity.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	


	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		User user =userRepository.findByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserPrincipal(user);
	}
	
	

    

}
