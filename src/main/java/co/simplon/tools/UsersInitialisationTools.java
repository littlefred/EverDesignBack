package co.simplon.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import co.simplon.models.Users;
import co.simplon.repository.UsersRepository;

@Named
public class UsersInitialisationTools {
	@Inject
	private UsersRepository usersRepository;

	/**
	 * method to insert a user file
	 * 
	 * @param file
	 * @return
	 * @throws ParseException
	 */
	public final String InsertionFileUser(List<String[]> file) {
		List<Users> usersList = new ArrayList<>(); // open list to note user to upload in DBB
		// initialisation of counters to follow the result of insertion file
		int nbUsersLoaded = 0;
		int nbUsersUpdated = 0;
		int nbUsersUnchanged = 0;
		int nbUsers = file.size();
		int nbLine = 0;
		String lines = "";
		// processing for each line of file
		for (String[] line : file) {
			nbLine++;
			Optional<Users> userInBase = this.usersRepository.findByMail(line[3]);
			if (!userInBase.isPresent()) {
				usersList.add(formatingNewUser(line));
				nbUsersLoaded++;
			} else {
				Users tempUser = formatingNewUser(line);
				if (!compareUsers(tempUser, userInBase.get())) {
					nbUsersUnchanged++;
					lines += "- " + nbLine;
				} else {
					usersList.add(updateUser(tempUser, userInBase.get()));
					nbUsersUpdated++;
				}
			}
		}
		// after add users that should be saved we write a resume of actions
		this.usersRepository.saveAll(usersList);
		System.out.println(nbUsersLoaded + " users loaded");
		System.out.println(nbUsersUpdated + " users updated");
		System.out.println(nbUsersUnchanged + " users unchanged");
		int nbTotal = nbUsersLoaded + nbUsersUpdated + nbUsersUnchanged;
		System.out.println("result : " + nbTotal + " users out of " + nbUsers);
		if (!lines.equals("")) {
			System.out.println("==> " + nbUsersUnchanged + " users untreated because no change : ");
			System.out.println("Lines " + lines);
		}
		return "file loaded";
	}

	/**
	 * method to format data in user object
	 * 
	 * @param informations
	 * @return
	 */
	private Users formatingNewUser(String[] informations) {
		// prepare insertion of user
		Users tempUser = new Users();
		tempUser.setLastName(informations[0].toUpperCase());
		// Update firstName format
		char[] chars = informations[1].toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		String tempFirstName = new String(chars);
		tempUser.setFirstName(tempFirstName);
		// update date of birth format & date of creation format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date tempBirthDate = new Date();
		Date tempCreationDate = new Date();
		try {
			tempBirthDate = sdf.parse(informations[2]);
			tempCreationDate = sdf.parse(informations[11]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tempUser.setDateOfBirth(tempBirthDate);
		tempUser.setDateOfCreation(tempCreationDate);
		tempUser.setMail(informations[3]);
		tempUser.setPassword(informations[4].hashCode());
		tempUser.setPhone(Integer.parseInt(informations[5]));
		tempUser.setStreet(informations[6]);
		tempUser.setCity(informations[7].toUpperCase());
		tempUser.setZipCode(informations[8].toUpperCase());
		tempUser.setCountry(Countries.valueOf(informations[9].toUpperCase()));
		tempUser.setPosition(Positions.valueOf(informations[10].toUpperCase()));
		return tempUser;
	}

	/**
	 * method to check if information password, or phone, or address, or position
	 * changed for a user
	 * 
	 * @param user
	 * @param oldUser
	 * @return true if there are one or many change or false if no change
	 */
	private boolean compareUsers(Users user, Users oldUser) {
		if (user.getPassword() != oldUser.getPassword() || user.getPhone() != oldUser.getPhone()
				|| !user.getStreet().equals(oldUser.getStreet()) || !user.getCity().equals(oldUser.getCity())
				|| !user.getZipCode().equals(oldUser.getZipCode()) || !user.getCountry().equals(oldUser.getCountry())
				|| !user.getPosition().equals(oldUser.getPosition())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to update user with new informations only on password, phone, address,
	 * and position
	 * 
	 * @param user
	 * @param oldUser
	 * @return
	 */
	private Users updateUser(Users user, Users oldUser) {
		oldUser.setPassword(user.getPassword());
		oldUser.setPhone(user.getPhone());
		oldUser.setStreet(user.getStreet());
		oldUser.setCity(user.getCity());
		oldUser.setZipCode(user.getZipCode());
		oldUser.setCountry(user.getCountry());
		oldUser.setPosition(user.getPosition());
		return oldUser;
	}

}
