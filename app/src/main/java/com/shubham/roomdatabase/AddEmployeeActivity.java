package com.shubham.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.shubham.roomdatabase.Database.DatabaseHelper;
import com.shubham.roomdatabase.Entity.UserModel;
import com.shubham.roomdatabase.databinding.ActivityAddEmpolyeeBinding;

import java.io.File;

public class AddEmployeeActivity extends AppCompatActivity {
    public static final int IMAGE_CODE = 987;
    ActivityAddEmpolyeeBinding binding;

    DatabaseHelper databaseHelper;
    String gender;
    String name;
    String fatherName;
    String dob;
    String phone;
    String email;
    String address;
    String employeeId;
    String designation;
    String experience;
    String salary;
    String imagePath = null;
    int Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmpolyeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = DatabaseHelper.getInstance(AddEmployeeActivity.this);

        if (getIntent().getExtras() != null) {
            binding.saveBtn.setText("save changes");
            binding.nameEt.setText(getIntent().getStringExtra("Name"));
            binding.fatherNameEt.setText(getIntent().getStringExtra("FatherName"));
            binding.dobEt.setText(getIntent().getStringExtra("Dob"));
            binding.phoneEt.setText(getIntent().getStringExtra("Phone"));
            binding.emailEt.setText(getIntent().getStringExtra("Email"));
            binding.addressEt.setText(getIntent().getStringExtra("Address"));
            binding.employeeId.setText(getIntent().getStringExtra("EmployeeId"));
            binding.designitionEt.setText(getIntent().getStringExtra("Designation"));
            binding.experienceEt.setText(getIntent().getStringExtra("Experience"));
            binding.salaryEt.setText(String.valueOf(getIntent().getExtras().getFloat("Salary")));
            if (getIntent().getExtras().getString("imagePath") != null) {
                getUriFromImagePath(getIntent().getExtras().getString("imagePath"));
            }


            if (getIntent().getExtras().getString("Gender") != null) {
                if (getIntent().getExtras().getString("Gender").equals("male")) {
                    binding.rbMale.setChecked(true);
                } else {
                    binding.rbFemale.setChecked(true);
                }
            }


            if (getIntent().getExtras().getBoolean("MaritalStatus")) {
                binding.rbMarried.setChecked(true);
            } else {
                binding.rbUnmarried.setChecked(true);
            }
        }

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK);
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, IMAGE_CODE);
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedIdGender = binding.rgGender.getCheckedRadioButtonId();
                RadioButton genderRb = (RadioButton) findViewById(selectedIdGender);

                int selectedIdMaritalStatus = binding.rgMaritalStatus.getCheckedRadioButtonId();
                RadioButton maritalStatus = (RadioButton) findViewById(selectedIdMaritalStatus);
                boolean marriage = false;


                if (getIntent().getExtras() != null) {
                    Id = getIntent().getExtras().getInt("Id");


                    name = binding.nameEt.getText().toString();
                    fatherName = binding.fatherNameEt.getText().toString();
                    dob = binding.dobEt.getText().toString();
                    phone = binding.phoneEt.getText().toString();
                    email = binding.emailEt.getText().toString();
                    address = binding.addressEt.getText().toString();
                    employeeId = binding.employeeId.getText().toString();
                    designation = binding.designitionEt.getText().toString();
                    experience = binding.experienceEt.getText().toString();
                    salary = binding.salaryEt.getText().toString();
                    gender = genderRb.getText().toString();
                    if (maritalStatus.getText().toString().equals("married")) {
                        marriage = true;
                    }
                    addNewEmployeeFunc(Id, name, fatherName, dob, gender, phone, email, address, employeeId, designation, experience, marriage, Float.parseFloat(salary), imagePath);

                } else {
                    name = binding.nameEt.getText().toString();
                    fatherName = binding.fatherNameEt.getText().toString();
                    dob = binding.dobEt.getText().toString();
                    phone = binding.phoneEt.getText().toString();
                    email = binding.emailEt.getText().toString();
                    address = binding.addressEt.getText().toString();
                    employeeId = binding.employeeId.getText().toString();
                    designation = binding.designitionEt.getText().toString();
                    experience = binding.experienceEt.getText().toString();
                    salary = binding.salaryEt.getText().toString();
                    if (selectedIdGender == -1) {
                        gender = null;
                    } else {
                        gender = genderRb.getText().toString();
                    }

                    if (selectedIdMaritalStatus != -1) {
                        if (maritalStatus.getText().toString().equals("married")) {
                            marriage = true;
                        }
                    }

                    if (!binding.salaryEt.getText().toString().isEmpty()) {
                        addNewEmployeeFunc(0, name, fatherName, dob, gender, phone, email, address, employeeId, designation, experience, marriage, Float.parseFloat(salary), imagePath);
                    } else {
                        addNewEmployeeFunc(0, name, fatherName, dob, gender, phone, email, address, employeeId, designation, experience, marriage, 0, imagePath);
                    }

                }

            }
        });
    }
    private void getUriFromImagePath(String imageP) {
        File imageFile;
        Uri imageUri;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            imageFile = new File(imageP);
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageFile.getName());
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            imagePath = getRealPathFromURI(imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            binding.imageView.setImageBitmap(bitmap);
        } else {
            imageFile = new File(imageP);
            imageUri = Uri.fromFile(imageFile);
            imagePath = getRealPathFromURI(imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            binding.imageView.setImageBitmap(bitmap);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_CODE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    binding.imageView.setImageURI(selectedImageUri);
                    imagePath = getRealPathFromURI(data.getData());
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void addNewEmployeeFunc(int Id, String name, String fatherName, String dob,
                                   String gender, String phone,
                                   String email, String address,
                                   String employeeId, String designation,
                                   String experience, boolean maritalStatus,
                                   float salary, String image) {

        if (Id == 0) {
            databaseHelper.userDao().addUser(new UserModel(name, fatherName, dob, gender, phone, email, address, employeeId, designation, experience, maritalStatus, salary, image));
            Toast.makeText(AddEmployeeActivity.this, "employee added successfully", Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper.userDao().updateUser(new UserModel(Id, name, fatherName, dob, gender, phone, email, address, employeeId, designation, experience, maritalStatus, salary, image));
            Toast.makeText(AddEmployeeActivity.this, "employee updated successfully", Toast.LENGTH_SHORT).show();
        }
//        finish();

        ((MainActivity) getApplicationContext()).showUser();
    }

}