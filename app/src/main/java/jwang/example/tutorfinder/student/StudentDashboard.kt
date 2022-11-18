package jwang.example.tutorfinder.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R


class StudentDashboard : AppCompatActivity() {
    lateinit var adapter: MyTutorsRvAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var filterTutorNavigateBtn:Button
    companion object {
        const val TUTOR_ID = "tutor id"
        var tutors: MutableList<Tutor> = mutableListOf(
            Tutor( "John", "35","Male","London","5555555555","12","5", "John@gmail.com","Masters"),
            Tutor( "Ted", "40","Male","Toronto","1111111111","Bachelor","10", "Ted@gmail.com","Post Graduate"),
            Tutor( "Leo", "36","Male","Ottawa","2222222222","10","6", "Leo@gmail.com","Masters"),
            Tutor( "Shyam", "35","Male","London","5555555555","11","7", "Shyam@gmail.com","Bachelor"),

            )
      fun executeMyFunc(holder: FilteredTutorRv.ViewHolder, position: Int) {}

    }

//    var tutors: MutableList<Tutor> = mutableListOf(
//        Tutor(101, "John", "35","Male","London","5555555555","12","5", "John@gmail.com"),
//        Tutor(101, "Ted", "40","Male","Toronto","1111111111","Bachelor","10", "Ted@gmail.com"),
//    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
    filterTutorNavigateBtn=findViewById(R.id.filterTutorActivityBtn)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyTutor)
        adapter = MyTutorsRvAdapter(this, tutors)
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

        startActivity(Intent(this, TutorFilterActivity::class.java))
    }

    fun sentRequestTutor(view: View) {
        startActivity(Intent(this, SentRequestToTutorActivity::class.java))

    }
}