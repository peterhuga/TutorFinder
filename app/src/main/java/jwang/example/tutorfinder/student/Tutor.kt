package jwang.example.tutorfinder.student

import java.io.Serializable

data class Tutor (
    var id:String = "",
    var name:String = "",
    val age:Int = 0,
    val address:String = "",
    val phone:String = "",
    var grades:String = "",
    var experience:String = "",
    val email: String = "",
    var education: String = "",
    var role: String = ""
    ) : Serializable {
    constructor(name: String, age: Int, address: String, phone: String, grades: String, experience: String, email: String, education: String, role: String) : this(id = "2222",
    name, age, address, phone, grades, experience, email, education, role)

}