package jwang.example.tutorfinder.student

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.StudentRequestActivity


class TutorFilterActivity : AppCompatActivity() {
    lateinit var adapter: FilteredTutorRv
    lateinit var myObj:String
    lateinit var myidOb:jwang.example.tutorfinder.tutor.Student
    lateinit var mytestObj:Any
    lateinit var recyclerView: RecyclerView
    lateinit var gradeSpinner: Spinner
    lateinit var degreeSpinner: Spinner
    lateinit var experienceSpinner: Spinner
    val NOTIFICATION_ID = 111
    var id=1
    val CHANNEL_ID = "101"
    private val database = Firebase.database.reference
    val currentUser = FirebaseAuth.getInstance().currentUser
    var TutorDatabaseKey: MutableList<String> = mutableListOf()
    var tutorListFromDatabase: MutableList<Tutor> = mutableListOf()
lateinit var sendRequestToTutorButton:Button
    //Jianwei
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sendRequestToTutorButton=findViewById(R.id.sendRequestToTutorBtn)
        //createNotificationChannel()
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
        database.child("users").orderByChild("role").equalTo("tutor").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //recyclerView.adapter?.notifyDataSetChanged()
                // Log.d("myTag", "notify2: ${TutorDatabaseKeys}")
                // tvStudentAmount.text = "You have ${TutorScreenActivity.students.size} student(s)"

            }


            override fun onCancelled(error: DatabaseError) {
            }
        })



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
//fun createNotificationChannel() {
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channnel_desc)
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}
    fun addfilteredTutorToRV() {
                for (tutor in tutorListFromDatabase) {
                    if (tutor.experience == experienceSpinner.selectedItem.toString()){

                        filteredTutors.add(tutor)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
    }






    fun onFilterClicked(view: View) {
        //filteredTutors.clear()
            val listPersonType = object : TypeToken<MutableList<Tutor>>() {}.type
            tutorListFromDatabase = Gson().fromJson(myObj, listPersonType)
            //var testmodel = i.getValue(Tutor::class.java)
            // var xyz = Gson().fromJson(myObjt, Tutor::class.java)

            Log.d("conversion done", tutorListFromDatabase.toString())


        //addfilteredTutorToRV()
    }
//    fun sendPhoneNotification() {
//        recyclerView.findViewHolderForLayoutPosition(0)
//        //val mail = Uri.parse("mailto:xyz@gmail.com")
//        val intent = Intent(this,SentRequestToTutorActivity::class.java).apply {
//            type = "image/*"
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.phone_12)
//        // The line below is equivalent to the line above.  You may build up the Builder either way
//        //       builder.setSmallIcon(R.drawable.android);
//        builder.setContentIntent(pendingIntent)
//        builder.setAutoCancel(true)
//        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.phone_icon))
//        builder.setContentTitle("Notification for Friend Request Sent")
//        builder.setContentText("Press to View List of Sent Request")
//        val notificationManager = getSystemService(
//            NOTIFICATION_SERVICE
//        ) as NotificationManager
//        notificationManager.notify(NOTIFICATION_ID, builder.build())
//    }
//    fun onSendRequestTapped(view: View) {
//       sendRequestToTutorButton.text="Request Sent"
//        id+=1
//        myidOb = jwang.example.tutorfinder.tutor.Student(id,EditProfile.myIdentity.name,EditProfile.myIdentity.age,EditProfile.myIdentity.email)
//        Log.d("mystudent",myidOb.toString())
//         //StudentRequestActivity.requestedStudents.add(myidOb)
//       // Log.d("request",StudentRequestActivity.requestedStudents.toString())
//
//        sendPhoneNotification()
//    }
}