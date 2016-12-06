package cache;

import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCache {
    private Map<String, UserCache> userCacheMap;

    public LoginCache(){
        userCacheMap = new HashMap<>();
    }

    public LoginCache(List<User> users){
        super();
        fill(users);
    }

    public void fill(List<User> users){
        for (User user : users) {
            put(user);
        }
    }

    public void put(User user){
        userCacheMap.put(user.getEmail(), new UserCache(user.getPassword(), user.getClass().getSimpleName()));
    }

    public String getUserType(String email){
        return userCacheMap.get(email).type;
    }

    public String getUserPassword(String email){
        return userCacheMap.get(email).password;
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
