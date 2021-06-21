package com.giuseppesorce.superheroes.models

/**
 * @author Giuseppe Sorce
 */

data class SuperHero(
    var id:Int,
    var image: String,
    var name: String = "",
    var description: String = "",
    var totalSeries: String="0",
    var totalComics: String="0",
    var totalStories: String="0",
    var totalEvents: String="0",
    var isLike:Boolean=false
)