package com.example.mobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.userRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        adapter.userList = loadUsers()
        adapter.notifyDataSetChanged()
    }

    private fun loadUsers(): List<User> {
        return listOf(
            User(
                avatarUrl = "",
                userName = "User name 0",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 1",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 2",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 3",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 4",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 5",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 6",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 7",
                groupName = "C"
            ),
            User(
                avatarUrl = "",
                userName = "User name 8",
                groupName = "C"
            ),
            User(
                avatarUrl = "",
                userName = "User name 9",
                groupName = "C"
            )
        )
    }
}