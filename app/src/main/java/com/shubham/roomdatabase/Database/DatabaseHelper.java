package com.shubham.roomdatabase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shubham.roomdatabase.Dao.UserDao;
import com.shubham.roomdatabase.Entity.UserModel;

@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public static final String DATABASE_NAME = "userDatabase";

    public abstract UserDao userDao();

    public static DatabaseHelper getInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (getInstance == null) {
            getInstance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return getInstance;
    }
}
