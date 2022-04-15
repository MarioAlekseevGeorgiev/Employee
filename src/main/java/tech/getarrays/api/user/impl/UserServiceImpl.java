package tech.getarrays.api.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.getarrays.api.user.bean.RegisterUser;
import tech.getarrays.api.user.service.UserDao;
import tech.getarrays.api.user.bean.User;
import tech.getarrays.api.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    @Override
    public boolean checkEmailForExisting(String email) {
        return userDao.checkEmailForExisting(email);
    }

    @Override
    public ResponseEntity<?> addUser(RegisterUser user) {

        if (checkEmailForExisting(user.getEmail())) {
            return new ResponseEntity<>("This email is used", HttpStatus.CONFLICT);
        }

        if (!checkPasswords(user.getPassword(), user.getRepassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        user.setPassword(user.getPassword());
        User newUser = userDao.addUser(new User(null, user.getEmail(), user.getUsername(), user.getPassword(), null));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }



    private boolean checkPasswords(String password, String repassword) {
        return password.equals(repassword);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.getUserByEmail(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found in the database");
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
//
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//    }
}
