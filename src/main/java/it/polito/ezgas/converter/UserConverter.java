package it.polito.ezgas.converter;

import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

public class UserConverter {

	public UserConverter() {
	}

	public static UserDto convertEntityToDto(User userEntity) {
		UserDto userDto = new UserDto();
		userDto.setUserId(userEntity.getUserId());
		userDto.setEmail(userEntity.getEmail());
		userDto.setPassword(userEntity.getPassword());
		userDto.setAdmin(userEntity.getAdmin());
		userDto.setReputation(userEntity.getReputation());
		userDto.setUserName(userEntity.getUserName());
		return userDto;
	}

	public static User convertDtoToEntity(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAdmin(userDto.getAdmin());
		if (userDto.getReputation() == null) {
			user.setReputation(0);
		} else {
			user.setReputation(userDto.getReputation());
		}
		user.setUserName(userDto.getUserName());
		return user;
	}

	public static LoginDto convertEntityToLoginDto(User userEntity){
		LoginDto loginDto = new LoginDto();
		loginDto.setUserId(userEntity.getUserId());
		loginDto.setUserName(userEntity.getUserName());
		// TODO: check where to get token
		loginDto.setToken("token");
		loginDto.setEmail(userEntity.getEmail());
		loginDto.setReputation(userEntity.getReputation());
		loginDto.setAdmin(userEntity.getAdmin());
		return loginDto;
	}
}
