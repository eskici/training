package com.moss.project.eneasy.service;

import java.util.Arrays;
import java.util.List;

import com.moss.project.eneasy.dto.UserDto;
import com.moss.project.eneasy.entity.Authority;
import com.moss.project.eneasy.entity.AuthorityType;
import com.moss.project.eneasy.exception.UserAlreadyExistException;
import com.moss.project.eneasy.repository.UserRepository;
import lombok.AllArgsConstructor;

import com.moss.project.eneasy.repository.UserDAO;
import com.moss.project.eneasy.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class UserService{

	private UserRepository userRepository;
	
	public Long registerNewUserAccount(UserDto userDto){
		User user = User.builder()
				.email(userDto.getEmail())
				.username(userDto.getUsername())
				.password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
				.authorities(Arrays.asList(new Authority(AuthorityType.ROLE_USER)))
				.build();

		return userRepository.save(user).getId();
	}

	public User findUserById(String userId) {
		return userRepository.findById(Long.valueOf(userId)).orElse(null);
	}
	public void existEmail(String email) throws UserAlreadyExistException {
		if(userRepository.findByEmail(email)!= null )
			throw new UserAlreadyExistException();
	}

	public User existUsername(String username) throws UserAlreadyExistException {
		if(userRepository.findByUsername(username)!= null )
			throw new UserAlreadyExistException();
	}
}
