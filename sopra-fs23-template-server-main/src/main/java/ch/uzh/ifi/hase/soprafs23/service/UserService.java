package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.Date;

import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setStatus(UserStatus.OFFLINE);
    newUser.setCreationDate(new Date());

    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("The information for the user: {} was applied", newUser);
    return newUser;
  }




  public User getLoginFromUser(User user){
    Optional<User> loginUser = userRepository.findByUsername(user.getUsername());
    //If the user exists and the password provided is correct, the user is logged in
    if(loginUser.isPresent() && loginUser.get().getPassword().equals(user.getPassword())){
      return loginUser.get(); }
    //If the user does not exist or the password provided is incorrect, an error is thrown  
    else{
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username does not exist or is not matching with the provided Password"); }
  }



  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   * 
   *   private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());


    String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
    if (userByUsername != null && userByUsername.getPassword != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format(baseErrorMessage, "username and the password", "are"));
    } else if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
    } else if (userByPassword != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "password", "is"));
    }
  }
   */
  private void checkIfUserExists(User userToBeCreated) {
   
    Optional<User> userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
    String baseErrorMessage = "Unfortunately, the %s you entered %s already in use. Therefore we could not complete the registration.";
    if (userByUsername.isPresent()) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
    }
  }

  public User getUserByID(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with the id " + id + " was not found");
    }
  }


  public User updateUser(Long id, UserPutDTO userPutDTO) {
    Optional<User> userToUpdate = userRepository.findById(id);
    if (userToUpdate.isPresent()) {
      userToUpdate.get().setUsername(userPutDTO.getUsername());
      userToUpdate.get().setBirthday(userPutDTO.getBirthday());
      User user = userRepository.save(userToUpdate.get());
      userRepository.flush();
      return user;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with the id " + id + " was not found");
  }
}

