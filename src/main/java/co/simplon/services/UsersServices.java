package co.simplon.services;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Users;

@Service
@Named
public interface UsersServices {
	/**
	 * method to check that user exists
	 * 
	 * @param mail
	 * @param pwd
	 * @return
	 */
	public Users authentificationUser(String mail, String pwd);

	/**
	 * method to check that email is already used
	 * 
	 * @param mail
	 * @return
	 */
	public boolean checkAvailableEmail(String mail);
	
	/**
	 * method to control that the callback of mail is correct
	 * @param userId
	 * @param userMail
	 * @return
	 */
	public int callBackMailControl(Long userId, String userMail);
	
	/**
	 * method to open an account
	 * @param user
	 * @return
	 */
	public Users openingAccount(Users user);

}
