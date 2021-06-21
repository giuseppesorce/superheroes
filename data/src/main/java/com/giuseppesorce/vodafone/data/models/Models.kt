package com.giuseppesorce.vodafone.data.models

import kotlinx.serialization.Serializable

/**
 * @author Giuseppe Sorce
 */
@Serializable
data class SCharactersResponse(var data: SDataResponse? = null)

@Serializable
data class SDataResponse(
    var offset: Int? = null,
    var limit: Int? = null,
    var total: Int? = null,
    var count: Int? = null,
    var results: List<SCharacter>? = null
)

@Serializable
data class SCharacter(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var modified: String? = null,
   var thumbnail: SThumbnail? = null,
    var comics: SItemDetail? = null,
    var series: SItemDetail? = null,
    var stories: SItemDetail? = null,
    var events: SItemDetail? = null
)


@Serializable
data class SThumbnail(var path:String?, var extension:String?)

@Serializable
data class SItemDetail(var available:Int?, var items:List<SChItem>?, var returned:Int)


//@Serializable
//data class SSeries(var available:Int?, var items:List<SChItem>?,  var returned:Int)
//
//@Serializable
//data class SStories(var available:Int?, var items:List<SChItem>?)
//
//@Serializable
//data class SEvents(var available:Int?, var items:List<SChItem>?)
//
@Serializable
data class SChItem(var name:String?, var resourceURI:String?, var type:String?=null)

@Serializable
data class ErrorResponse(var message: String? = null)