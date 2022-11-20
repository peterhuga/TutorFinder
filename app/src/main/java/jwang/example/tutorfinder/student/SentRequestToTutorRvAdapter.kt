package jwang.example.tutorfinder.student

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class SentRequestToTutorRvAdapter(val context: Context, private val dataSet: MutableList<Tutor>) :
    RecyclerView.Adapter<SentRequestToTutorRvAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var nameTextView: TextView
        lateinit var experienceTextView: TextView
        lateinit var degreeTextView: TextView
        lateinit var gradeTextView: TextView


        init {
            // Define click listener for the ViewHolder's View.
            nameTextView = view.findViewById<TextView>(R.id.sentReqTutorNameTextView)
            experienceTextView = view.findViewById<TextView>(R.id.sentReqTutorExperienceTextView)
            degreeTextView = view.findViewById<TextView>(R.id.sentReqTutorDegreeView)
            gradeTextView = view.findViewById<TextView>(R.id.sentReqTutorGradeTextView)



        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_sent_request_tutor_item, viewGroup, false)

        // add the 3 lines of code below to show 5 recycler items in the activity at a time
        val lp = view.layoutParams
        view.layoutParams = lp
        lp.height = 512
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameTextView.text = "Name: "+ dataSet[position].name
        viewHolder.experienceTextView.text = "Experience(#Years): "+dataSet[position].experience
        viewHolder.gradeTextView.text = "Grade Taught: "+dataSet[position].grade
        viewHolder.degreeTextView.text = "Academic Degree: "+dataSet[position].degree

viewHolder.itemView.setOnClickListener{

    val intent = Intent(context, MyTutorProfileActivity::class.java)
    intent.putExtra("position", position)

    (context as Activity).startActivityForResult (intent, 1)

}
    }





    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount():Int {
        return dataSet.size

    }

}

