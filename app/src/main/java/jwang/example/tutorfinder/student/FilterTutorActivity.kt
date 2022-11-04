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

        //Jianwei
        var filteredTutors: MutableList<Tutor> = mutableListOf()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_filter_tutor)
            degreeQuery=findViewById(R.id.courseQueryEditText)
            gradeQuery=findViewById(R.id.gradeQueryEditText)
            experienceQuery=findViewById(R.id.experienceQueryEditText)

            recyclerView = findViewById<RecyclerView>(R.id.filturedTutorsRv)

            //Jianwei
            adapter = FilteredTutorRv(this, filteredTutors)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)


        }




    fun onFilterClicked(view: View) {
        val mutListIterator = StudentDashboard.tutors.listIterator()

            for (people in StudentDashboard.tutors){
                 var x=people
if (people.degree == degreeQuery.text.toString()) {
    // if (gradeQuery.text.toString() == people.grade && degreeQuery.text.toString() == people.degree && experienceQuery.text.toString() == people.experience){

    //Jianwei
    // FilteredTutorRv.ViewHolder.mk.degreeText = people.degree
//
//    FilteredTutorRv.ViewHolder.mk.experienceText = people.experience
//    FilteredTutorRv.ViewHolder.mk.gradeText = people.grade
//    FilteredTutorRv.ViewHolder.mk.nameText = people.name


    //Jianwei
    filteredTutors.add(people)
//StudentDashboard.tutors.clear()
    //StudentDashboard.tutors.add(people)
    //recyclerView.adapter?.bindViewHolder()


    recyclerView.adapter?.notifyDataSetChanged()
    Log.d("filtration", "$people")
}
               // }
            }

    }
}