package tech.getarrays.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.getarrays.api.web.entity.UserWeb;
import tech.getarrays.api.web.service.UserWebService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserWebController {

    private final UserWebService userWebService;

    public UserWebController(UserWebService userWebService) {
        this.userWebService = userWebService;
    }

    @GetMapping("/{id}")
    public UserWeb getById(@PathVariable("id") Long id) {
        return userWebService.getById(id);
    }

    @GetMapping("/all")
    public List<UserWeb> getAll() {
        return userWebService.findAll();
    }
}
