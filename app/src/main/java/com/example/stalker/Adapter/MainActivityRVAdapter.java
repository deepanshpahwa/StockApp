package com.example.stalker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stalker.Bean.RealmObjectListOfFavStocks;
import com.example.stalker.Bean.Symbol;
import com.example.stalker.R;
import com.example.stalker.Utils;

import java.util.Map;

import io.realm.RealmList;

public class MainActivityRVAdapter extends RecyclerView.Adapter<MainActivityRVAdapter.ViewHolder> {
    private final RealmList<String> favoriteStocks;
    private Map<String, Symbol> stocks;
    private ItemClickListener mClickListener;

    public MainActivityRVAdapter(Map<String, Symbol> stocks, RealmObjectListOfFavStocks favoriteStocks) {
        this.stocks = stocks;
        this.favoriteStocks = favoriteStocks.getList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_stock, viewGroup, false);

        return new ViewHolder(contactView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String stockName =  stocks.get(favoriteStocks.get(position)).getQuote().getCompanyName();
        String stockSymbol = stocks.get(favoriteStocks.get(position)).getQuote().getSymbol();
        String stockPrice = "$"+stocks.get(favoriteStocks.get(position)).getQuote().getLatestPrice().toString();

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

    public void setClickListener(ItemClickListener itemClickListener) {
        Utils.print("setclickListener");
        this.mClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView stockNameTv, stockSymbolTv, stockPriceTv;

         public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            stockNameTv = itemView.findViewById(R.id.stock_name_tv);
            stockSymbolTv = itemView.findViewById(R.id.stock_symbol_tv);
            stockPriceTv = itemView.findViewById(R.id.stock_price_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Utils.print("onClick");
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    public Symbol getItem(String sName) {
        return stocks.get(sName);
    }

    // allows clicks events to be caught

    // parent activity will implement this method to respond to click events



    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
