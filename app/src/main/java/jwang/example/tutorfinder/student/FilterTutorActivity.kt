package jwang.example.tutorfinder.student

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.snapshot.Index
import jwang.example.tutorfinder.R
import kotlin.reflect.typeOf

class FilterTutorActivity : AppCompatActivity() {

    lateinit var adapter: FilteredTutorRv
    lateinit var recyclerView: RecyclerView
        lateinit var gradeQuery: EditText
        lateinit var degreeQuery: EditText
        lateinit var experienceQuery:EditText

        var filteredTutors:MutableList<Tutor> = mutableListOf()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_filter_tutor)
            degreeQuery=findViewById(R.id.courseQueryEditText)
            gradeQuery=findViewById(R.id.gradeQueryEditText)
            experienceQuery=findViewById(R.id.experienceQueryEditText)

            recyclerView = findViewById<RecyclerView>(R.id.filturedTutorsRv)
            adapter = FilteredTutorRv(this, filteredTutors)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)


        }




    fun onFilterClicked(view: View) {

            for (people in StudentDashboard.tutors){
if (people.degree == degreeQuery.text.toString()) {
    filteredTutors.remove(people)
filteredTutors.add(people)
    recyclerView.adapter?.notifyDataSetChanged()
    Log.d("filtration", "$people")
}
               // }
            }

    }
}