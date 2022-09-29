package jwang.example.tutorfinder.tutor


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.R.*
import java.lang.System.exit


class TutorScreenActivity : AppCompatActivity() {

    var deletedId = 0

    //Dummy data for populating UI
    companion object {
        val students: ArrayList<Student> = arrayListOf(
            Student(101, "Sampath", 21, "sampath@email.com"),
            Student(102, "Jianwei", 22, "jianwei@email.com"),
            Student(103,"Sunny", 19, "Sunny@email.com"),
            Student(104, "Sampath", 23, "sampath@email.com"),
            Student(105, "Jianwei", 24, "jianwei@email.com"),
            Student(106,"Sunny", 25, "Sunny@email.com")
        )


            const val STUDENT_ID = "student id"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_tutor_screen)


        val recyclerView = findViewById<RecyclerView>(id.recyclerViewMyStudents)
        val adapter = MyStudentsRvAdapter(students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter.onItemClick = {
//            val intent = Intent(this, MyStudentProfileActivity::class.java)
//            intent.putExtra(MyStudentsRvAdapter.STUDENT_ID, it.id)
//            startActivity(intent)
//        }
        supportActionBar?.title = "Tutor Portal"

    }

    override fun onResume() {
        super.onResume()
        deletedId = intent.getIntExtra(STUDENT_ID,0)
        Log.d("Student", "DeleteID, $deletedId")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_tutor_screen, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.action_bar -> {startActivity(Intent(this, EditProfileActivity::class.java))
                return true}
            id.action_settings -> {startActivity(Intent(this, StudentRequestActivity::class.java))
                return true}
        }
        return super.onOptionsItemSelected(item)
    }
}