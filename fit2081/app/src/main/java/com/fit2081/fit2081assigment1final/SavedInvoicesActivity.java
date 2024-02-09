package com.fit2081.fit2081assigment1final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fit2081.fit2081assigment1final.provider.InvoiceViewModel;
import com.fit2081.fit2081assigment1final.provider.Invoice;
import com.google.android.material.snackbar.Snackbar;

public class SavedInvoicesActivity extends AppCompatActivity implements InvoiceAdapter.OnInvoiceListener{
    private InvoiceViewModel invoiceViewModel;
    private RecyclerView recyclerView;
    private InvoiceAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_invoices);
        recyclerView = findViewById(R.id.recycler_view_saved_invoices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InvoiceAdapter(this);
        recyclerView.setAdapter(adapter);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        invoiceViewModel.getAllInvoices().observe(this, invoices -> {
            adapter.setInvoices(invoices);
        });
    }

    @Override
    public void onInvoiceClick(Invoice invoice) {
        // Show a Snackbar with a DELETE action to confirm the deletion of the invoice
        Snackbar.make(recyclerView, "Confirm delete?", Snackbar.LENGTH_LONG)
                .setAction("DELETE", view -> {
                    // Call the method in the ViewModel to delete the invoice from the database
                    invoiceViewModel.deleteInvoice(invoice);
                    // After deletion, a new list of invoices will be fetched automatically if you have LiveData set up correctly in your ViewModel
                })
                .show();
    }


}

