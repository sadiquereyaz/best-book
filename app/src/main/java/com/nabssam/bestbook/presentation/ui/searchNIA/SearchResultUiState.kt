package com.nabssam.bestbook.presentation.ui.searchNIA
/*

import com.nabssam.bestbook.domain.model.FollowableTopic
import com.nabssam.bestbook.domain.model.Topic
import com.nabssam.bestbook.domain.model.UserNewsResource

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState

    */
/**
     * The state query is empty or too short. To distinguish the state between the
     * (initial state or when the search query is cleared) vs the state where no search
     * result is returned, explicitly define the empty query state.
     *//*

    data object EmptyQuery : SearchResultUiState

    data object LoadFailed : SearchResultUiState

    data class Success(
        val topics: List<FollowableTopic> = emptyList(),
        val newsResources: List<UserNewsResource> = emptyList(),
    ) : SearchResultUiState {
        fun isEmpty(): Boolean = topics.isEmpty() && newsResources.isEmpty()
    }

    */
/**
     * A state where the search contents are not ready. This happens when the *Fts tables are not
     * populated yet.
     *//*

    data object SearchNotReady : SearchResultUiState
}

data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean,
)*/
