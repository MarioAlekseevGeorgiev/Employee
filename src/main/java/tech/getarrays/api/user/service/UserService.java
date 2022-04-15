package tech.getarrays.api.user.service;

import org.springframework.http.ResponseEntity;
import tech.getarrays.api.user.bean.RegisterUser;
import tech.getarrays.api.user.bean.User;

public interface UserService {

    User getUser(String email, String password);

    boolean checkEmailForExisting(String email);

    ResponseEntity<User> addUser(RegisterUser user);
}
