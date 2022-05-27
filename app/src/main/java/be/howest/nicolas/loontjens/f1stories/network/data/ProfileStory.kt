package be.howest.nicolas.loontjens.f1stories.network.data

data class ProfileStory(
    val storyid: Int,
    val content: String,
    val country: String,
    val raceid: Int,
    val userid: Int,
    var score: Int,
    val date: String,
    val image1: String? = null,
    val image2: String? = null,
    val image3: String? = null
)
