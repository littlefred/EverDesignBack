package co.simplon.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.models.Login;
import co.simplon.models.Users;
import co.simplon.services.UsersServices;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.194:4200"})
public class UsersController {
	@Inject
	private UsersServices usersServices;
	
	/**
	 *  method to control that user exists and connexion is OK
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/cnx", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Users> cnxUser(@RequestBody Login body) {
		Users user = this.usersServices.authentificationUser(body.mail, body.pwd);
		if (user == null) {
			return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Users>(user, HttpStatus.OK);
		}
	}
	
	/**
	 * method to check that mail is already exists or not
	 * @param mail
	 * @return
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<Boolean> emailAvailable(@RequestParam String mail) {
		boolean response = this.usersServices.checkAvailableEmail(mail);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
	
	/**
	 * method to control that the callback mail request is correct
	 * @param userId
	 * @param userMail
	 * @return
	 */
	@GetMapping(value = "/callBack")
	@ResponseBody
	public ResponseEntity<Integer> callBackControl(@RequestParam Long userId, @RequestParam String userMail) {
		int result = this.usersServices.callBackMailControl(userId, userMail);
		if(result == 0) {
			return new ResponseEntity<Integer>(0, HttpStatus.NOT_FOUND);
		} else if(result == 1) {
			return new ResponseEntity<Integer>(1, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(2, HttpStatus.OK);
		}
	}
	
	/**
	 * method to manage an opening account
	 * @param user
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<Users> openAccount(@RequestBody Users user) {
		Users userCreated = this.usersServices.openingAccount(user);
		if (userCreated == null) {
			return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Users>(user, HttpStatus.CREATED);
		}
	}
	
}
