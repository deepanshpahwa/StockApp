package com.example.stalker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stalker.Bean.BestMatch;
import com.example.stalker.Bean.ListOfBestMatches;

class StockRecyclerViewAdapter  extends RecyclerView.Adapter<StockRecyclerViewAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final ListOfBestMatches mData;
    private final Context context;
    private ItemClickListener mClickListener;

    public StockRecyclerViewAdapter(Context context, ListOfBestMatches data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.favorite_stock_rv_row, viewGroup, false);
        return new ViewHolder(view) ;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        BestMatch stockName = mData.getBestMatches().get(position);
        viewHolder.stockNameAbbrTv.setText(stockName.get1Symbol());
        viewHolder.stockNameTv.setText(stockName.get2Name());
        //Do realm stuff and check if the stock is already favorited.
        Utils.print("loaded row layout");
        viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.favoriteButton.setCompoundDrawablesWithIntrinsicBounds(drawable.);
                Utils.makeToast(context,"New Favorite added");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.getBestMatches().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stockNameAbbrTv,stockNameTv;
        Button favoriteButton;

        ViewHolder(View itemView) {
            super(itemView);
            stockNameAbbrTv = itemView.findViewById(R.id.stockname_abbr_tv);
            stockNameTv = itemView.findViewById(R.id.stockname_tv);
            favoriteButton = itemView.findViewById(R.id.favorite_button);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
