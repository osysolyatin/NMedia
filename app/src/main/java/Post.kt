import android.provider.Settings.Global.getString
import ru.netology.nmedia.R

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var shares: Int = 0
) {

}



