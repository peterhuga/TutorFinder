package jwang.example.tutorfinder.student

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R


class FilteredTutorRv(val context: Context, private val dataset: MutableList<Tutor>): RecyclerView.Adapter<FilteredTutorRv.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val nameTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorNameView)
val sendRequestToTutor:Button = itemView.findViewById(R.id.sendRequestToTutorBtn)
            val gradeTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorGradeView)
            val degreeTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorDegreeView)
            val experienceTextView: TextView = itemView.findViewById<TextView>(R.id.filteredtutorExperienceView)
        val NOTIFICATION_ID = 111
        val CHANNEL_ID = "101"
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
        holder.degreeTextView.text = "Academic Degree: ${item.degree}"
        holder.gradeTextView.text = "Grade: ${item.grade}"
        holder.experienceTextView.text = "Experience(#Years): ${item.experience}"
//       // executeMyFunc(holder,position)
//        holder.sendRequestToTutor.setOnClickListener{
//
//        }
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
    fun sendPhoneNotification() {
        //val mail = Uri.parse("mailto:xyz@gmail.com")
        val NOTIFICATION_ID = 111
        val CHANNEL_ID = "101"
        val intent = Intent(context,EditStudentProfile::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.phone_12)
        // The line below is equivalent to the line above.  You may build up the Builder either way
        //       builder.setSmallIcon(R.drawable.android);
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
      //  builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.phone_icon))
        builder.setContentTitle("Express is waiting for your response")
        builder.setContentText("Press to mail Pradip!")
       // val notificationManager = getSystemService(
            //AppCompatActivity.NOTIFICATION_SERVICE
        //) as NotificationManager
        //notificationManager.notify(NOTIFICATION_ID, builder.build())
    }


}