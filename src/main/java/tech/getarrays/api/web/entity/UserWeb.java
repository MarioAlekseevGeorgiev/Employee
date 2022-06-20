package tech.getarrays.api.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserWeb {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;

    public UserWeb(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserWeb() {}

    public Long getId() {return id;}

    public UserWeb setId(Long id) {this.id = id; return this;}

    public String getUsername() {return username;}

    public UserWeb setUsername(String username) {this.username = username; return this;}

    public String getPassword() {return password;}

    public UserWeb setPassword(String password) {this.password = password; return this;}
}
