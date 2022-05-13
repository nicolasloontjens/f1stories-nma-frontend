package be.howest.nicolas.loontjens.f1stories.network.data

data class Story(
    val storyid: Int,
    val content: String,
    val country: String,
    val raceid: Int,
    val userid: Int,
    val score: Int,
    val date: String,
    val username: String,
    val image1: String? = null,
    val image2: String? = null,
    val image3: String? = null
)
