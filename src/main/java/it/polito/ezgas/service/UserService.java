package it.polito.ezgas.service;

import java.util.List;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.dto.IdPw;

/**
 * Created by softeng on 27/4/2020.
 *
 * Update V2 :
 * 1) issue 22/23	--->		InvalidUserException must be thrown when a non existing userId is set for
 *                              "increaseUserReputation" and "decreaseUserReputation" APIs.
 *                              For the API "deleteUser" the behaviour should be :
 *                                  - negative Id : InvalidUserException
 *                                  - non existing user Id : false as returned value
 */
public interface UserService {
	
	/*
	 * Queries the database and returns the UserDto corresponding to the userId passed as parameter.
	 * Throws an exception in case of invalid (negative) userId
	 * Returns null if no user is found with the given Id
	 */
    UserDto getUserById(Integer userId) throws InvalidUserException;
    
    /*
     * Stores the user passed as parameter in the application database
     */
    UserDto saveUser(UserDto userDto);
    
    /*
     * Returns an ArrayList with all the Users in the database.
     * Returns an empty ArrayList if no user is registered in the database
     */
    List<UserDto> getAllUsers();
    
    /*
     * Deletes the user with the given Id from the database.
     * Throws an exception in case of invalid (negative) userId
   	 * Returns true if the user is deleted, false otherwise
     */
    Boolean deleteUser(Integer userId) throws InvalidUserException;
    
    /*
     * Performs a login in the application
     */
    LoginDto login(IdPw credentials) throws InvalidLoginDataException;
    
    
    /*
     * Increases by 1 the user Reputation (until maximum value equal to 5)
     * Throws an exception in case of invalid (negative) userId or in case of non existing user with the given userId
     * Returns the current value of user reputation
     */
    Integer increaseUserReputation(Integer userId) throws InvalidUserException;
    
    
    /*
     * Decreases by 1 the user Reputation (until minimum value equal to -5)
     * Throws an exception in case of invalid (negative) userId or in case of non existing user with the given userId
     * Returns the current value of user reputation
     */  
    Integer decreaseUserReputation(Integer userId) throws InvalidUserException;
}
