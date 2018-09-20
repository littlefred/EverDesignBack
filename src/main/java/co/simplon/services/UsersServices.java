package co.simplon.services;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Users;

@Service
@Named
public interface UsersServices {
	// method to check that user exists
	public Users authentificationUser(String mail, String pwd);

}
