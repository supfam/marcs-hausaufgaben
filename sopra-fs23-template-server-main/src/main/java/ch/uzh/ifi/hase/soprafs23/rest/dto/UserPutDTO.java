package ch.uzh.ifi.hase.soprafs23.rest.dto;
import java.util.Date;

public class UserPutDTO {

  private String username;
  private Date birthday;

  //Username
  public String getUsername() {return username;}
  public void setUsername(String username) {this.username = username;}


  //Birthday
  public Date getBirthday() {return birthday;}
  public void setBirthday(Date birthday) {this.birthday = birthday;}
  
}


