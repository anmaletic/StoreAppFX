package hr.vsite.storeappfx;

import hr.vsite.storeappfx.models.DatabaseSim;
import hr.vsite.storeappfx.models.Price;
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

public class StoreController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    // Frontend
    @FXML
    private TableView<Product> tblProducts;
    public TableColumn<Product, String> colProdName;
    public TableColumn<Product, String> colProdDescription;
    public TableColumn<Product, Integer> colProdQuantity;
    public TableColumn<Product, Double> colProdPrice;

    @FXML
    private TableView<Product> tblCart;
    public TableColumn<Product, String> colCartName;
    public TableColumn<Product, Integer> colCartQuantity;
    public TableColumn<Product, Double> colCartPrice;

    public ComboBox<Integer> cbRemoveAmount;
    public ComboBox<Integer> cbAddAmount;

    public Button btnRemove;
    public Button btnAdd;
    public Button btnCheckOut;

    public Label valSubtotal;
    public Label valTax;
    public Label valTotal;
    //

    private Product selectedProductItem;
    private Product selectedCartItem;
    private final Price cartValue = new Price();
    private String currency;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private ObservableList<Product> cartList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productList = (ObservableList<Product>) DatabaseSim.getProductList();
        cartList = (ObservableList<Product>) DatabaseSim.getCartList();

        // Set up TableView for Products
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProdQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblProducts.setItems(productList);

        // Set up TableView for Cart
        colCartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblCart.setItems(cartList);

        currency = AppConfig.getCurrency();
        updatePrice();
    }

    public void addToCart() {
        int amount = cbAddAmount.getSelectionModel().getSelectedItem();
        if(amount == 0) { return; }

        if (selectedProductItem != null && selectedProductItem.getQuantity() >= amount) {
            selectedProductItem.setQuantity(selectedProductItem.getQuantity() - amount);

            if (selectedProductItem.getQuantity() == 0) {
                btnAdd.setDisable(true);
            }

            var existingCartItem = cartList.stream()
                    .filter(item -> item.getName().equals(selectedProductItem.getName()))
                    .findFirst()
                    .orElse(null);

            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + amount);
            }
            else {
                cartList.add(new Product(
                        selectedProductItem.getId(),
                        selectedProductItem.getName(),
                        selectedProductItem.getDescription(),
                        selectedProductItem.getPrice(),
                        amount
                ));
            }
        }
        tblProducts.refresh();
        tblCart.refresh();
        updateAddAmount();
        updateRemoveAmount();
        updatePrice();
    }
    public void removeFromCart() {
        int amount = cbRemoveAmount.getSelectionModel().getSelectedItem();
        if(amount == 0) { return; }

        if (selectedCartItem != null) {
            selectedCartItem.setQuantity(selectedCartItem.getQuantity() - amount);

            var existingProductItem = productList.stream()
                    .filter(item -> item.getName().equals(selectedCartItem.getName()))
                    .findFirst()
                    .orElse(null);


            if (existingProductItem != null) {
                existingProductItem.setQuantity(existingProductItem.getQuantity() + amount);
            } else {
                productList.add(new Product(
                        productList.size(),
                        selectedCartItem.getName(),
                        selectedCartItem.getDescription(),
                        selectedCartItem.getPrice(),
                        amount
                ));
            }

            if (selectedCartItem.getQuantity() == 0) {
                cartList.remove(selectedCartItem);
                btnRemove.setDisable(true);
            }
        }


        tblProducts.refresh();
        tblCart.refresh();
        updateAddAmount();
        updateRemoveAmount();
        updatePrice();
    }
    public void updatePrice() {
        cartValue.setSubtotal(0);
        for (var prod : cartList) {
            cartValue.addSubtotal(prod.getPrice() * prod.getQuantity());
        }

        valSubtotal.setText(String.format("%.2f %s", cartValue.getSubtotal(), currency));
        valTax.setText(String.format("%.2f %s", cartValue.getTax(), currency));
        valTotal.setText(String.format("%.2f %s", cartValue.getTotal() , currency));

        btnCheckOut.setDisable((cartValue.getTotal() == 0));
    }

    public void setSelectedProductItem() {
        selectedProductItem = tblProducts.getSelectionModel().getSelectedItem();
        if(selectedProductItem != null) {
            btnAdd.setDisable(selectedProductItem.getQuantity() <= 0);
            updateAddAmount();
        }
    }
    public void setSelectedCartItem() {
        selectedCartItem = tblCart.getSelectionModel().getSelectedItem();
        btnRemove.setDisable(selectedCartItem == null);
        updateRemoveAmount();
    }

    private void updateAddAmount() {
        updateAmount(selectedProductItem, cbAddAmount);

        btnAdd.setDisable(selectedProductItem == null || selectedProductItem.getQuantity() <= 0);
    }
    private void updateRemoveAmount() {
        updateAmount(selectedCartItem, cbRemoveAmount);
    }
    private void updateAmount(Product selectedItem, ComboBox<Integer> cbAmount) {
        if (selectedItem != null) {
            ObservableList<Integer> cbItems = FXCollections.observableArrayList();
            for (int i = 0; i <= selectedItem.getQuantity(); i++){
                cbItems.add(i);
            }
            cbAmount.setItems(cbItems);

            int selectedQuantity = selectedItem.getQuantity() > 0 ? 1 : 0;
            cbAmount.setValue(selectedQuantity);
        } else {
            cbAmount.setItems(null);
        }
    }

    public void checkOut() {
        // do checkout logic and remove items from cart
        selectedCartItem = null;
        btnRemove.setDisable(true);
        cartList.clear();
        tblCart.refresh();
        updateRemoveAmount();
        updatePrice();
    }
}
