package io.realworld.app.web.rules

import io.ktor.server.engine.ConnectorType
import io.realworld.app.config.SERVER_PORT
import io.realworld.app.config.setup
import io.realworld.app.web.util.HttpUtil
import org.junit.rules.ExternalResource
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.TimeUnit

class AppRule : ExternalResource() {
    private val app = setup()
    lateinit var http: HttpUtil
    val port = app.environment.connectors.find { it.type == ConnectorType.HTTP }?.port ?: SERVER_PORT

    override fun before() {
        app.start()
        waitUntilListening()
        http = HttpUtil(port)
    }

    override fun after() {
        app.stop(500, 500, TimeUnit.MILLISECONDS)
    }

    private fun waitUntilListening() {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5)
        while (System.nanoTime() < deadline) {
            try {
                Socket().use {
                    it.connect(InetSocketAddress("localhost", port), 100)
                }
                return
            } catch (ignored: Exception) {
                TimeUnit.MILLISECONDS.sleep(100)
            }
        }
    }
}
