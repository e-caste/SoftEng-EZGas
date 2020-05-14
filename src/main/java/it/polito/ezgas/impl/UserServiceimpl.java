package it.polito.ezgas.impl;

import java.util.ArrayList;
import java.util.List;

import it.polito.ezgas.Repository.UserRepository;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.service.UserService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class UserServiceimpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		// dto -> entity -> save -> entity -> dto
		User user = userRepository.findById(userDto.getUserId());
		// update existing user
		if (user != null) {
			user.setUserName(userDto.getUserName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setReputation(userDto.getReputation());
		} else {
			user = UserConverter.convertDtoToEntity(userDto);
		}
		userRepository.save(user);
		return UserConverter.convertEntityToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(UserConverter.convertEntityToDto(user));
		}
		return userDtos;
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		String email = credentials.getUser();
		String password = credentials.getPw();

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidLoginDataException("User does not exist.");
		}
		if (!user.getPassword().equals(password)) {
			throw new InvalidLoginDataException("Wrong password.");
		}

		// TODO: check where to get token
		LoginDto loginDto = new LoginDto(
					user.getUserId(),
					user.getUserName(),
					"token",
					user.getEmail(),
					user.getReputation()
				);
		loginDto.setAdmin(user.getAdmin());
		return loginDto;
	}

	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
