package cache;

import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCache {
    private Map<String, UserCache> userCacheMap;

    public LoginCache() {
        userCacheMap = new HashMap<>();
    }

    public LoginCache(List<User> users) {
        super();
        fill(users);
    }

    public void fill(List<User> users) {
        for (User user : users) {
            put(user);
        }
    }

    public void put(User user) {
        try {
            userCacheMap.put(user.getEmail(), new UserCache(user.getPassword(), user.getClass().getSimpleName()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getUserType(String email) {
        try {
            return userCacheMap.get(email).type;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getUserPassword(String email) {
        try {
            return userCacheMap.get(email).password;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private class UserCache {
        private String password;
        private String type;

        private UserCache(String password, String type) {
            this.password = password;
            this.type = type;
        }
    }
}
