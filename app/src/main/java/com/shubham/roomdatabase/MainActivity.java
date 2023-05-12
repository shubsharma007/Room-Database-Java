package com.shubham.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.shubham.roomdatabase.Adapter.UserAdapter;
import com.shubham.roomdatabase.Database.DatabaseHelper;
import com.shubham.roomdatabase.Entity.UserModel;
import com.shubham.roomdatabase.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    List<UserModel> userModelList;

    DatabaseHelper databaseHelper;

    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userModelList = new ArrayList<>();
        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        showUser();

        binding.addFirstEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);

//                AddEmployee addEmp = new AddEmployee(databaseHelper, MainActivity.this);
//                addEmp.show(getSupportFragmentManager(), addEmp.getTag());
            }
        });

        binding.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addFirstEmployee.performClick();
            }
        });

        binding.deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.userDao().deleteAllUser();
                showUser();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }
    private void filter(String text) {
        ArrayList<UserModel> filteredlist = new ArrayList<>();

        for (UserModel item : userModelList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            userAdapter.filterList(filteredlist);
        }
    }

    public void showUser() {

        if (databaseHelper.userDao().getAllUser().size() > 0) {
            userModelList = databaseHelper.userDao().getAllUser();
            userAdapter = new UserAdapter(userModelList, MainActivity.this, databaseHelper);
            binding.recyclerViewEmployees.setAdapter(userAdapter);
            binding.recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            binding.nothingFoundCard.setVisibility(View.GONE);
            binding.deleteAllBtn.setVisibility(View.VISIBLE);
            binding.recyclerViewEmployees.setVisibility(View.VISIBLE);
            binding.searchView.setVisibility(View.VISIBLE);
        } else {
            binding.deleteAllBtn.setVisibility(View.GONE);
            binding.nothingFoundCard.setVisibility(View.VISIBLE);
            binding.recyclerViewEmployees.setVisibility(View.GONE);
            binding.searchView.setVisibility(View.GONE);
        }

    }
}