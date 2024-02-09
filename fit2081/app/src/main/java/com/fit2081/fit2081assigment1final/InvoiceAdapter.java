package com.fit2081.fit2081assigment1final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fit2081.fit2081assigment1final.provider.Invoice;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private List<Invoice> invoices = new ArrayList<>();


    private OnInvoiceListener onInvoiceListener;

    public interface OnInvoiceListener {
        void onInvoiceClick(Invoice invoice);
    }

    public InvoiceAdapter(OnInvoiceListener onInvoiceListener) {
        this.onInvoiceListener = onInvoiceListener;
    }




    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_invoice, parent, false);
        return new InvoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice currentInvoice = invoices.get(position);
        holder.textViewIssuer.setText(currentInvoice.getIssuerName());
        holder.textViewBuyer.setText(currentInvoice.getBuyerName());
        holder.textViewTotal.setText(String.format(Locale.getDefault(), "%.2f", currentInvoice.getTotalValue()));
        holder.textViewId.setText(currentInvoice.getInvoiceId());
        holder.itemView.setOnClickListener(v -> {
            onInvoiceListener.onInvoiceClick(currentInvoice);
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
        notifyDataSetChanged();
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIssuer;
        TextView textViewBuyer;
        TextView textViewTotal;
        TextView textViewId;

        InvoiceViewHolder(View itemView) {
            super(itemView);
            textViewIssuer = itemView.findViewById(R.id.tv_issuer);
            textViewBuyer = itemView.findViewById(R.id.tv_buyer);
            textViewTotal = itemView.findViewById(R.id.tv_total);
            textViewId = itemView.findViewById(R.id.tv_invoice_id);

        }



    }
}
