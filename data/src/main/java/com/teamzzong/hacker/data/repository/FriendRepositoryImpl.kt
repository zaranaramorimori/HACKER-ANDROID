package com.teamzzong.hacker.data.repository

import com.teamzzong.hacker.data.model.request.toRequestChangeFriendStatus
import com.teamzzong.hacker.data.model.response.EmptyResponse
import com.teamzzong.hacker.data.remote.FriendService
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.domain.entity.friend.Friend
import com.teamzzong.hacker.domain.entity.friend.FriendStatus
import com.teamzzong.hacker.domain.entity.user.GithubUser
import com.teamzzong.hacker.domain.entity.user.UserDetail
import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendService: FriendService,
    private val userService: UserService,
    private val json: Json
) : FriendRepository {
    override suspend fun getFriends(): Result<List<Friend>> {
        return runCatching { friendService.getFriends().data.map { it.toEntity() } }
    }

    override suspend fun changeFriendStatus(friendId: Int): Result<FriendStatus> {
        return runCatching {
            val result = friendService.changeFriendStatus(
                friendId.toRequestChangeFriendStatus()
            ).data
            if (result.isFriend) FriendStatus.ADD else FriendStatus.CANCEL
        }
    }

    override suspend fun getUserDetail(userId: Int): Result<UserDetail> {
        return runCatching { userService.getUserDetail(userId).data.toEntity() }
    }

    override suspend fun attackUserOf(userId: Int): Result<Unit> {
        return runCatching { friendService.attackUser(userId) }
    }

    override suspend fun searchUsersAsFriend(userName: String): Result<List<GithubUser>> {
        val response = friendService.getUsersAsFriend(userName)
        return if (response.isSuccessful) {
            Result.success(requireNotNull(response.body()).data.map { it.toEntity() })
        } else {
            val errorBody =
                json.decodeFromString<EmptyResponse>(response.errorBody()?.string() ?: "")
            Result.failure(Throwable(errorBody.message))
        }
    }
}
