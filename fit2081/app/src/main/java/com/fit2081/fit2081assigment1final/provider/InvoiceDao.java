package com.fit2081.fit2081assigment1final.provider;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Insert;
import java.util.List;
import androidx.lifecycle.LiveData;
import android.database.Cursor;
import androidx.room.OnConflictStrategy;
@Dao
public interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Invoice invoice);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Item item);

    @Insert
    void addInvoice(Invoice invoice);

    @Update
    void update(Invoice invoice);

    @Delete
    void delete(Invoice invoice);

    @Query("SELECT * FROM invoices")
    LiveData<List<Invoice>> getAllInvoices();

    @Query("SELECT * FROM items WHERE invoiceId = :invoiceId")
    LiveData<List<Item>> getItemsForInvoice(String invoiceId);

    @Query("UPDATE invoices SET totalValue = (SELECT SUM(cost * quantity) FROM items WHERE invoiceId = :invoiceId) WHERE invoiceId = :invoiceId")
    void recalculateInvoiceTotal(String invoiceId);

    @Query("UPDATE invoices SET issuerName = '', buyerName = '', buyerAddress = '', isPaid = 0, totalValue = 0 WHERE invoiceId = :invoiceId")
    void clearInvoiceFields(String invoiceId);

    @Query("DELETE FROM items WHERE invoiceId = :invoiceId")
    void clearItems(String invoiceId);
}
