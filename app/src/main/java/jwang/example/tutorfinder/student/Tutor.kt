package jwang.example.tutorfinder.student

import java.io.Serializable
data class Tutor (
    var id: String = "",
    var name:String,
    val age:String,
    val gender: String,
    val address:String,
    val phone:String,
    var grade:String,
    var experience:String,
    val email: String,
    var degree:String,

    var role: String = ""
) : Serializable {
    constructor(name:String, age:String, gender: String, address: String, phone: String, experience: String,email: String,degree: String, role: String) : this(id = "1111", name,
        age,gender,address,phone,experience, email,degree, address, role)
}