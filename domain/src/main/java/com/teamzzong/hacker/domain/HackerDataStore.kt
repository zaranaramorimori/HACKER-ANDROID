package com.teamzzong.hacker.domain

interface HackerDataStore {
    var userToken: String
    var refreshToken: String
    var userUUID: String
    var social: String
    var firebaseToken: String
    var githubNickName: String
    var userId: Int
    var hackerNickName: String
    var versionCode: Int
    var isFirstAppVisit: Boolean
    fun clear()
}
