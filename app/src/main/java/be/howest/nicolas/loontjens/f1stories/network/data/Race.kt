package be.howest.nicolas.loontjens.f1stories.network.data

import java.util.*


data class Race (
    val raceid: Int,
    val title: String,
    val date: String
){
    fun getId(): Int = raceid
    override fun toString(): String = title
}