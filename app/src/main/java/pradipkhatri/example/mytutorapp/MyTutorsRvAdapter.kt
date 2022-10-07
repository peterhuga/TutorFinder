package pradipkhatri.example.mytutorapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pradipkhatri.example.mytutorapp.StudentActivity.Companion.STUDENT_ID
import java.util.*


class MyTutorsRvAdapter(val context: Context, private val dataset: MutableList<Tutor>): RecyclerView.Adapter<MyTutorsRvAdapter.ViewHolder>() {

    val TAG = "Tutor"



    //var onItemClick: ((Student)->Unit)? = null



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var mId: UUID? = null
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
            .inflate(R.layout.my_tutor_rv_item, parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = dataset[position]
        holder.nameTextView.text = "Name: ${item.name}"
        holder.ageTextView.text = "Age: ${item.age}"
        holder.emailTextView.text = "Email: ${item.email}"
        //Hardcoded for now. Include image resource id property in data model later.
        //holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)

//        onItemClick?.invoke(item)


        holder.itemView.setOnClickListener{
            Log.d(TAG,"item clicked")

                val intent = Intent(context, MyTutorProfileActivity::class.java)
               // intent.putExtra(STUDENT_ID, studentID)
                intent.putExtra("position", position)

            (context as Activity).startActivityForResult (intent, 1)


        }

    }
}