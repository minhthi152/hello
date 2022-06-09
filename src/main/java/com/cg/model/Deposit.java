package com.cg.model;

import java.math.BigDecimal;
import java.util.Date;

public class Deposit {

    private int id;
    private int customerId;
    private BigDecimal transactionAmount;

    private boolean deleted;
    private Date createdAt;
    private int createdBy;
    private Date updatedAt;
    private int updatedBy;

    public Deposit() {
    }

    public Deposit(int customerId, BigDecimal transactionAmount) {
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
    }

    public Deposit(int id, int customerId, BigDecimal transactionAmount, boolean deleted, Date createdAt, int createdBy, Date updatedAt, int updatedBy) {
        this.id = id;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}
