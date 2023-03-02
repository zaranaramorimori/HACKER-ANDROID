package com.teamzzong.hacker.domain.usecase.friend

import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import javax.inject.Inject

class SearchFriendUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(userName: String) = repository.searchUsersAsFriend(userName)
}
