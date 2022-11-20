package jwang.example.tutorfinder.student

import android.app.ActivityOptions
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R
import jwang.example.tutorfinder.tutor.EditTutorProfileActivity
import jwang.example.tutorfinder.tutor.StudentRequestActivity


class StudentDashboard : AppCompatActivity() {
    lateinit var adapter: MyTutorsRvAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var filterTutorNavigateBtn:Button
    val NOTIFICATION_ID = 111
    val CHANNEL_ID = "101"
    companion object {
        const val TUTOR_ID = "tutor id"
        var tutors: MutableList<Tutor> = mutableListOf(
            Tutor( "John", "35","Male","London","5555555555","12","5", "John@gmail.com","Masters"),
            Tutor( "Ted", "40","Male","Toronto","1111111111","Bachelor","10", "Ted@gmail.com","Post Graduate"),
            Tutor( "Leo", "36","Male","Ottawa","2222222222","10","6", "Leo@gmail.com","Masters"),
            Tutor( "Shyam", "35","Male","London","5555555555","11","7", "Shyam@gmail.com","Bachelor"),

            )


    }

//    var tutors: MutableList<Tutor> = mutableListOf(
//        Tutor(101, "John", "35","Male","London","5555555555","12","5", "John@gmail.com"),
//        Tutor(101, "Ted", "40","Male","Toronto","1111111111","Bachelor","10", "Ted@gmail.com"),
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
    filterTutorNavigateBtn=findViewById(R.id.filterTutorActivityBtn)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyTutor)
        adapter = MyTutorsRvAdapter(this, tutors)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        createNotificationChannel()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode == RESULT_OK){
//
//            if(requestCode==1){
//
//                if (data != null) {
//                    val deleteId = data.getIntExtra(StudentDashboard.TUTOR_ID,0)
//                    val position = data.getIntExtra("position", -1)
//                    Log.d("Student", "DltID, $deleteId")
//                    val mytutor = tutors.filter { student -> student.id != deleteId } as MutableList<Student>
//                    tutors.clear()
//                    tutors.addAll(mytutor)
//                    Log.d("Student", "size: ${tutors.size}")
//                    adapter.notifyItemRemoved(position)
//
//
//                }
//            }
//        }
//    }

     fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channnel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendPhoneNotification() {
        //val mail = Uri.parse("mailto:xyz@gmail.com")
        val intent = Intent(this,EditStudentProfile::class.java).apply {
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
        builder.setContentTitle("Express is waiting for your response")
        builder.setContentText("Press to mail Pradip!")
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_tutor_screen, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_settings -> {sendPhoneNotification()
                return true}
        }
        return super.onOptionsItemSelected(item)
    }
    fun onEditProfileBtnClicked(view: View) {
        startActivity(Intent(this,cameraTest::class.java))
    }
    fun onFilterBtnPressed(view: View) {

        startActivity(Intent(this, TutorFilterActivity::class.java))
    }

    fun sentRequestTutor(view: View) {
        startActivity(Intent(this, SentRequestToTutorActivity::class.java))

    }
}