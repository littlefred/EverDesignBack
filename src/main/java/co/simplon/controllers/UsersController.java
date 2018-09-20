package co.simplon.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	// method to control that user exists and connexion is OK
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
	
}
