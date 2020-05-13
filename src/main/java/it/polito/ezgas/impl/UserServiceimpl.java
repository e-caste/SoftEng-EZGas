package it.polito.ezgas.impl;

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
		User user = userRepository.findByUserName(userDto.getUserName());
		// update existing user
		if (user != null) {
			user.setUserName(userDto.getUserName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setReputation(userDto.getReputation());
		}
		// dto -> entity -> save -> entity -> dto
		return UserConverter.convertEntityToDto(
				userRepository.save(
						UserConverter.convertDtoToEntity(userDto)));
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		String username = credentials.getUser();
		String password = credentials.getPw();

		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new InvalidLoginDataException("User does not exist.");
		}
		if (!user.getPassword().equals(password)) {
			throw new InvalidLoginDataException("Wrong password.");
		}

		// TODO: check where to get token
		return new LoginDto(
				user.getUserId(),
				user.getUserName(),
				"token",
				user.getEmail(),
				user.getReputation()
				);
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
