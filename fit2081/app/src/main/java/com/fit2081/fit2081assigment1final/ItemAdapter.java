package com.fit2081.fit2081assigment1final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import com.fit2081.fit2081assigment1final.provider.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.nameTextView.setText(currentItem.getName());
        holder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));
        holder.costTextView.setText(String.format(Locale.getDefault(), "%.2f", currentItem.getCost()));
    }



    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;
        TextView costTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_item_name);
            quantityTextView = itemView.findViewById(R.id.tv_item_quantity);
            costTextView = itemView.findViewById(R.id.tv_item_cost);
        }
    }
}
