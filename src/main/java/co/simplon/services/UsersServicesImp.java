package co.simplon.services;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Users;
import co.simplon.repository.UsersRepository;

@Service
@Named
public class UsersServicesImp implements UsersServices{
	@Inject
	private UsersRepository usersRepository;

	// method to check if user exists since mail and password
	@Override
	public Users authentificationUser(String mail, String pwd) {
		Optional<Users> tempUser = this.usersRepository.findByMailAndPassword(mail, pwd.hashCode());
		if(tempUser.isPresent()) {
			return tempUser.get();
		} else {
			return null;
		}
	}

}
