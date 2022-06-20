package tech.getarrays.api.web.service;

import tech.getarrays.api.web.entity.UserWeb;

import java.util.List;

public interface UserWebService {
    UserWeb findUserWebByName(String username);

    List<UserWeb> findAll();

    UserWeb getById(Long id);
}
