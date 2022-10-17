package jwang.example.tutorfinder.student

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.MyStudentsRvAdapter
import jwang.example.tutorfinder.tutor.Student
import jwang.example.tutorfinder.tutor.TutorScreenActivity

class StudentDashboard : AppCompatActivity() {
    lateinit var adapter: MyStudentsRvAdapter
    lateinit var recyclerView: RecyclerView
    companion object {
        const val TUTOR_ID = "tutor id"
    }
    var tutors: MutableList<Student> = mutableListOf(
        Student(101, "Sampath", 21, "sampath@email.com"),
        Student(102, "Jianwei", 22, "jianwei@email.com"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyTutor)
        adapter = MyStudentsRvAdapter(this, tutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode == RESULT_OK){
//
//            if(requestCode==1){
//
//                if (data != null) {
//                    val deleteId = data.getIntExtra(StudentDashboard.TUTOR_ID,0)
//                    val position = data.getIntExtra("position", -1)
//                    Log.d("Student", "DltID, $deleteId")
//                    val mytutor = tutors.filter { student -> student.id != deleteId } as MutableList<Student>
//                    tutors.clear()
//                    tutors.addAll(mytutor)
//                    Log.d("Student", "size: ${tutors.size}")
//                    adapter.notifyItemRemoved(position)
//
//
//                }
//            }
//        }
//    }



    fun onEditProfileBtnClicked(view: View) {
        startActivity(Intent(this,EditStudentProfile::class.java))
    }
    fun onFilterBtnPressed(view: View) {
        startActivity(Intent(this,FilterTutorActivity::class.java))
    }
}