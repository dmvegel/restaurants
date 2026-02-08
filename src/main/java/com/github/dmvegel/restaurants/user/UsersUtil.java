package com.github.dmvegel.restaurants.user;

import com.github.dmvegel.restaurants.user.model.Role;
import com.github.dmvegel.restaurants.user.model.User;
import com.github.dmvegel.restaurants.user.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}