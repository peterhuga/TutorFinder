package jwang.example.tutorfinder.tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class StudentRequestActivity : AppCompatActivity() , OnClickListener{

    //Dummy data for populating UI
    companion object {
        val students: ArrayList<Student> = arrayListOf(
            Student(101, "Sampath", 21, "sampath@email.com"),
            Student(102, "Jianwei", 22, "jianwei@email.com"),
            Student(103,"Sunny", 19, "Sunny@email.com")
        )


        const val STUDENT_ID = "student id"

    }

    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_request)

        buttonBack = findViewById(R.id.buttonBack)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStudentRequests)
        val adapter = MyStudentsRvAdapter(TutorScreenActivity.students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        finish()
    }
}