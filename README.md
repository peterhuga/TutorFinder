# TutorFinder

fun onButtonClick(view: View) {
        when(view.id) {
            R.id.buttonEditProfile -> startActivity(Intent(this, EditProfileActivity::class.java))
            R.id.buttonShowStudentRequest -> startActivity(Intent(this, StudentRequestActivity::class.java))
        }
    }
