package com.example.roomapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomapp.model.User

// Note : this is the connection point to database ...

@Database(entities = [User::class], version = 1, exportSchema = false) // here we have one table is User in data base ..
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        var Instance  : UserDatabase? = null

        fun getDataBase(context: Context) : UserDatabase? {
            if(Instance != null){
                return Instance
            }else{
                synchronized(UserDatabase::class.java){
                    val database = Room.databaseBuilder(context.applicationContext,UserDatabase::class.java,"UserDataBase")
                        .fallbackToDestructiveMigration() // you will need this code when you need to upgrade you version of database ..
                        .build()
                    Instance = database
                }
            }
            return Instance
        }



    }

}

