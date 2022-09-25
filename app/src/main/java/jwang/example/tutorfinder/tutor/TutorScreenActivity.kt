package jwang.example.tutorfinder.tutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class TutorScreenActivity : AppCompatActivity() {

    //Dummy data for populating UI
    companion object {
        val students: ArrayList<Student> = arrayListOf(
            Student(101, "Sampath", 21, "sampath@email.com"),
            Student(102, "Jianwei", 22, "jianwei@email.com"),
            Student(103,"Sunny", 19, "Sunny@email.com"),
                    Student(101, "Sampath", 21, "sampath@email.com"),
        Student(102, "Jianwei", 22, "jianwei@email.com"),
        Student(103,"Sunny", 19, "Sunny@email.com")
        )


            const val STUDENT_ID = "student id"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_screen)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyStudents)
        val adapter = MyStudentsRvAdapter(students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter.onItemClick = {
//            val intent = Intent(this, MyStudentProfileActivity::class.java)
//            intent.putExtra(MyStudentsRvAdapter.STUDENT_ID, it.id)
//            startActivity(intent)
//        }
    }

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.buttonEditProfile -> startActivity(Intent(this, EditProfileActivity::class.java))
            R.id.buttonShowStudentRequest -> startActivity(Intent(this, StudentRequestActivity::class.java))
        }
    }
}