package com.giuseppesorce.vodafone.network.data

import com.giuseppesorce.vodafone.network.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Giuseppe Sorce
 */
@Singleton
 class NetworkEnvironment  @Inject constructor() {
       var authorization:String=""
       var base_url:String=BuildConfig.BASEURL

}