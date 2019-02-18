package com.example.stalker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stalker.Bean.Symbol;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Callback;

public class StockRvAdapter extends RecyclerView.Adapter<StockRvAdapter.ViewHolder> {
    private final ArrayList<String> favoriteStocks;
    private Map<String, Symbol> stocks;
    private ItemClickListener mClickListener;

    public StockRvAdapter(Map<String, Symbol> stocks, ArrayList<String> favoriteStocks) {
        this.stocks = stocks;
        this.favoriteStocks = favoriteStocks;
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

        String stockName =  stocks.get(favoriteStocks.get(position)).getQuote().getCompanyName();
        String stockSymbol = stocks.get(favoriteStocks.get(position)).getQuote().getSymbol();
        String stockPrice = stocks.get(favoriteStocks.get(position)).getQuote().getLatestPrice().toString();

        TextView stockNameTv = viewHolder.stockNameTv;
        TextView stockSymbolTv = viewHolder.stockSymbolTv;
        TextView stockPriceTv = viewHolder.stockPriceTv;


        stockNameTv.setText(stockName);
        stockSymbolTv.setText(stockSymbol);
        stockPriceTv.setText(stockPrice);




    }

    @Override
    public int getItemCount() {
        if (stocks.size() == 0){
            System.out.println(1/0);
            System.out.print(":::::::::::Hey man, favorite stocks is empty");
        }
        return favoriteStocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView stockNameTv, stockSymbolTv, stockPriceTv;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            stockNameTv = itemView.findViewById(R.id.stock_name_tv);
            stockSymbolTv = itemView.findViewById(R.id.stock_symbol_tv);
            stockPriceTv = itemView.findViewById(R.id.stock_price_tv);



        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    }
