package io.realworld.app.domain

data class UserStats(
    val articlesCount: Long,
    val commentsCount: Long,
    val favoritesCount: Long
)

data class UserStatsDTO(val stats: UserStats)
