package com.fit2081.fit2081assigment1final.provider;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class InvoiceRepository {
    private final InvoiceDao invoiceDao;
    private final ItemDao itemDao;
    private final LiveData<List<Invoice>> allInvoices;
    private final ExecutorService executor = AppDatabase.databaseWriteExecutor;

    public InvoiceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        invoiceDao = db.invoiceDao();
        itemDao = db.itemDao();
        allInvoices = invoiceDao.getAllInvoices();
    }

    public LiveData<List<Invoice>> getAllInvoices() {
        return allInvoices;
    }

    public void insert(Invoice invoice) {
        executor.execute(() -> {
            long invoiceId = invoiceDao.insert(invoice);
            invoiceDao.recalculateInvoiceTotal(String.valueOf(invoiceId));
        });
    }

    public void update(Invoice invoice) {
        executor.execute(() -> invoiceDao.update(invoice));
    }

    public void delete(Invoice invoice) {
        executor.execute(() -> invoiceDao.delete(invoice));
    }

    public void insertItem(Item item) {
        executor.execute(() -> {
            invoiceDao.insertItem(item);
            invoiceDao.recalculateInvoiceTotal(item.getInvoiceId());
        });
    }

    public LiveData<List<Item>> getItemsForInvoice(String invoiceId) {
        return itemDao.getItemsForInvoice(invoiceId);
    }

    public void addItemToInvoice(Item item) {
        executor.execute(() -> {
            invoiceDao.insertItem(item);
            invoiceDao.recalculateInvoiceTotal(item.getInvoiceId());
        });
    }

    public void clearInvoice(String invoiceId) {
        executor.execute(() -> {
            invoiceDao.clearItems(invoiceId);
            invoiceDao.clearInvoiceFields(invoiceId);
        });
    }


    public void insertInvoice(Invoice invoice) {
        executor.execute(() -> invoiceDao.insert(invoice));
    }


    public void deleteItemsForInvoice(String invoiceId) {
        executor.execute(() -> itemDao.deleteItemsForInvoice(invoiceId));
    }






}
