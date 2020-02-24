package fr.lewon.bot.runner.session

import org.springframework.web.reactive.function.client.WebClient

/**
 * Manages the session between the host and the client.
 */
abstract class AbstractSessionManager(private val login: String, private val password: String, private val sessionDurability: Long, private val webClientBuilder: WebClient.Builder) {

    private lateinit var webClient: WebClient
    private lateinit var sessionObject: Any
    private var lastGenerationTime: Long = -sessionDurability
    private var forceRefresh: Boolean = false

    /**
     * Returns the user session. If no session has been generated, or if the last generated session is older than the session
     * durability, generates a new session by calling [.generateSessionObject]
     *
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getSession(): Any {
        initAll()
        return this.sessionObject
    }

    @Throws(Exception::class)
    fun getWebClient(): WebClient {
        initAll()
        return webClient
    }

    @Throws(Exception::class)
    private fun initAll() {
        val currentTimeMillis = System.currentTimeMillis()
        if (this.forceRefresh || this.lastGenerationTime + this.sessionDurability <= currentTimeMillis) {
            webClient = webClientBuilder.build()
            this.sessionObject = this.generateSessionObject(webClient, this.login, this.password)
            this.lastGenerationTime = currentTimeMillis
            this.forceRefresh = false
        }
    }

    /**
     * Forces the refresh of the session on the next use of [.getSession], ignoring its age
     */
    fun forceRefresh() {
        this.forceRefresh = true
    }

    /**
     * Generates a session object aiming to store the needed datas for a stateful connexion, such as cookies.
     *
     * @param webClient
     * @param login
     * @param password
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun generateSessionObject(webClient: WebClient, login: String, password: String): Any

}
