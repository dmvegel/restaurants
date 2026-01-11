package ru.javaops.restaurants.user.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.stereotype.Service;
import ru.javaops.restaurants.common.error.NotFoundException;
import ru.javaops.restaurants.common.service.BaseService;
import ru.javaops.restaurants.user.model.User;
import ru.javaops.restaurants.user.repository.UserRepository;

import java.util.List;

import static ru.javaops.restaurants.app.config.SecurityConfig.PASSWORD_ENCODER;

@Service
public class UserService extends BaseService<User, UserRepository> {
    protected final UserCache userCache;

    public UserService(UserRepository repository, UserCache userCache) {
        super(repository);
        this.userCache = userCache;
    }

    public List<User> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public User get(int id) {
        return getExisted(id);
    }

    public User get(String email) {
        return getExistedByEmail(email);
    }

    private User getExistedByEmail(String email) {
        return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }

    @Transactional
    public void delete(int id) {
        User user = getExisted(id);
        repository.deleteById(id);
        userCache.removeUserFromCache(user.getEmail());
    }

    @Transactional
    public User create(User user) {
        return prepareAndSave(user);
    }

    @Transactional
    public User update(User user) {
        User saved = getExisted(user.getId());
        String oldEmail = saved.getEmail();
        if (!user.getEmail().equals(oldEmail)) {
            userCache.removeUserFromCache(oldEmail);
        }
        userCache.removeUserFromCache(saved.getEmail());
        return prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return repository.save(user);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = getExisted(id);
        user.setEnabled(enabled);
        userCache.removeUserFromCache(user.getEmail());
    }
}
