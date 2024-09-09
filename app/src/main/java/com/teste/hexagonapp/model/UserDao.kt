package com.teste.hexagonapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getActiveUsers(): List<User>

    @Query("SELECT * FROM users WHERE isActive = 0")
    fun getInactiveUsers(): List<User>

    @Query("UPDATE users SET isActive = 0 WHERE id = :userId")
    suspend fun deactivateUser(userId: Int)

    @Query("UPDATE users SET isActive = 1 WHERE id = :userId")
    suspend fun activateUser(userId: Int)
}