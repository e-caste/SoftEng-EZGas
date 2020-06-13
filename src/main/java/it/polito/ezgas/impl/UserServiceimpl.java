package it.polito.ezgas.impl;

import java.util.ArrayList;

import java.util.List;

import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.service.UserService;


@Service
public class UserServiceimpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		//User user = userRepository.findOne(userId);
		User user = null;
		List <User> allU= userRepository.findAll();
		for (User u : allU){
			if(u.getUserId() == userId){
				user = u;
			}
		}
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		return UserConverter.convertEntityToDto(user);
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		//User user = userRepository.findOne(userDto.getUserId());
		User user = null;
		List <User> allU= userRepository.findAll();
		for (User u : allU){
			if(u.getUserId() == userDto.getUserId()){
				user = u;
			}
		}
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
			boolean existingEmail = false;
			List<User> allUsers =userRepository.findAll();
			for(User u : allUsers){
				if((u.getEmail().equals(userDto.getEmail()))){
					existingEmail = true;
				}
			}
			//if(userRepository.findByEmail(userDto.getEmail()) == null){
			if(existingEmail == false){
				userRepository.save(user);
				uDto = UserConverter.convertEntityToDto(user);
			} else {
				User userByEmail = null;

				for(User u : allUsers){
					if((u.getEmail().equals(userDto.getEmail()))){
						userByEmail = u;
					}
				}
				//uDto = UserConverter.convertEntityToDto(userRepository.findByEmail(userDto.getEmail()));
				uDto = UserConverter.convertEntityToDto(userByEmail);
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
		//User user = userRepository.findOne(userId);
		User user = null;
		List <User> allU= userRepository.findAll();
		for (User u : allU){
			if(u.getUserId() == userId){
				user = u;
			}
		}
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		try {
			userRepository.delete(userId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		String email = credentials.getUser();
		String password = credentials.getPw();
		User user = null;
		List<User> allUsers =userRepository.findAll();
		for(User u : allUsers){
			if((u.getEmail().equals(email))){
				user = u;
			}
		}

		//User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidLoginDataException("User does not exist.");
		}
		if (!user.getPassword().equals(password)) {
			throw new InvalidLoginDataException("Wrong password.");
		}

		return UserConverter.convertEntityToLoginDto(user);
	}

	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		//User user = userRepository.findOne(userId);
		User user = null;
		List <User> allU= userRepository.findAll();
		for (User u : allU){
			if(u.getUserId() == userId){
				user = u;
			}
		}
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		Integer currentReputation = user.getReputation();
		Integer newRep;
		if(currentReputation < 5){
			newRep = currentReputation + 1;
			user.setReputation(newRep);
			userRepository.save(user);
		} else {
			newRep = currentReputation;
		}
		return newRep;
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		//User user = userRepository.findOne(userId);
		User user = null;
		List <User> allU= userRepository.findAll();
		for (User u : allU){
			if(u.getUserId() == userId){
				user = u;
			}
		}
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		Integer currentReputation = user.getReputation();
		Integer newRep;
		if(currentReputation > -5){
			newRep = currentReputation - 1;
			user.setReputation(newRep);
			userRepository.save(user);
		} else {
			newRep = currentReputation;
		}
		return newRep;
	}
}
