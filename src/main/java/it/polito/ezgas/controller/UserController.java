package it.polito.ezgas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.service.UserService;
import it.polito.ezgas.utils.Constants;

/**
 * Created by softeng on 27/4/2020.
 */
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(Constants.GET_USER_BY_ID)
	public UserDto getUserById(@PathVariable Integer userId) {
		try {
			return userService.getUserById(userId);
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return null;
		}
	}

	@RequestMapping(Constants.GET_ALL_USERS)
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value= Constants.SAVE_USER, method= RequestMethod.POST)
	public UserDto saveUser(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}
	
	@RequestMapping(value= Constants.DELETE_USER, method= RequestMethod.DELETE)
	public Boolean deleteUser(@PathVariable Integer userId) {
		try {
			return userService.deleteUser(userId);
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	
	@RequestMapping(value= Constants.INCREASE_REPUTATION, method= RequestMethod.POST)
	public Integer increaseUserReputation(@PathVariable Integer userId) {
		try {
			return userService.increaseUserReputation(userId);
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	
	@RequestMapping(value= Constants.DECREASE_REPUTATION, method= RequestMethod.POST)
	public Integer decreaseUserReputation(@PathVariable Integer userId) {
		try {
			return userService.decreaseUserReputation(userId);
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return 0;
		}
	}
	@RequestMapping(value= Constants.LOGIN, method= RequestMethod.POST)
	public LoginDto login(@RequestBody IdPw credentials) {
		try {
			return userService.login(credentials);
		} catch (InvalidLoginDataException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return null;
		}
	}
	
}
