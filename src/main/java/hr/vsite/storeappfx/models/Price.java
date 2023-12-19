package hr.vsite.storeappfx.models;

import hr.vsite.storeappfx.AppConfig;

public class Price {
    private final double taxRate;
    private double subtotal;
    private double tax;
    private double total;

    public Price() {
        taxRate = AppConfig.getTaxRate();
    }


    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
        setTax(this.subtotal * taxRate);
        setTotal(this.subtotal + tax);
    }
    public void addSubtotal(double subtotal) {
        this.subtotal += subtotal;
        setTax(this.subtotal * taxRate);
        setTotal(this.subtotal + tax);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
