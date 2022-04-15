package tech.getarrays.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.api.user.bean.RegisterUser;
import tech.getarrays.api.user.bean.User;
import tech.getarrays.api.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping("/login")
    public ResponseEntity<?> signUser(@RequestBody User user) {
        User userLogged = userService.getUser(user.getEmail().trim(), user.getPassword().trim());

        if (userLogged != null) {
            return new ResponseEntity<>(userLogged, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email or password is incorrect!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUser user) {
        return userService.addUser(user);
    }

    @GetMapping("/email")
    public boolean checkEmailForExisting(@RequestParam(name="email") String email) {
        return userService.checkEmailForExisting(email);
    }
}
