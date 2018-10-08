package co.simplon.services;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Users;
import co.simplon.repository.UsersRepository;
import co.simplon.tools.ControlsData;
import co.simplon.tools.Positions;
import co.simplon.tools.SendEmail;
import co.simplon.tools.Title;

@Service
@Named
public class UsersServicesImp implements UsersServices {
	@Inject
	private UsersRepository usersRepository;

	/**
	 * method to check if user exists since mail and password
	 */
	@Override
	public Users authentificationUser(String mail, String pwd) {
		Optional<Users> tempUser = this.usersRepository.findByMailAndPassword(mail, String.valueOf(pwd.hashCode()));
		if (tempUser.isPresent()) {
			return tempUser.get();
		} else {
			return null;
		}
	}

	/**
	 * method to check that email is available
	 */
	@Override
	public boolean checkAvailableEmail(String mail) {
		boolean result = false;
		Optional<Users> controlEmail = this.usersRepository.findByMail(mail);
		if (controlEmail.isPresent()) {
			result = true;
		}
		return result;
	}

	/**
	 * method to do controls since userId and userMail of callBack mail request
	 */
	@Override
	public int callBackMailControl(Long userId, String userMail) {
		Optional<Users> userInBase = this.usersRepository.findByMail(userMail);
		if (userInBase.isPresent()) {
			SendEmail tools = new SendEmail();
			Long idInBase = tools.generateID(userInBase.get().getId());
			if (idInBase == userId) {
				Users userUpdated = userInBase.get();
				userUpdated.setPosition(Positions.USER_VALID);
				this.usersRepository.save(userUpdated);
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	/**
	 * method to do controls before open an account
	 */
	@Override
	public Users openingAccount(Users user) {
		if (user.getId() == null && ControlsData.controlName(user.getLastName())
				&& ControlsData.controlName(user.getFirstName()) && ControlsData.controlPhone(user.getPhone())
				&& ControlsData.controlDate(user.getDateOfBirth()) && ControlsData.controlStreet(user.getStreet())
				&& ControlsData.controlZipCode(user.getZipCode()) && ControlsData.controlCity(user.getCity())
				&& user.getCountry() != null && ControlsData.controlMail(user.getMail()) && user.getPassword() != null
				&& ControlsData.controlDate(user.getDateOfCreation())) {
			user.setPosition(Positions.USER_NOVALID);
			Users userSaved = this.usersRepository.save(user);
			if (userSaved == null) {
				return null;
			} else {
				SendEmail tools = new SendEmail();
				boolean sendInscriptionMail = tools.sendSimpleMessage(user.getMail(), Title.INSCRIPTION,
						user.getId());
				if (sendInscriptionMail) {
					return userSaved;
				} else {
					this.usersRepository.deleteById(userSaved.getId());
					return null;
				}
			}
		} else {
			System.err.println("RequestBody is not correct.");
			return null;
		}
	}

}
