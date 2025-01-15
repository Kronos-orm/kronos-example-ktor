package kotlinorm.com.example.ktor.pojos

import com.kotlinorm.annotations.PrimaryKey
import com.kotlinorm.annotations.Table
import com.kotlinorm.interfaces.KPojo

@Table("tb_user")
data class User(
    @PrimaryKey(identity = true)
    var id: Int? = null,
    var name: String? = null, // name for user
    var age: Int? = null,
    var version: Int = 1
) : KPojo