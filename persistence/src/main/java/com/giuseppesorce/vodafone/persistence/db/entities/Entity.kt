package com.giuseppesorce.vodafone.persistence.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Giuseppe Sorce
 */

@Entity(tableName = "hero")
data class RHero(
    @PrimaryKey
    var id: Int,
    var name:String,
    var description:String,
    var islike:Boolean= false,
    var image : String?=null,
    var  totalComics : String,
    var  totalEvents  : String,
    var totalSeries  : String,
    var totalStories : String
)
