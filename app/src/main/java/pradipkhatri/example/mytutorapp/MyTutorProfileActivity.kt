package pradipkhatri.example.mytutorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

class MyTutorProfileActivity : AppCompatActivity() {
    lateinit var sendRequest:Button
    lateinit var cancelRequest:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tutor_profile)
        sendRequest=findViewById(R.id.sendReqBtn)
        //sendRequest.isActivated = true
        cancelRequest=findViewById(R.id.cancelRequestBtn)
        cancelRequest.isVisible=false
    }

    fun onGoBackPressed(view: View) {
        finish()
    }
    fun onSendRequestPressed(view: View) {

if (sendRequest.isPressed==true){
    sendRequest.isVisible=false
    cancelRequest.isVisible=true
    Snackbar.make(
        findViewById(R.id.myConstraintLayout),
        R.string.msgSendReq,
        Snackbar.LENGTH_SHORT
    ).show()
}
    }

    fun onCancelPressed(view: View) {
        if(cancelRequest.isPressed==true){
            cancelRequest.isVisible=false
            sendRequest.isVisible=true
            Snackbar.make(
                findViewById(R.id.myConstraintLayout),
                R.string.msgCancelReq,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}