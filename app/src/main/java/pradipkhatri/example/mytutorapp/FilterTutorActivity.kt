package pradipkhatri.example.mytutorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast


class FilterTutorActivity : AppCompatActivity() {
    lateinit var genderQuery:Spinner
    lateinit var courseQuery:EditText
    lateinit var gradeQuery:EditText
    lateinit var locationQueryTextView:TextView
    lateinit var locationSeekBar:SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_tutor)
        genderQuery = findViewById(R.id.GenderQuerySpinner)
        courseQuery=findViewById(R.id.courseQueryEditText)
        gradeQuery=findViewById(R.id.gradeQueryEditText)
        locationQueryTextView=findViewById(R.id.locationSliderTextView)
        locationSeekBar=findViewById(R.id.LocationSeekBar)
        locationQueryTextView.text="0KM"

        locationSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                locationQueryTextView.text=seek.progress.toString()+"KM"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
                locationQueryTextView.text=seek.progress.toString()+"KM"

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })
    }

    fun onDonePressed(view: View) {

        finish()
    }
}