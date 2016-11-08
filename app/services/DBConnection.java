package services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class DBConnection {
    private DBConnection(){}

    private static final Morphia morphia;
    private static final Datastore datastore;

    static{
        morphia = new Morphia();
        morphia.mapPackage("models");
        datastore = morphia.createDatastore(new MongoClient("localhost"), "studentsystem");
        datastore.ensureIndexes();
    }

    public static Morphia getInstance(){
        return morphia;
    }

    public static Datastore getDatastore(){
        return datastore;
    }
}
