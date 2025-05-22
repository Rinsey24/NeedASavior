package com.rinsey24.needasavior;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;
    private boolean isEffectivelyVisible = true;

    public CartItem(Product product, int quantity) {
        this.product = product;
        setQuantity(quantity);
        this.isEffectivelyVisible = true;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            this.quantity = 0;
        }
    }

    public String getTitle() {
        return product != null ? product.getTitle() : "Название неизвестно";
    }

    public double getPrice() {
        return product != null ? product.getPrice() : 0.0;
    }

    public String getImageName() {
        return product != null ? product.getImageName() : null;
    }

    public float getTotalPrice() {
        return (float) (getPrice() * getQuantity());
    }

    public boolean isEffectivelyVisible() {
        return isEffectivelyVisible;
    }

    public void setEffectivelyVisible(boolean effectivelyVisible) {
        isEffectivelyVisible = effectivelyVisible;
    }
}