package cache;

import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCache {
    private Map<String, UserCache> userCacheMap;

    public LoginCache(List<User> users) {
        userCacheMap = new HashMap<>();
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
            System.out.println(user.toString());
        }
    }

    public UserCache getUser(String email) {
        return userCacheMap.get(email);
    }

    public class UserCache {
        private String password;
        private String type;

        public String getPassword() {
            return password;
        }

        public String getType() {
            return type;
        }

        private UserCache(String password, String type) {
            this.password = password;
            this.type = type;
        }
    }
}
