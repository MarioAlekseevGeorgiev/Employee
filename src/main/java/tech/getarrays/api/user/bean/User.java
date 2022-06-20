package tech.getarrays.api.user.bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String email;
    private String username;
    private String password;
//    @ManyToMany(fetch = EAGER)
//    Collection<Role> roles = new ArrayList<>();
    @Column(nullable = false, updatable = false)
    private String userCode;

    public User() {}

    public User(Long id, String email, String username, String password, String userCode) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userCode = userCode;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUserCode() {return userCode;}

    public void setUserCode(String userCode) {this.userCode = userCode;}

//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userCode='" + userCode + '\'' +
                '}';
    }
}
