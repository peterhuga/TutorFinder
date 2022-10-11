package pradipkhatri.example.mytutorapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import pradipkhatri.example.mytutorapp.R.*


class StudentActivity : AppCompatActivity() {
    lateinit var adapter: MyTutorsRvAdapter
    lateinit var recyclerView: RecyclerView

    var tutors: MutableList<Tutor> = mutableListOf(
        Tutor( "Sampath", "21", "male","london","1234567890","2","math","xyz@gmail.com",),

    )
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_student)
        recyclerView = findViewById<RecyclerView>(id.recyclerViewMyTutor)
        adapter = MyTutorsRvAdapter(this, tutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }



}