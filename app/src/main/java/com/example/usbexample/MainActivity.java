package com.example.usbexample;

import android.os.Bundle;

import android.view.KeyEvent;
// import android.widget.Toast; // Unused import removed
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final StringBuilder barcodeBuilder = new StringBuilder();
    private final List<String> barcodeList = new ArrayList<>();
    private BarcodeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up RecyclerView for displaying scanned barcodes
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BarcodeAdapter(barcodeList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        // Barcode scanner usually sends a KEYCODE_ENTER event at the end of a scan
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            String barcode = barcodeBuilder.toString();
            barcodeBuilder.setLength(0); // Clear the builder for the next scan
            barcodeList.add(barcode); // Add scanned barcode to the list
            adapter.notifyItemInserted(barcodeList.size() - 1); // Update RecyclerView
            return true;
        } else {
            char pressedKey = (char) event.getUnicodeChar();
            barcodeBuilder.append(pressedKey); // Append character to barcode
            return super.onKeyDown(keyCode, event);
        }
    }
}
