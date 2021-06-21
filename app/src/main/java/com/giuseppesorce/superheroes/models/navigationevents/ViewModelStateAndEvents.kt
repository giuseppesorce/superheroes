package com.giuseppesorce.superheroes.models.navigationevents

sealed class MainState {
}

sealed class MainEvents {
    object  GotoHome: MainEvents()
}

sealed class HomeState {
    object  ShowLikeAnimation: HomeState()
    object  ShowDisLikeAnimation: HomeState()
    data class ShowError(var error:Int):HomeState()
}

sealed class HomeEvents {
    data class  GotoLikedOrDislikeHeroes(var isLike:Boolean): HomeEvents()

}


sealed class LikeState {
}

sealed class LikeEvents {
    object  GoBack: LikeEvents()
}
