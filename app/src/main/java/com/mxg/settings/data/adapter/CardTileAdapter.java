// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.data.adapter;

import androidx.annotation.NonNull;

import com.mxg.settings.data.viewholder.CardTileViewHolder;

import java.util.List;

public class CardTileAdapter extends CardTileBaseAdapter {

    public CardTileAdapter(List<String> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTileViewHolder holder, int position) {
        if (!mData.isEmpty()) {
            String tile = mData.get(position);
            holder.mIcon.setBackgroundResource(getCardIcon(holder.itemView.getContext(), tile));
            holder.mTitle.setText(getCardTitle(holder.itemView.getContext(), tile));
            holder.mTileMark.setChecked(true);
            holder.mTileMark.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (!isChecked) {
                    onDataChangeListener.onDataSetChanged(isChecked, tile);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
