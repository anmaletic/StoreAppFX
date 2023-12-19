package hr.vsite.storeappfx;

import hr.vsite.storeappfx.models.DatabaseSim;
import hr.vsite.storeappfx.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    // Frontend
    @FXML
    private TableView<Product> tblProducts;
    public TableColumn<Product, String> colProdName;
    public TableColumn<Product, String> colProdDescription;
    public TableColumn<Product, Integer> colProdQuantity;
    public TableColumn<Product, Double> colProdPrice;

    public Button btnAddProduct;
    public Button btnUpdateProduct;
    public Button btnDeleteProduct;

    public TextField tfNewProdName;
    public TextField tfNewProdDesc;
    public TextField tfNewProdQuantity;
    public TextField tfNewProdPrice;

    public TextField tfEditProdName;
    public TextField tfEditProdDesc;
    public TextField tfEditProdQuantity;
    public TextField tfEditProdPrice;
    //

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private Product selectedProductItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productList = (ObservableList<Product>) DatabaseSim.getProductList();

        // Set up TableView for Products
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProdQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblProducts.setItems(productList);
    }

    public void handleInput() {
        boolean isDisabled =
                tfNewProdName.getLength() <= 0 ||
                tfNewProdQuantity.getLength() <= 0 ||
                tfNewProdDesc.getLength() <= 0 ||
                tfNewProdPrice.getLength() <= 0;

        btnAddProduct.setDisable(isDisabled);
    }

    public void setSelectedProductItem() {
        selectedProductItem = tblProducts.getSelectionModel().getSelectedItem();
        if(selectedProductItem != null) {
            tfEditProdName.setText(selectedProductItem.getName());
            tfEditProdDesc.setText(selectedProductItem.getDescription());
            tfEditProdQuantity.setText(String.valueOf(selectedProductItem.getQuantity()));
            tfEditProdPrice.setText(String.valueOf(selectedProductItem.getPrice()));

            btnUpdateProduct.setDisable(false);
            btnDeleteProduct.setDisable(false);
        } else {
            btnUpdateProduct.setDisable(true);
            btnDeleteProduct.setDisable(true);
        }
    }

    public void addProduct() {
        try {
            productList.add(new Product(
                productList.size(),
                tfNewProdName.getText(),
                tfNewProdDesc.getText(),
                Double.parseDouble(tfNewProdPrice.getText()),
                Integer.parseInt(tfNewProdQuantity.getText())
            ));
            resetInputValues(tfNewProdName, tfNewProdDesc, tfNewProdQuantity, tfNewProdPrice);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error", "Some of the entered values are not correct.");
        }
    }
    public void updateProduct() {
        try {
            selectedProductItem.setName(tfEditProdName.getText());
            selectedProductItem.setDescription(tfEditProdDesc.getText());
            selectedProductItem.setQuantity(Integer.parseInt(tfEditProdQuantity.getText()));
            selectedProductItem.setPrice(Double.parseDouble(tfEditProdPrice.getText()));

            tblProducts.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error", "Some of the entered values are not correct.");
        }
    }
    public void deleteProduct() {
        try {
            productList.remove(selectedProductItem);
            tblProducts.refresh();

            resetInputValues(tfEditProdName, tfEditProdDesc, tfEditProdQuantity, tfEditProdPrice);
            setSelectedProductItem();
        } catch (Exception e) {
            logger.error("Error while deleting product.", e);
            e.printStackTrace();
        }
    }

    private void resetInputValues(TextField tfProdName, TextField tfProdDesc, TextField tfProdQuantity, TextField tfProdPrice) {
        tfProdName.setText("");
        tfProdDesc.setText("");
        tfProdPrice.setText("");
        tfProdQuantity.setText("");
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
