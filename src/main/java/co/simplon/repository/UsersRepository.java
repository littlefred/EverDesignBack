package co.simplon.repository;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.models.Users;

@Repository
@Named
public interface UsersRepository extends JpaRepository<Users, Long> {
	/**
	 * method to find a user by mail
	 * 
	 * @param mail
	 * @return
	 */
	public Optional<Users> findByMail(String mail);

	/**
	 * method to find a user by mail and password
	 * 
	 * @param mail
	 * @param pwd
	 * @return
	 */
	public Optional<Users> findByMailAndPassword(String mail, String pwd);

}
