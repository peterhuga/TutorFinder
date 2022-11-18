package jwang.example.tutorfinder.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class TutorFilterActivity : AppCompatActivity() {
    lateinit var adapter: FilteredTutorRv
    lateinit var recyclerView: RecyclerView
    lateinit var gradeSpinner: Spinner
    lateinit var degreeSpinner: Spinner
    lateinit var experienceSpinner: Spinner

    //Jianwei
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_filter)
        degreeSpinner=findViewById(R.id.academicDegreeSpinner)
        gradeSpinner=findViewById(R.id.gradeSpinner)
        experienceSpinner=findViewById(R.id.experienceSpinner)

        val degreespinneradapter = ArrayAdapter.createFromResource(
            this,
            R.array.academicDegreeSpinner,
            android.R.layout.simple_spinner_item
        )
        degreespinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        degreeSpinner.adapter = degreespinneradapter
       // degreeSpinner.onItemSelectedListener = this


        val gradepinneradapter = ArrayAdapter.createFromResource(
            this,
            R.array.gradeSpinner,
            android.R.layout.simple_spinner_item
        )
        gradepinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeSpinner.adapter = gradepinneradapter
       // gradeSpinner.onItemSelectedListener = this


        val experiencepinneradapter = ArrayAdapter.createFromResource(
            this,
            R.array.ExpereienceSpinner,
            android.R.layout.simple_spinner_item
        )
        experiencepinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        experienceSpinner.adapter = experiencepinneradapter
        //gradeSpinner.onItemSelectedListener = this

        recyclerView = findViewById<RecyclerView>(R.id.filturedTutorsRv)

        //Jianwei
        adapter = FilteredTutorRv(this, filteredTutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
//    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//       // textView.setText(p0?.getItemAtPosition(p2).toString())
//grad = p0?.getItemAtPosition(p2).toString()
//        // you can also use this command anywhere outside of the onItemSelected
//        //       textView.setText(spinner.selectedItem.toString())
//    }
//
//    override fun onNothingSelected(p0: AdapterView<*>?) {
//        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_LONG).show()
//    }

    fun addfilteredTutorToRV(){
        for (people in StudentDashboard.tutors){
            if (people.degree == degreeSpinner.selectedItem.toString() ) {
//                && people.experience == experienceSpinner.selectedItem.toString() && people.grade == gradeSpinner.selectedItem.toString()
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