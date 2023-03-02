package com.teamzzong.hacker.domain.repository.friend

import com.teamzzong.hacker.domain.entity.friend.Friend
import com.teamzzong.hacker.domain.entity.friend.FriendStatus
import com.teamzzong.hacker.domain.entity.user.GithubUser
import com.teamzzong.hacker.domain.entity.user.UserDetail

interface FriendRepository {
    suspend fun getFriends(): Result<List<Friend>>
    suspend fun changeFriendStatus(friendId: Int): Result<FriendStatus>
    suspend fun getUserDetail(userId: Int): Result<UserDetail>
    suspend fun attackUserOf(userId: Int): Result<Unit>
    suspend fun searchUsersAsFriend(userName: String): Result<List<GithubUser>>
}
