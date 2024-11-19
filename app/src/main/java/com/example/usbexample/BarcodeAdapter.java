package com.example.usbexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.BarcodeViewHolder> {

    private final List<String> barcodes;

    public BarcodeAdapter() {
        this.barcodes = new ArrayList<>();
    }

    public BarcodeAdapter(List<String> barcodes) {
        this.barcodes = barcodes;
    }

    @NonNull
    @Override
    public BarcodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barcode, parent, false);
        return new BarcodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarcodeViewHolder holder, int position) {
        holder.bind(barcodes.get(position));
    }

    @Override
    public int getItemCount() {
        return barcodes.size();
    }

    static class BarcodeViewHolder extends RecyclerView.ViewHolder {
        private final TextView barcodeText;

        BarcodeViewHolder(@NonNull View itemView) {
            super(itemView);
            barcodeText = itemView.findViewById(R.id.barcodeText);
        }

        void bind(String barcode) {
            barcodeText.setText(barcode);
        }
    }
}
