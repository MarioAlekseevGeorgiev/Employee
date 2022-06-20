package tech.getarrays.api.web.impl;

import org.springframework.stereotype.Service;
import tech.getarrays.api.web.entity.UserWeb;
import tech.getarrays.api.web.repository.UserWebRepository;
import tech.getarrays.api.web.service.UserWebService;

import java.util.List;

@Service
public class UserWebServiceImpl implements UserWebService {

    private final UserWebRepository userWebRepositiry;

    public UserWebServiceImpl(UserWebRepository userWebRepositiry) {
        this.userWebRepositiry = userWebRepositiry;
    }

    @Override
    public UserWeb findUserWebByName(String username) {
        return this.userWebRepositiry.findUserWebByName(username);
    }

    @Override
    public List<UserWeb> findAll() {
        return userWebRepositiry.findAll();
    }

    @Override
    public UserWeb getById(Long id) {
        return userWebRepositiry.getById(id);
    }
}
