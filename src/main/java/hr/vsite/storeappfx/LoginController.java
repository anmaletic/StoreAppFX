package hr.vsite.storeappfx;

import hr.vsite.storeappfx.models.DatabaseSim;
import hr.vsite.storeappfx.models.LoggedInUserSingleton;
import hr.vsite.storeappfx.models.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    // Frontend
    public TextField tfUsername;
    public PasswordField tfPassword;
    public Button btnLogin;
    public Label lblErrorField;
    //

    private LoggedInUserSingleton loggedInUser;
    private List<User> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = DatabaseSim.getUserList();
        loggedInUser = LoggedInUserSingleton.getInstance();
    }

    public void login() {
        if (tfUsername.getLength() <= 0 || tfPassword.getLength() <= 0) return;

        var existingUser = users.stream()
                .filter(item -> item.getUsername().equals(tfUsername.getText()) && item.getPassword().equals(tfPassword.getText()))
                .findFirst()
                .orElse(null);

        if (existingUser != null) {
            loggedInUser.setLoggedInUser(existingUser);
            lblErrorField.setText("");
        } else {
            lblErrorField.setText("Wrong username or password!");
            logger.info("Attempted login with wrong data. User: {}", tfUsername.getText());
        }
    }
    public void handleInput() {
        btnLogin.setDisable(tfUsername.getLength() <= 0 || tfPassword.getLength() <= 0);
    }
}