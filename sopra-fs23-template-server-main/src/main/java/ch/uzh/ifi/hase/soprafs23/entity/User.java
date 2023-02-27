package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  //Changed name to password

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  //Password does not has to be unique
  @Column(nullable = false, unique = false)
  private String password;

  //Birthday does not has to be unique and has not to be filled
  @Column(nullable = true, unique = false)
  private Date birthday;

  //Birthday does not has to be unique
  @Column(nullable = true, unique = false)
  private Date creationDate;

  //token nullable = false richtig
  @Column(nullable = true, unique = true)
  private String token;

  @Column(nullable = true)
  private UserStatus status;

  //User ID
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  //Password
  public String getPassword() {return password;}
  public void setPassword(String password) {this.password = password;}

  //Username
  public String getUsername() {return username;}
  public void setUsername(String username) {this.username = username;}

  //Token
  public String getToken() {return token;}
  public void setToken(String token) {this.token = token;}

  //User Status
  public UserStatus getStatus() {return status;}
  public void setStatus(UserStatus status) {this.status = status;}

  //Birthday
  public Date getBirthday() {return birthday;}

public void setBirthday(Date birthday) {this.birthday = birthday;}

//Creation Date
public Date getCreationDate() {return creationDate;}

public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

}
