package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import java.util.Date;

public class UserGetDTO {

  private Long id;
  private String username;
  private String password;
  private UserStatus status;
  private String token;
  private Date birthday;
  private Date creationDate;

  //User ID
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}


  //Username
  public String getUsername() {return username;}
  public void setUsername(String username) {this.username = username;}


  //Status
  public UserStatus getStatus() {return status;}
  public void setStatus(UserStatus status) {this.status = status;}


  //Birthday
  public Date getBirthday() {return birthday;}
  public void setBirthday(Date birthday) {this.birthday = birthday;}


  //Creation Date
  public Date getCreationDate() {return creationDate;}
  public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

}
