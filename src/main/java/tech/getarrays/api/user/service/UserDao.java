package tech.getarrays.api.user.service;

import tech.getarrays.api.user.bean.User;

public interface UserDao {

    User getUser(String email, String password);

    boolean checkEmailForExisting(String email);

    User addUser(User user);

    User getUserByEmail(String email);
}
