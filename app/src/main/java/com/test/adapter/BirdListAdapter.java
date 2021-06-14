package com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.test.BR;
import com.test.MainActivity;
import com.test.R;
import com.test.databinding.AdapterBirdItemBinding;
import com.test.model.firstfragmentbird.BirdItem;
import com.test.ui.BirdItemClickListener;
import java.util.List;
import io.reactivex.annotations.NonNull;

public class BirdListAdapter extends RecyclerView.Adapter<BirdListAdapter.MyViewHolder> implements BirdItemClickListener {
    private List<BirdItem> birdItemList;
    private ClickListener clickListener;

    public BirdListAdapter(List<BirdItem> birdItemList, ClickListener clickListener) {
        this.birdItemList = birdItemList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterBirdItemBinding adapterBirdItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_bird_item, parent, false);
        return new MyViewHolder(adapterBirdItemBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BirdItem birdItem = birdItemList.get(position);
        holder.bind(birdItem);
        holder.adapterBirdItemBinding.setItemClickListener(this);
    }

    @Override
    public void cardClicked(BirdItem birdItem) {
        if (birdItem.isClicked()) {
            birdItem.setClicked(false);
            clickListener.onItemClick(null);
        } else {
            for (BirdItem birdItemLocal : birdItemList) {
                if (!birdItemLocal.equals(birdItem))
                    birdItemLocal.setClicked(false);
            }
            birdItem.setClicked(true);
            clickListener.onItemClick(birdItem);
        }
        this.notifyDataSetChanged();
        MainActivity.RX.onNext("You clicked on a " + birdItem.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public AdapterBirdItemBinding adapterBirdItemBinding;

        MyViewHolder(AdapterBirdItemBinding adapterBirdItemBinding) {
            super(adapterBirdItemBinding.getRoot());
            this.adapterBirdItemBinding = adapterBirdItemBinding;
        }

        public void bind(Object obj) {
            adapterBirdItemBinding.setVariable(BR.birdItem, obj);
            adapterBirdItemBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return birdItemList.size();
    }

    /**
     * To catch the Bird list item click
     */
    public interface ClickListener {
        void onItemClick(BirdItem birdItem);
    }
}
