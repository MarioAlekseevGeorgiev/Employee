package tech.getarrays.api.user.bean;

import com.sun.istack.NotNull;

public class RegisterUser {

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String repassword;

    public RegisterUser(String email, String username, String password, String repassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.repassword = repassword;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getRepassword() {return repassword;}

    public void setRepassword(String repassword) {this.repassword = repassword;}
}
