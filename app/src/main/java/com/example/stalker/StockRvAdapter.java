package com.example.stalker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stalker.Bean.Symbol;

import java.util.Map;

public class StockRvAdapter extends RecyclerView.Adapter<StockRvAdapter.ViewHolder> {
    private Map<String, Symbol> stocks;
    public StockRvAdapter(Map<String, Symbol> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_stock, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Symbol stock = stocks.get(position);
        TextView textView = viewHolder.stockNameTv;
//        textView.setText(stock.getStockCode());




    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stockNameTv;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            stockNameTv = (TextView) itemView.findViewById(R.id.stock_name_tv);

        }
    }

    }
