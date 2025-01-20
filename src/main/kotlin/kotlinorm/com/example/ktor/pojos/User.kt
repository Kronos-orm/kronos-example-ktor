package kotlinorm.com.example.ktor.pojos

import com.kotlinorm.annotations.Default
import com.kotlinorm.annotations.PrimaryKey
import com.kotlinorm.annotations.Table
import com.kotlinorm.interfaces.KPojo

@Table("tb_user")
data class User(
    @PrimaryKey(identity = true)
    var id: Int? = null,
    var name: String? = null, // name for user
    var age: Int? = null,
    @Default("1")
    var version: Int? = null
) : KPojo