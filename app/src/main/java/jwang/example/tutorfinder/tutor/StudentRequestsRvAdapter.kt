package jwang.example.tutorfinder.tutor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.TutorScreenActivity.Companion.STUDENT_ID


class StudentRequestsRvAdapter(val context: Context, private val dataset: List<Student>): RecyclerView.Adapter<StudentRequestsRvAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            val nameTextView: TextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemName)
            val ageTextView : TextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemAge)
            val emailTextView: TextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemEmail)
            val imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageViewMyStudentItem)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_requests_rv_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.nameTextView.text = "Name: ${item.name}"
        holder.ageTextView.text = "Age: ${item.age}"
        holder.emailTextView.text = "Email: ${item.email}"
        //Hardcoded for now. Include image resource id property in data model later.
        holder.imageView.setImageResource(R.drawable.student_male)

//        onItemClick?.invoke(item)

        holder.itemView.setOnClickListener{

            //StudentRequestActivity.STUDENT_ID = item.id
            StudentRequestActivity.POSITION = position

            StudentRequestActivity.STUDENT_ID_DELETE = 0
            StudentRequestActivity.POSITION_DELETE = 0

            val intent = Intent(context,RequestedStudentProfileActivity::class.java)
            (context as Activity).startActivity(intent)

        }

    }
}