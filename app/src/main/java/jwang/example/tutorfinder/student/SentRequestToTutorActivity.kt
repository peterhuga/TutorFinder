package jwang.example.tutorfinder.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class SentRequestToTutorActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewManager: RecyclerView.LayoutManager
    var filteredTutors: MutableList<Tutor> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent_request_to_tutor)
        recyclerView = findViewById(R.id.sentRequestTutorRV)
        recyclerViewManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = recyclerViewManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SentRequestToTutorRvAdapter(this,StudentDashboard.tutors)
//
//        for (person in StudentDashboard.tutors){
//    filteredTutors.add(person)
//}

    }

}
