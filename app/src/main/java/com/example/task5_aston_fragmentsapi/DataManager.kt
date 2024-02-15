package com.example.task5_aston_fragmentsapi

import android.content.Context
import com.google.gson.Gson
import java.io.File

object DataManager {
    private const val FILE_NAME = "users.json"
    private val initialUserList = listOf(
        User("1", "Иван", "Иванов", "89884567890"),
        User("2", "Максим", "Максимов", "8987654321"),
        User("3", "Сергей", "Сергеев", "8471324568"),
        User("4", "Илья", "Ильин", "89261451278")
    )

    private val userList = mutableListOf<User>()

    fun loadUsers(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            val gson = Gson()
            val usersJson = file.readText()
            val users = gson.fromJson(usersJson, Array<User>::class.java)
            userList.clear()
            userList.addAll(users)
        } else {
            userList.addAll(initialUserList)
        }
    }

    fun saveUsers(context: Context, userList: List<User>) {
        val file = File(context.filesDir, FILE_NAME)
        val gson = Gson()
        val usersJson = gson.toJson(userList)
        file.writeText(usersJson)
        this.userList.clear()
        this.userList.addAll(userList)
    }

    fun getAllUsers(): List<User> {
        return userList
    }

    fun updateUser(updatedUser: User) {
        val index = userList.indexOfFirst { it.id == updatedUser.id }
        if (index != -1) {
            userList[index] = updatedUser
        }
    }
}