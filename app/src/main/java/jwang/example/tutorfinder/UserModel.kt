package jwang.example.tutorfinder

data class Student (
    var email: String,
    var role: String,
    var firstName: String = "",
    var lastName: String = "")

data class Tutor (
    var email: String,
    var role: String,
    var firstName: String = "",
    var lastName: String = "")

data class UnknownUser (
    var email: String = "",
    var role: String = "",
    var firstName: String = "",
    var lastName: String = "" )