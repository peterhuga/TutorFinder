package jwang.example.tutorfinder.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class TutorFilterActivity : AppCompatActivity() {
    lateinit var adapter: FilteredTutorRv
    lateinit var recyclerView: RecyclerView
    lateinit var gradeQuery: EditText
    lateinit var degreeQuery: EditText
    lateinit var experienceQuery: EditText

    //Jianwei
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_filter)
        degreeQuery=findViewById(R.id.courseQueryEditText)
        gradeQuery=findViewById(R.id.gradeQueryEditText)
        experienceQuery=findViewById(R.id.experienceQueryEditText)

        recyclerView = findViewById<RecyclerView>(R.id.filturedTutorsRv)

        //Jianwei
        adapter = FilteredTutorRv(this, filteredTutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    fun addfilteredTutorToRV(){
        for (people in StudentDashboard.tutors){
            if (people.degree == degreeQuery.text.toString() && people.experience == experienceQuery.text.toString() && people.grade == gradeQuery.text.toString()) {

                //Jianwei
                filteredTutors.add(people)

                recyclerView.adapter?.notifyDataSetChanged()
                Log.d("filtration", "$people")
            }
            // }
        }
    }


    fun onFilterClicked(view: View) {
        filteredTutors.clear()
        addfilteredTutorToRV()
    }
}