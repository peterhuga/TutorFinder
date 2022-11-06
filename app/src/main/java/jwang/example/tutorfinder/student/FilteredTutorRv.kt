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
import jwang.example.tutorfinder.student.StudentDashboard.Companion.executeMyFunc


class FilteredTutorRv(val context: Context, private val dataset: MutableList<Tutor>): RecyclerView.Adapter<FilteredTutorRv.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

//        Jianwei
//           companion object mk{
//                var nameText:String=""
//                var gradeText:String=""
//                var degreeText:String=""
//                var experienceText:String=""
//
//
//           }

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
      //  holder.bind(item)
        //holder.bind(dataset[position])

//Jianwei
//item.name = ViewHolder.nameText
//        item.degree = ViewHolder.nameText
//        item.grade = ViewHolder.nameText
//        item.experience = ViewHolder.nameText


        holder.nameTextView.text = "Name: ${item.name}"
        holder.degreeTextView.text = "Academic Degree: ${item.degree}"
        holder.gradeTextView.text = "Grade: ${item.grade}"
        holder.experienceTextView.text = "Experience(#Years): ${item.experience}"
//       // executeMyFunc(holder,position)

//
//        holder.itemView.setOnClickListener{
//
//                val intent = Intent(context, FilterTutorActivity::class.java)
//                intent.putExtra("position", position)
//
//            (context as Activity).startActivityForResult (intent, 1)
//
//
//        }

    }
}