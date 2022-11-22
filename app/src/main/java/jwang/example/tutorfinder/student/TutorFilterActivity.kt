package jwang.example.tutorfinder.student

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class TutorFilterActivity : AppCompatActivity() {
    lateinit var adapter: FilteredTutorRv
    lateinit var recyclerView: RecyclerView
    lateinit var gradeSpinner: Spinner
    lateinit var degreeSpinner: Spinner
    lateinit var experienceSpinner: Spinner
    val NOTIFICATION_ID = 111
    val CHANNEL_ID = "101"
    //Jianwei
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()
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
fun createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channnel_desc)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
    fun addfilteredTutorToRV(){

        for (people in StudentDashboard.tutors){
//            if (people.degree == degreeSpinner.selectedItem.toString()){
//               // && people.experience == experienceSpinner.selectedItem.toString() && people.grade == gradeSpinner.selectedItem.toString()){
//        //Jianwei
//                filteredTutors.add(people)
//                recyclerView.adapter?.notifyDataSetChanged()
//            }
            if(people.degree == degreeSpinner.selectedItem.toString() && people.experience == experienceSpinner.selectedItem.toString() && people.grade==gradeSpinner.selectedItem.toString()){
               // filteredTutors.clear()
                filteredTutors.add(people)
                recyclerView.adapter?.notifyDataSetChanged()
            }


        }
}
//            if(!degreeSpinner.isSelected) {
//                filteredTutors.add(people)
//                recyclerView.adapter?.notifyDataSetChanged()
//                    if (degreeSpinner.selectedItem.toString() == people.degree) {
//                        filteredTutors.add(people)
//                        recyclerView.adapter?.notifyDataSetChanged()
//                    } else {
//                        Toast.makeText(
//                            baseContext, "No degree result found",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//            }
//                    else {
//                    Toast.makeText(
//                        baseContext, "Academic degree can't be blank",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                }
//
//}

//                filteredTutors.add(people)
//                recyclerView.adapter?.notifyDataSetChanged()
//


//                if(gradeSpinner.isSelected && gradeSpinner.selectedItem.toString()==people.grade){
//                    filteredTutors.add(people)
//                    recyclerView.adapter?.notifyDataSetChanged()
//                    if(experienceSpinner.isSelected && experienceSpinner.selectedItem.toString()==people.experience){
//                        filteredTutors.add(people)
//                        recyclerView.adapter?.notifyDataSetChanged()
//
//
//                    }
//                    else{
//                        //filteredTutors.clear()
//                        recyclerView.adapter?.notifyDataSetChanged()
//                        Toast.makeText(baseContext, "No exp Result found",
//                     Toast.LENGTH_SHORT).show()
//                    }
//                }
//                else{
//                    recyclerView.adapter?.notifyDataSetChanged()
//                    Toast.makeText(baseContext, "No grade Result found",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//            else if(degreeSpinner.isNotEmpty() && degreeSpinner.selectedItem.toString() !=people.degree){
//                recyclerView.adapter?.notifyDataSetChanged()
//                Toast.makeText(baseContext, "Nodeg  Result found",
//                    Toast.LENGTH_SHORT).show()
//            }
//             if(degreeSpinner.selectedItem.toString() ==people.degree &&  gradeSpinner.selectedItem.toString()==people.grade){
//                filteredTutors.add(people)
//                recyclerView.adapter?.notifyDataSetChanged()
//                 Toast.makeText(baseContext, "sgs",
//                     Toast.LENGTH_SHORT).show()
//
//            }
//             else if(degreeSpinner.selectedItem.toString() !=people.degree ||  gradeSpinner.selectedItem.toString()!=people.grade ){
//                //filteredTutors.add(people)
//                //recyclerView.adapter?.notifyDataSetChanged()
//
//                 Toast.makeText(baseContext, "",
//                Toast.LENGTH_SHORT).show()
//
//            }






    fun onFilterClicked(view: View) {
        filteredTutors.clear()
        addfilteredTutorToRV()
    }
    fun sendPhoneNotification() {
        recyclerView.findViewHolderForLayoutPosition(0)
        //val mail = Uri.parse("mailto:xyz@gmail.com")
        val intent = Intent(this,SentRequestToTutorActivity::class.java).apply {
            type = "image/*"
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.phone_12)
        // The line below is equivalent to the line above.  You may build up the Builder either way
        //       builder.setSmallIcon(R.drawable.android);
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.phone_icon))
        builder.setContentTitle("Notification for Friend Request Sent")
        builder.setContentText("Press to View List of Sent Request")
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
    fun onSendRequestTapped(view: View) {
     findViewById<Button>(R.id.sendRequestToTutorBtn).text="Request Sent"
        sendPhoneNotification()
    }
}