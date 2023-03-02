package com.teamzzong.hacker.presentation.gallery.model

sealed class GalleryUiState {
    object Add : GalleryUiState()
    data class User(
        val id: Int,
        val nickName: String,
        val head: String,
        val face: String
    ) : GalleryUiState()
}
