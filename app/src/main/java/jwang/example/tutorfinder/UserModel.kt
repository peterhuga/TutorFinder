package jwang.example.tutorfinder

data class SignInStudent (
    var email: String,
    var role: String
   )

data class SignInTutor (
    var email: String,
    var role: String
    )

data class UnknownUser (
    var email: String = "",
    var role: String = ""
   )