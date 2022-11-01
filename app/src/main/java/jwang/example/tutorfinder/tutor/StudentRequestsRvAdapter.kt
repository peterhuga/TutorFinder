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

    val TAG = "Tutor"



    //var onItemClick: ((Student)->Unit)? = null



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            val nameTextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemName)
            val ageTextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemAge)
            val emailTextView = itemView.findViewById<TextView>(R.id.textViewMyStudentItemEmail)
            val imageView = itemView.findViewById<ImageView>(R.id.imageViewMyStudentItem)

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
            Log.d(TAG,"item clicked")

                /*val studentID = item.id
                val intent = Intent(it.context, RequestedStudentProfileActivity::class.java)
                intent.putExtra(STUDENT_ID, studentID)
                it.context.startActivity(intent)*/

            StudentRequestActivity.STUDENT_ID = item.id

            val intent = Intent(context,RequestedStudentProfileActivity::class.java)
            (context as Activity).startActivity(intent)

        }

    }
}