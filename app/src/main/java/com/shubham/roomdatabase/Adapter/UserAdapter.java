package com.shubham.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.roomdatabase.AddEmployeeActivity;
import com.shubham.roomdatabase.Database.DatabaseHelper;
import com.shubham.roomdatabase.Entity.UserModel;
import com.shubham.roomdatabase.MainActivity;
import com.shubham.roomdatabase.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    private static List<UserModel> userModelList;

    Context context;

    DatabaseHelper databaseHelper;

    public UserAdapter(List<UserModel> userModelList, Context context, DatabaseHelper databaseHelper) {
        this.userModelList = userModelList;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user, parent, false));
    }


    // method for filtering our recyclerview items.
    public void filterList(List<UserModel> filterlist) {
        userModelList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.

       notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        UserModel singleUnit = userModelList.get(position);

        holder.name.setText("name- " + singleUnit.getName());

        holder.phone.setText("phone- " + singleUnit.getPhone());

        holder.eId.setText("employee Id- " + String.valueOf(singleUnit.getEmployeeId()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for update
                Bundle bundle = new Bundle();
                bundle.putInt("Id", singleUnit.getId());
                bundle.putString("Name", singleUnit.getName());
                bundle.putString("FatherName", singleUnit.getFatherName());
                bundle.putString("Dob", singleUnit.getDob());
                bundle.putString("Gender", singleUnit.getGender());
                bundle.putString("Phone", singleUnit.getPhone());
                bundle.putString("Email", singleUnit.getEmail());
                bundle.putString("Address", singleUnit.getAddress());
                bundle.putString("EmployeeId", singleUnit.getEmployeeId());
                bundle.putString("Designation", singleUnit.getDesignation());
                bundle.putString("Experience", singleUnit.getExperience());
                bundle.putBoolean("MaritalStatus", singleUnit.isMaritalStatus());
                bundle.putFloat("Salary", singleUnit.getSalary());
                bundle.putString("imagePath", singleUnit.getImagePath());

                Intent intent = new Intent(context.getApplicationContext(), AddEmployeeActivity.class);
//                AddEmployee addEmp = new AddEmployee(databaseHelper, context);
//                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                intent.putExtras(bundle);
//                intent.show(fm, addEmp.getTag());
                context.startActivity(intent);

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder b = new AlertDialog.Builder(context)
                        .setTitle("Do u really want to remove this employee ???")
                        .setPositiveButton("yes proceed",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        UserModel e = databaseHelper.userDao().getUserById(singleUnit.getId());
                                        databaseHelper.userDao().DeleteUser(new UserModel(singleUnit.getId(),
                                                singleUnit.getName(), singleUnit.getFatherName(),
                                                singleUnit.getDob(), singleUnit.getGender(), singleUnit.getPhone(),
                                                singleUnit.getEmail(), singleUnit.getAddress(),
                                                singleUnit.getEmployeeId(), singleUnit.getDesignation(),
                                                singleUnit.getExperience(), singleUnit.isMaritalStatus(),
                                                singleUnit.getSalary(), singleUnit.getImagePath()));
                                        Toast.makeText(context, e.getName() + " removed successfully", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) context).showUser();
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                                }
                        );
                b.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, eId;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardEmployee);
            name = itemView.findViewById(R.id.nameEt);
            phone = itemView.findViewById(R.id.phoneEt);
            eId = itemView.findViewById(R.id.eIdEt);
        }
    }
}
