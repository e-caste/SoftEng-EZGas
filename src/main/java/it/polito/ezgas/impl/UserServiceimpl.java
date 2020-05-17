package it.polito.ezgas.impl;

import java.util.ArrayList;

import java.util.List;

import it.polito.ezgas.repository.UserRepository;
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
		User user = userRepository.findById(userId);
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		return UserConverter.convertEntityToDto(user);
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		User user = userRepository.findById(userDto.getUserId());
		UserDto uDto = null;

		// update existing user
		if (user != null) {
			user.setUserName(userDto.getUserName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setReputation(userDto.getReputation());

			userRepository.save(user);
			uDto = UserConverter.convertEntityToDto(user);
		} else {
			user = UserConverter.convertDtoToEntity(userDto);
			if(!user.getAdmin()){
				userRepository.save(user);
				uDto = UserConverter.convertEntityToDto(user);
			}
		}

		return uDto;
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
		User user = userRepository.findById(userId);
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		if(!user.getAdmin()){
			userRepository.delete(userId);
			return true;
		} else {
			return false;
		}
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
		return UserConverter.convertEntityToLoginDto(user);
	}

	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		User user = userRepository.findById(userId);
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		Integer actualRep = user.getReputation();
		Integer newRep;
		if(actualRep < 5){
			newRep = actualRep + 1;
			user.setReputation(newRep);
			userRepository.save(user);
		} else {
			newRep = actualRep;
		}
		return newRep;
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		User user = userRepository.findById(userId);
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		Integer actualRep = user.getReputation();
		Integer newRep;
		if(actualRep > -5){
			newRep = actualRep - 1;
			user.setReputation(newRep);
			userRepository.save(user);
		} else {
			newRep = actualRep;
		}
		return newRep;
	}
}
