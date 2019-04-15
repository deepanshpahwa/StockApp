package com.example.stalker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stalker.Bean.CustomIndicatorBeanRealm;
import com.example.stalker.Bean.ListOFCustomIndicatorsBean;
import com.example.stalker.R;
import com.example.stalker.Utils;

import io.realm.Realm;

public class SavedIndicatorRVAdapter extends RecyclerView.Adapter<SavedIndicatorRVAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    Realm realm;
    private ItemClickListener mClickListener;


    public SavedIndicatorRVAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//        Context context = viewGroup.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);

        View view = mInflater.inflate(R.layout.save_indicator_tv_row, viewGroup, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        CustomIndicatorBeanRealm  object = realm.where(ListOFCustomIndicatorsBean.class).findFirst().getList().get(position);

        viewHolder.customIndicatorName.setText(object.getName());//TODO
        if (object.hasThirdElement()) {
            viewHolder.customIndicatorDefintion.setText(object.getFirstElement() + object.getFirstMathFunction() + object.getSecondElement() + object.getSecondMathFunction() + object.hasThirdElement());//TODO
        }else{
            viewHolder.customIndicatorDefintion.setText(object.getFirstElement() + object.getFirstMathFunction() + object.getSecondElement());//TODO
        }
    }

    @Override
    public int getItemCount() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ListOFCustomIndicatorsBean.class).findFirst().getList().size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener  = itemClickListener;
        Utils.print("HHHHsettnglistener");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView customIndicatorName;
        private final TextView customIndicatorDefintion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customIndicatorName = (TextView)itemView.findViewById(R.id.indicator_name_tv);
            customIndicatorDefintion = (TextView)itemView.findViewById(R.id.indicator_definition_tv);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view   ) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Utils.print("HHHHOnclick 2");
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
