package com.giuseppesorce.vodafone.architecture

data class CommonState(
    val loadingState: LoadingState = LoadingState.Idle(),
    val errorState: ErrorState = ErrorState.NoError
)

data class SimpleAlertData(
    val title: String="",
    var message: String="",
    var titleRes : Int = -1,
    var messageRes : Int = -1,
    var codeMessage : Int = 0
)

sealed class LoadingState {
    class Idle : LoadingState()
    class Loading : LoadingState()
    class Empty : LoadingState()
}

sealed class ErrorState {
    object NoError : ErrorState()
    object UnknownError: ErrorState()
    data class Error(val message: String) : ErrorState()
}