package be.howest.nicolas.loontjens.f1stories.network.data

data class Profile(
    val id: Int,
    val username: String,
    val userscore: Int,
    val stories: List<ProfileStory>,
    val racesvisited: Int
)
