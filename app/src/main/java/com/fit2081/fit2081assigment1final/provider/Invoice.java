package com.fit2081.fit2081assigment1final.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Invoice.TABLE_NAME)
public class Invoice {
    public static final String TABLE_NAME = "invoices";

    public Invoice(@NonNull String invoiceId, String issuerName, String buyerName,
                   String buyerAddress, boolean isPaid, double totalValue) {
        this.invoiceId = invoiceId;
        this.issuerName = issuerName;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.isPaid = isPaid;
        this.totalValue = totalValue;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "invoiceId")
    private String invoiceId;

    @ColumnInfo(name = "issuerName")
    private String issuerName;

    @ColumnInfo(name = "buyerName")
    private String buyerName;

    @ColumnInfo(name = "buyerAddress")
    private String buyerAddress;

    @ColumnInfo(name = "isPaid")
    private boolean isPaid;

    @ColumnInfo(name = "totalValue")
    private double totalValue;

    @NonNull
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(@NonNull String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }


}
