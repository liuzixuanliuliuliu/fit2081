package com.fit2081.fit2081assigment1final.provider;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class InvoiceViewModel extends AndroidViewModel {
    private final InvoiceRepository repository;
    private final LiveData<List<Invoice>> allInvoices;

    public InvoiceViewModel(Application application) {
        super(application);
        repository = new InvoiceRepository(application);
        allInvoices = repository.getAllInvoices();
    }

    public LiveData<List<Invoice>> getAllInvoices() {
        return allInvoices;
    }

    public void insertInvoice(Invoice invoice) {
        repository.insert(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        repository.update(invoice);
    }

    public void deleteInvoice(Invoice invoice) {
        repository.delete(invoice);
    }

    public String getCurrentInvoiceId() {

        return "current-invoice-id";
    }

    public void insertItem(Item item) {
        repository.insertItem(item);
    }


}