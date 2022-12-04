package jwang.example.tutorfinder.tutor

import java.io.Serializable

data class Student (
    var id: String = "",
    var name: String = "",
    var age: Int = 0,
    var email: String = "",
    var phone: Int = 0,
    var address: String = "",
    var grade: Int = 0,
    var role: String = ""
    ) : Serializable{
    constructor(name:String, age:Int, email: String, phone:Int, address:String, grade: Int, role: String) : this(id = "1111", name,
    age, email, phone, address, grade, role)
}