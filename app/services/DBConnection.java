package services;

import cache.LoginCache;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import models.Admin;
import models.Instructor;
import models.Student;
import models.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private DBConnection(){}

    private static final Morphia morphia;
    private static final Datastore datastore;
    private static final LoginCache loginCache;

    static{
        morphia = new Morphia();
        morphia.mapPackage("models");
        datastore = morphia.createDatastore(new MongoClient("localhost"), "studentsystem");
        datastore.ensureIndexes();
        List<User> users = new ArrayList<>();
        users.addAll(Admin.getAll());
        users.addAll(Student.getAll());
        users.addAll(Instructor.getAll());
        loginCache = new LoginCache(users);
    }

    public static Morphia getInstance(){
        return morphia;
    }

    public static Datastore getDatastore(){
        return datastore;
    }

    public static LoginCache getLoginCache() {
        return loginCache;
    }
}
