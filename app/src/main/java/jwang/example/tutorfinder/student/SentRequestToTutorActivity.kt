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
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jwang.example.tutorfinder.R

class SentRequestToTutorActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewManager: RecyclerView.LayoutManager
    var filteredTutors: MutableList<Tutor> = mutableListOf()
    val CHANNEL_ID = "201"
    val NOTIFICATION_ID = 222


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent_request_to_tutor)
        recyclerView = findViewById(R.id.sentRequestTutorRV)
        recyclerViewManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = recyclerViewManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SentRequestToTutorRvAdapter(this,StudentDashboard.tutors)
        createNotificationChannel()
//
//        for (person in StudentDashboard.tutors){
//    filteredTutors.add(person)
//}

    }

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
        builder.setContentTitle("Notification for Friend Request Cancelled")
        builder.setContentText("Press to View List of Sent Request")
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun cancelRequestButtonClicked(view: View) {
        if(findViewById<Button>(R.id.cancelRequestButton).isPressed){
            StudentDashboard.tutors.removeAt(0)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        sendPhoneNotification()
    }

}
