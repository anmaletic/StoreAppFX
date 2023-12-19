package hr.vsite.storeappfx.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoggedInUserSingleton {

    private static LoggedInUserSingleton instance;
    private final ObjectProperty<User> loggedInUser = new SimpleObjectProperty<>();


    private LoggedInUserSingleton() { }

    public static synchronized LoggedInUserSingleton getInstance() {
        if (instance == null) {
            instance = new LoggedInUserSingleton();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser.getValue();
    }
    public ObjectProperty<User> loggedInUserProperty() {
        return loggedInUser;
    }
    public void setLoggedInUser(User user) {
        loggedInUser.set(user);
    }
}