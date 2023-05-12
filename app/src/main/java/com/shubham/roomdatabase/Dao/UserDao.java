package com.shubham.roomdatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shubham.roomdatabase.Entity.UserModel;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(UserModel userModel);

    @Update
    void updateUser(UserModel userModel);

    @Delete
    void DeleteUser(UserModel userModel);

//    @Query("SELECT * FROM UserModel")
//    List<UserModel> getAllUser();

    @Query("SELECT * FROM UserEmployee")
    List<UserModel> getAllUser();

    @Query("DELETE FROM UserEmployee")
    void deleteAllUser();

    @Query("SELECT * FROM UserEmployee WHERE gender = 'male' ")
    List<UserModel> getAllMales();

    @Query("SELECT * FROM UserEmployee WHERE gender = 'female' ")
    List<UserModel> getAllFemales();

    // Get a user by ID
    @Query("SELECT * FROM UserEmployee WHERE id = :id")
    UserModel getUserById(int id);


}
