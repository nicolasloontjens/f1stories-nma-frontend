package be.howest.nicolas.loontjens.f1stories.network.data

data class Comment(
    val commentid: Int,
    val userid: Int,
    val storyid: Int,
    val content: String,
    val username: String
)
