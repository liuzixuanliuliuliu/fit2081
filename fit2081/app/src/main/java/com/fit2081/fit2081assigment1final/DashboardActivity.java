package com.fit2081.fit2081assigment1final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import android.view.GestureDetector;
import androidx.core.view.GestureDetectorCompat;
import android.view.MotionEvent;
import java.util.Locale;
import android.widget.Toast;
import android.util.Log;









import android.widget.Button;

// Assuming InvoiceViewModel, ItemAdapter, Item, and Invoice classes are correctly defined
import com.fit2081.fit2081assigment1final.provider.InvoiceViewModel;
import com.fit2081.fit2081assigment1final.provider.Item;
import com.fit2081.fit2081assigment1final.provider.Invoice;
import com.google.android.material.snackbar.Snackbar;

public class DashboardActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, InvoiceAdapter.OnInvoiceListener {
    private DrawerLayout drawerLayout;
    private GestureDetectorCompat gestureDetector;
    private InvoiceViewModel invoiceViewModel;
    private TextView txtInvoiceTotal;
    private EditText edtIssuerName, edtBuyerName, edtItemName, edtItemQuantity, edtItemCost, edtBuyerAddress;
    private SwitchCompat switchIsPaid;
    private ItemAdapter itemAdapter;

    private InvoiceAdapter invoiceAdapter;
    private List<Item> itemList = new ArrayList<>();
    private String currentInvoiceId;

    private LinearLayout itemHeader;
    private RecyclerView rvInvoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();
        setupRecyclerViews();
        setupWikiSearch();
        setupGestureDetection();



        NavigationView navigationView = findViewById(R.id.nav_view); // Your NavigationView ID
        navigationView.setNavigationItemSelectedListener(this);









        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        currentInvoiceId = generateUniqueInvoiceId();

//        invoiceViewModel.getAllInvoices().observe(this, invoices -> {
//            invoiceAdapter.setInvoices(invoices);
//            invoiceHeader.setVisibility(View.VISIBLE);
//
//        });
    }


    private void setupWikiSearch() {
        Button btnSearchWiki = findViewById(R.id.btn_search_wiki);
        btnSearchWiki.setOnClickListener(view -> {
            String itemName = edtItemName.getText().toString().trim();
            if (!itemName.isEmpty()) {
                Intent intent = new Intent(DashboardActivity.this, WebWikiActivity.class);
                intent.putExtra("ITEM_URL", "https://en.wikipedia.org/wiki/" + itemName.replace(" ", "_"));
                startActivity(intent);
            } else {
                Toast.makeText(DashboardActivity.this, "Item name is empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }








    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = findViewById(R.id.fab_add_invoice);
        fab.setOnClickListener(view -> addInvoice());

        itemHeader = findViewById(R.id.itemHeader);

        txtInvoiceTotal = findViewById(R.id.tv_invoice_total);
        edtIssuerName = findViewById(R.id.edt_issuer_name);
        edtBuyerName = findViewById(R.id.edt_buyer_name);
        edtItemName = findViewById(R.id.edt_Item_Name);
        edtItemQuantity = findViewById(R.id.edt_Item_Quantity);
        edtItemCost = findViewById(R.id.edt_Item_Cost);
        edtBuyerAddress = findViewById(R.id.edt_buyer_address);
        switchIsPaid = findViewById(R.id.switch_is_paid);

    }

    private void setupRecyclerViews() {
        // This line is looking for a RecyclerView in your layout with the ID rv_invoice_items.
        // Make sure that your layout file contains a RecyclerView with this ID.
        RecyclerView rvItems = findViewById(R.id.rv_invoice_items);
        if (rvItems != null) {
            itemAdapter = new ItemAdapter(new ArrayList<>());
            rvItems.setAdapter(itemAdapter);
            rvItems.setLayoutManager(new LinearLayoutManager(this));
        } else {
            // Handle the case where the RecyclerView is not found in your layout.
            Log.e("DashboardActivity", "RecyclerView not found in the layout");
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_add_item) {
            addItem();
        } else if (id == R.id.nav_add_invoice) {
            addInvoice();
        } else if (id == R.id.nav_clear_fields) {
            clearAllFields();
        } else if (id == R.id.nav_list_invoices) {
            listAllInvoices();
        } else if (id == R.id.nav_maps) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            exitApplication();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add_item) {
            addItem();
            return true;
        } else if (id == R.id.menu_clear_fields) {
            clearAllFields();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void addItem() {
        String itemName = edtItemName.getText().toString();
        String quantityString = edtItemQuantity.getText().toString();
        String costString = edtItemCost.getText().toString();

        if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(costString)) {
            return;
        }

        int itemQuantity = Integer.parseInt(quantityString);
        double itemCost = Double.parseDouble(costString);

        Item newItem = new Item(IdUtils.genId(), itemName, itemCost, itemQuantity, currentInvoiceId);
        itemAdapter.addItem(newItem);
        updateInvoiceTotal();

        itemHeader.setVisibility(View.VISIBLE);

    }





    private void addInvoice() {
        String issuerName = edtIssuerName.getText().toString();
        String buyerName = edtBuyerName.getText().toString();
        String buyerAddress = edtBuyerAddress.getText().toString();
        boolean isPaid = switchIsPaid.isChecked();

        Invoice newInvoice = new Invoice(currentInvoiceId, issuerName, buyerName, buyerAddress, isPaid, calculateTotal());
        invoiceViewModel.insertInvoice(newInvoice);

        edtIssuerName.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (Item item : itemList) {
                    invoiceViewModel.insertItem(item);
                }
            }
        },100);


        clearAllFields();
        itemList.clear();
        itemAdapter.notifyDataSetChanged();
        currentInvoiceId = generateUniqueInvoiceId();
        itemHeader.setVisibility(View.GONE);
    }

    private void clearAllFields() {
        edtIssuerName.setText("");
        edtBuyerName.setText("");
        edtBuyerAddress.setText("");
        edtItemName.setText("");
        edtItemQuantity.setText("");
        edtItemCost.setText("");
        switchIsPaid.setChecked(false);
        txtInvoiceTotal.setText(getString(R.string.invoice_total, 0.0));
        itemList.clear();
        itemAdapter.notifyDataSetChanged();
    }

    private void listAllInvoices() {
        Intent intent = new Intent(this, SavedInvoicesActivity.class);
        startActivity(intent);
    }

    private void exitApplication() {
        finishAffinity();
    }

    private void updateInvoiceTotal() {
        double total = calculateTotal();
        txtInvoiceTotal.setText(getString(R.string.invoice_total, total));
    }

    private double calculateTotal() {
        double total = 0;
        for (Item item : itemList) {
            total += item.getCost() * item.getQuantity();
        }
        return total;
    }

    private String generateUniqueInvoiceId() {
        return IdUtils.genId();
    }

    @Override
    public void onInvoiceClick(Invoice invoice) {
        Snackbar.make(rvInvoice, "Confirm delete?", Snackbar.LENGTH_LONG)
                .setAction("DELETE", view -> {
                    // Make sure deleteInvoice method is correctly implemented in your ViewModel
                    invoiceViewModel.deleteInvoice(invoice);
                    // Consider updating your list or notifying the adapter
                })
                .show();
    }

    private void setupGestureDetection() {
        gestureDetector = new GestureDetectorCompat(this, new GestureListener());
        View touchpadView = findViewById(R.id.touchpad_area); // Corrected to View
        touchpadView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }


    private void setupViewModelAndObservers() {
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        currentInvoiceId = generateUniqueInvoiceId();
        invoiceViewModel.getAllInvoices().observe(this, invoices -> {
            invoiceAdapter.setInvoices(invoices);

        });
    }






    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean isScrolling = false;

        @Override
        public boolean onDown(MotionEvent e) {
            // This will make sure to capture the down event
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // Add invoice on double tap and ignore other gestures
            addInvoice();
            isScrolling = false; // Reset scrolling flag
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // Add item on long press if not scrolling
            if (!isScrolling) {
                addItem();
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Set flag to true to indicate scrolling has started
            isScrolling = true;

            // Horizontal scroll adjusts item cost
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                adjustItemCost(distanceX > 0 ? -convertPixelsToCostUnits(Math.abs(distanceX)) : convertPixelsToCostUnits(Math.abs(distanceX)));
            }
            // Vertical scroll adjusts item quantity
            else {
                // Notice the inversion of distanceY sign since scrolling down should decrease the quantity
                adjustItemQuantity(distanceY > 0 ? convertPixelsToQuantityUnits(Math.abs(distanceY)) : -convertPixelsToQuantityUnits(Math.abs(distanceY)));
            }
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Reset scrolling flag when fling is detected
            isScrolling = false;
            return true;
        }
    }

// Existing methods for adjustItemCost, adjustItemQuantity, convertPixelsToCostUnits, and convertPixelsToQuantityUnits remain the same



    // Now, let's correct the adjustItemCost and adjustItemQuantity methods to properly reflect the intended behavior:
    private void adjustItemCost(float costChange) {
        try {
            float currentCost = Float.parseFloat(edtItemCost.getText().toString());
            currentCost += costChange;
            edtItemCost.setText(String.format(Locale.getDefault(), "%.2f", currentCost));
        } catch (NumberFormatException e) {
            Toast.makeText(DashboardActivity.this, "Invalid cost format", Toast.LENGTH_SHORT).show();
        }
    }

    private void adjustItemQuantity(float quantityChange) {
        try {
            int currentQuantity = Integer.parseInt(edtItemQuantity.getText().toString());
            // Add the quantity change, making sure not to go below 0
            currentQuantity = (int) Math.max(0, currentQuantity + quantityChange);
            // Update the EditText to display the new quantity
            edtItemQuantity.setText(String.valueOf(currentQuantity));
        } catch (NumberFormatException e) {
            // This catch block will trigger if edtItemQuantity doesn't contain a valid integer
            Toast.makeText(DashboardActivity.this, "Invalid quantity format", Toast.LENGTH_SHORT).show();
        }
    }

    // The conversion methods remain unchanged
    private float convertPixelsToCostUnits(float pixels) {
        return pixels / 50; // Adjust the sensitivity as needed
    }


    private int convertPixelsToQuantityUnits(float pixels) {
        // Decrease the divisor if the quantity isn't changing enough when you scroll
        // For example, changing from 50 to 30 will make each scroll action more sensitive
        return (int) (pixels / 30); // Adjust the sensitivity as needed
    }}


















