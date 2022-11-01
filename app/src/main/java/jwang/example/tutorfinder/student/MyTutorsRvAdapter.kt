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


class MyTutorsRvAdapter(val context: Context, private val dataset: MutableList<Tutor>): RecyclerView.Adapter<MyTutorsRvAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val nameTextView = itemView.findViewById<TextView>(R.id.textViewMyTutorItemName)
            val genderTextView = itemView.findViewById<TextView>(R.id.textViewMyTutorItemGender)
            val addressTextView = itemView.findViewById<TextView>(R.id.textViewMyTutorItemAddress)
            val experienceTextView = itemView.findViewById<TextView>(R.id.textViewMyTutorItemExperience)

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
        holder.addressTextView.text = "Address: ${item.address}"
        holder.genderTextView.text = "Gender: ${item.gender}"
        holder.experienceTextView.text = "Course: ${item.experience}"


        holder.itemView.setOnClickListener{

                val intent = Intent(context, MyTutorProfileActivity::class.java)
                intent.putExtra("position", position)

            (context as Activity).startActivityForResult (intent, 1)


        }

    }
}