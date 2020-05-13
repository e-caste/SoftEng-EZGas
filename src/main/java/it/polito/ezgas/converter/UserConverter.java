package it.polito.ezgas.converter;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

public class UserConverter {
	
	
	
	
	UserDto convertEntityToEntity(User us) {
		UserDto Udto=new UserDto();
		Udto.setUserId(us.getUserId());
		Udto.setEmail(us.getEmail());
		Udto.setPassword(us.getPassword());
		Udto.setAdmin(us.getAdmin());
		Udto.setReputation(us.getReputation());
		Udto.setUserName(us.getUserName());
		
		return Udto;
	}
	
	User convertDtoToEntity(UserDto us) {
		User Uentity=new User();
		Uentity.setUserId(us.getUserId());
		Uentity.setEmail(us.getEmail());
		Uentity.setPassword(us.getPassword());
		Uentity.setAdmin(us.getAdmin());
		Uentity.setReputation(us.getReputation());
		Uentity.setUserName(us.getUserName());
		
		return Uentity;
	}
	
	
}
