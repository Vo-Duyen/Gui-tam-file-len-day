package com.example.spending_management.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.spending_management.adapters.TransactionAdapter;
import com.example.spending_management.models.Transaction;
import com.example.spending_management.utils.Constants;
import com.example.spending_management.utils.Helper;
import com.example.spending_management.viewmodels.MainViewModel;
import com.example.spending_management.views.fragments.AddTransactionFragment;
import com.example.spending_management.R;
import com.example.spending_management.databinding.ActivityMainBinding;
import com.example.spending_management.views.fragments.ClickInfor;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");
        Constants.setCategories();
        calendar = Calendar.getInstance();
        updateDate();
        binding.nextDateBtn.setOnClickListener(c-> {
            if(Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            } else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });
        binding.previousDateBtn.setOnClickListener(c-> {
            if(Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            } else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });
        binding.floatingActionButton.setOnClickListener(c -> {
            new AddTransactionFragment().show(getSupportFragmentManager(), null);
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")) {
                    Constants.SELECTED_TAB = 1;
                    updateDate();
                } else if(tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB = 0;
                    updateDate();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionAdapter transactionsAdapter = new TransactionAdapter(MainActivity.this, transactions, transaction -> {
                    // Xử lý khi item được click
                    long id = transaction.getId();
                    new ClickInfor(id).show(getSupportFragmentManager(), null);
                });
                binding.transactionList.setAdapter(transactionsAdapter);
                if(transactions.size() > 0) {
                    binding.emptyState.setVisibility(View.GONE);
                } else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                DecimalFormat df = new DecimalFormat("#");
                binding.incomeLbl.setText(String.valueOf(df.format(aDouble)));
            }
        });
        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                DecimalFormat df = new DecimalFormat("#");
                binding.expenseLbl.setText(String.valueOf(df.format(aDouble)));
            }
        });
        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                DecimalFormat df = new DecimalFormat("#");
                binding.totalLbl.setText(String.valueOf(df.format(aDouble)));
            }
        });
        viewModel.getTransactions(calendar);
    }
    public void getTransactions() {
        viewModel.getTransactions(calendar);
    }
    void updateDate() {
        if(Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar);
    }
    public MenuItem searchItem, thongBao;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        searchItem = menu.findItem(R.id.search);
        thongBao = menu.findItem(R.id.ThongBao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item == searchItem)
        {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            EditText editText = new EditText(this);
//            editText.setHint("Nhập từ khóa");
//            builder.setTitle("Tìm kiếm")
//                    .setView(editText)
//                    .setPositiveButton("OK", (dialog, which) -> {
//                        Toast.makeText(MainActivity.this, "Long", Toast.LENGTH_SHORT).show();
//                    })
//                    .setNegativeButton("Thoát", null)
//                    .show();
            Toast.makeText(this, "Vui lòng đăng ký Vip để tìm kiếm!", Toast.LENGTH_SHORT).show();
        }
        else if (item == thongBao)
        {
            Toast.makeText(this, "Không có thông báo!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}