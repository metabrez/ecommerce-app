package com.edu.model;

import java.util.List;

public class OrderRequest {
    private List<Long> productIds;
    private Double totalAmount;

    // Getters and Setters
    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

}
