package com.teste.hexagonapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthDate: Date,
    val cpf: String,
    val city: String,
    val photoUri: String,
    val isActive: Boolean
)