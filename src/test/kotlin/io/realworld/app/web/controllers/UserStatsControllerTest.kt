package io.realworld.app.web.controllers

import io.realworld.app.domain.UserStatsDTO
import io.realworld.app.web.rules.AppRule
import org.apache.http.HttpStatus
import io.realworld.app.domain.repository.ArticleFavorites
import io.realworld.app.domain.repository.Articles
import io.realworld.app.domain.repository.Comments
import io.realworld.app.domain.repository.Users
import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Test
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

class UserStatsControllerTest {
    companion object {
        @ClassRule
        @JvmField
        val appRule = AppRule()
    }

    @Test
    fun `get user activity stats successfully`() {
        val suffix = System.nanoTime()
        val email = "stats_user_$suffix@gmail.com"
        val username = "stats_user_$suffix"
        val userId = transaction {
            Users.insertAndGetId {
                it[Users.email] = email
                it[Users.username] = username
                it[Users.password] = "password"
            }.value
        }

        transaction {
            val articleId1 = Articles.insertAndGetId {
                it[slug] = "article-1-$suffix"
                it[title] = "Article 1"
                it[description] = "Desc 1"
                it[body] = "Body 1"
                it[author] = userId
            }.value

            val articleId = Articles.insertAndGetId {
                it[slug] = "article-2-$suffix"
                it[title] = "Article 2"
                it[description] = "Desc 2"
                it[body] = "Body 2"
                it[author] = userId
            }.value

            Comments.insert {
                it[body] = "Comment 1"
                it[author] = userId
                it[article] = articleId1
            }

            ArticleFavorites.insert {
                it[article] = articleId
                it[user] = userId
            }
        }

        val response = appRule.http.get<UserStatsDTO>("/api/profiles/$username/stats")

        assertEquals(HttpStatus.SC_OK, response.status)
        assertEquals(2L, response.body.stats.articlesCount)
        assertEquals(1L, response.body.stats.commentsCount)
        assertEquals(1L, response.body.stats.favoritesCount)
    }

    @Test
    fun `get user activity stats returns 404 for non-existent user`() {
        val response = appRule.http.get<Any>("/api/profiles/nonexistentuser/stats")
        assertEquals(HttpStatus.SC_NOT_FOUND, response.status)
    }

    @Test
    fun `get user activity stats returns 422 for blank username`() {
        val response = appRule.http.get<Any>("/api/profiles/%20/stats")
        assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.status)
    }
}
