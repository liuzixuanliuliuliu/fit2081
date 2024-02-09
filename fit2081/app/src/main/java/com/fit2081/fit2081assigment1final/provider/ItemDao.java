package com.fit2081.fit2081assigment1final.provider;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import android.database.Cursor;

import java.util.List;
@Dao
public interface ItemDao {
    @Insert
    long insert(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items WHERE invoiceId = :invoiceId")
    LiveData<List<Item>> getItemsForInvoice(String invoiceId);

    @Query("SELECT * FROM items")
    Cursor selectAll();

    @Query("SELECT * FROM items WHERE itemId = :itemId")
    Cursor selectById(long itemId);


    @Query("DELETE FROM items WHERE invoiceId = :invoiceId")
    void deleteItemsForInvoice(String invoiceId);



}