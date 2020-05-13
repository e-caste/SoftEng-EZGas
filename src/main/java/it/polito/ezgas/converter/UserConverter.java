package it.polito.ezgas.converter;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

public class UserConverter {

	public UserConverter() {
	}

	UserDto convertEntityToDto(User userEntity) {
		UserDto userDto = new UserDto();
		userDto.setUserId(userEntity.getUserId());
		userDto.setEmail(userEntity.getEmail());
		userDto.setPassword(userEntity.getPassword());
		userDto.setAdmin(userEntity.getAdmin());
		userDto.setReputation(userEntity.getReputation());
		userDto.setUserName(userEntity.getUserName());
		return userDto;
	}

	User convertDtoToEntity(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAdmin(userDto.getAdmin());
		user.setReputation(userDto.getReputation());
		user.setUserName(userDto.getUserName());
		return user;
	}
}
