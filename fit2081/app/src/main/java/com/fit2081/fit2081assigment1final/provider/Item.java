package com.fit2081.fit2081assigment1final.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = Item.TABLE_NAME,
        indices = {@Index(value = "invoiceId")},
        foreignKeys = @ForeignKey(entity = Invoice.class,
                parentColumns = "invoiceId",
                childColumns = "invoiceId",
                onDelete = ForeignKey.CASCADE))
public class Item {

    public static final String TABLE_NAME = "items";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemId")
    private String itemId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "invoiceId")
    private String invoiceId;


    @NonNull
    public String getItemId() {
        return itemId;
    }

    public void setItemId(@NonNull String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Item(@NonNull String itemId, String name, double cost, int quantity, String invoiceId) {
        this.itemId = itemId;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.invoiceId = invoiceId;
    }



    public static Item fromContentValues(ContentValues values) {
        String itemId = "";
        String name = "";
        double cost = 0.0;
        int quantity = 0;
        String invoiceId = "";

        if (values.containsKey("itemId")) {
            itemId = values.getAsString("itemId");
        }
        if (values.containsKey("name")) {
            name = values.getAsString("name");
        }
        if (values.containsKey("cost")) {
            cost = values.getAsDouble("cost");
        }
        if (values.containsKey("quantity")) {
            quantity = values.getAsInteger("quantity");
        }
        if (values.containsKey("invoiceId")) {
            invoiceId = values.getAsString("invoiceId");
        }

        return new Item(itemId, name, cost, quantity, invoiceId);
    }
}
