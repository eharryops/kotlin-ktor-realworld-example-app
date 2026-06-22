package io.realworld.app.web.controllers

import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import io.realworld.app.domain.UserStatsDTO
import io.realworld.app.domain.service.UserService

class ProfileController(private val userService: UserService) {
    fun get(ctx: ApplicationCall) {
        ctx.parameters["username"]
//            userService.getProfileByUsername(ctx.attribute("email")!!, usernameFollowing).also { profile ->
//                ctx.json(ProfileDTO(profile))
    }

    fun follow(ctx: ApplicationCall) {
        ctx.parameters["username"]
//            userService.follow(ctx.attribute("email")!!, usernameToFollow).also { profile ->
//                ctx.json(ProfileDTO(profile))
    }

    fun unfollow(ctx: ApplicationCall) {
        ctx.parameters["username"]
//            userService.unfollow(ctx.attribute("email")!!, usernameToUnfollow).also { profile ->
//                ctx.json(ProfileDTO(profile))
    }

    suspend fun getStats(ctx: ApplicationCall) {
        val username = ctx.parameters["username"] ?: throw IllegalArgumentException("Username parameter is required.")
        require(username.isNotBlank()) { "Username parameter is required." }
        val stats = userService.getStatsByUsername(username)
        ctx.respond(UserStatsDTO(stats))
    }
}

