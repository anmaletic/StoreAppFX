package hr.vsite.storeappfx;

import hr.vsite.storeappfx.models.LoggedInUserSingleton;
import hr.vsite.storeappfx.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    // Frontend
    public HBox hboxUserInfo;
    public Label lblLoggedInUser;
    public VBox vboxDock;
    public Button btnInv;
    public Button btnMinimize;
    @FXML
    private Pane viewPlaceholder;
    //

    private final LoggedInUserSingleton login = LoggedInUserSingleton.getInstance();
    private User loggedInUser;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInUser = LoggedInUserSingleton.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            try {
                viewPlaceholder.getChildren().add(new FXMLLoader(getClass().getResource("login-view.fxml")).load());
            } catch (IOException e) {
                logger.error("Error while adding login-view to viewPlaceholder on startup.", e);
            }
        }
        login.loggedInUserProperty().addListener((observable, oldValue, newValue) -> login());
    }

    @FXML
    private void showView(ActionEvent e) {
        try {
            var sender = ((Button)e.getSource()).getId();
            new FXMLLoader();

            FXMLLoader loader;
            switch (sender) {
                case "btnLogout":
                    loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                    logout();
                    break;
                case "btnStore":
                    loader = new FXMLLoader(getClass().getResource("store-view.fxml"));
                    break;
                case "btnInv":
                    loader = new FXMLLoader(getClass().getResource("inventory-view.fxml"));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown caller: " + sender);
            }

            Pane view = loader.load();

            // Set the login view as the content of the placeholder
            viewPlaceholder.getChildren().clear();
            viewPlaceholder.getChildren().add(view);
        } catch (Exception ex) {
            logger.error("Error while adding view to viewPlaceholder.", ex);
            ex.printStackTrace();
        }
    }
    private void login() {
        loggedInUser = LoggedInUserSingleton.getInstance().getLoggedInUser();
        if (loggedInUser == null) { return; }

        lblLoggedInUser.setText(loggedInUser.getUsername());
        hboxUserInfo.setVisible(true);
        vboxDock.setVisible(true);

        if(loggedInUser.getRole().equals("admin"))
        {
            btnInv.setVisible(true);
        }

        try {
            viewPlaceholder.getChildren().add(new FXMLLoader(getClass().getResource("store-view.fxml")).load());
        } catch (IOException e) {
            logger.error("Error while adding store-view to viewPlaceholder.", e);
            throw new RuntimeException(e);
        }
    }
    private void logout() {
        LoggedInUserSingleton.getInstance().setLoggedInUser(null);
        hboxUserInfo.setVisible(false);
        vboxDock.setVisible(false);
        btnInv.setVisible(false);
    }

    public void minimizeApp() {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeApp() {
        Platform.exit();
    }
}