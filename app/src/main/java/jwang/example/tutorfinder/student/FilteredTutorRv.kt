package jwang.example.tutorfinder.student

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.TutorScreenActivity


class FilteredTutorRv(val context: Context, private val dataset: MutableList<Tutor>): RecyclerView.Adapter<FilteredTutorRv.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val nameTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorNameView)

            val gradeTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorGradeView)
            val degreeTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorDegreeView)
            val experienceTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorExperienceView)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filtered_tutor_item, parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = dataset[position]
        holder.nameTextView.text = "Name: ${item.name}"
        holder.degreeTextView.text = "Academic Degree: ${item.education}"
        holder.gradeTextView.text = "Grade: ${item.grades}"
        holder.experienceTextView.text = "Experience(#Years): ${item.experience}"
//       // executeMyFunc(holder,position)

//
//        holder.itemView.setOnClickListener {
//
//            val builder = AlertDialog.Builder(holder.itemView.context)
//
//            builder.setTitle("Are you sure to delete this student?")
//            builder.setCancelable(false)
//            builder.setPositiveButton("Confirm") {
//                //send student id back to parent to delete it
//                    dialog, which ->
//
//
//                builder.setNegativeButton("Cancel") { dialog, which ->
//                    dialog.cancel()
//                }
//                builder.show()
//
//
//            }
//
//        }
    }
}