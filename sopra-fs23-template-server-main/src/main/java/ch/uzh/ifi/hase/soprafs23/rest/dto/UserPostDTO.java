package ch.uzh.ifi.hase.soprafs23.rest.dto;
import java.util.Date;

public class UserPostDTO {

  private String username;
  private String password;

  //Username
  public String getUsername() {return username;}
  public void setUsername(String username) {this.username = username;}

  //Password
  public String getPassword() {return password;}
  public void setPassword(String password) {this.password = password;}
  
}
