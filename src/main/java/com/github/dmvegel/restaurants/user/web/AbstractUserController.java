package com.github.dmvegel.restaurants.user.web;

import com.github.dmvegel.restaurants.user.model.User;
import com.github.dmvegel.restaurants.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import static org.slf4j.LoggerFactory.getLogger;

@AllArgsConstructor
public abstract class AbstractUserController {
    protected final Logger log = getLogger(getClass());

    protected UserService userService;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public User get(int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    public void update(User user) {
        userService.update(user);
    }
}