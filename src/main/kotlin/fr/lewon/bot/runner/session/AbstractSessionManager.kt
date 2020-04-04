package fr.lewon.bot.runner.session

import fr.lewon.bot.runner.bot.props.BotPropertyStore
import org.springframework.web.reactive.function.client.WebClient

/**
 * Manages the session between the host and the client.
 */
abstract class AbstractSessionManager(private val login: String, private val loginPropertyStore: BotPropertyStore, private val sessionDurability: Long, private val webClientBuilder: WebClient.Builder) {

    private lateinit var webClient: WebClient
    private lateinit var sessionObject: Any
    private var lastGenerationTime: Long = -sessionDurability
    private var forceRefresh: Boolean = true

    /**
     * Returns the user session. If no session has been generated, or if the last generated session is older than the session
     * durability, generates a new session by calling [.generateSessionObject]
     *
     * @return
     * @throws Exception
     */
    fun buildSessionHolder(): SessionHolder {
        initAll()
        return SessionHolder(sessionObject, webClient)
    }

    @Synchronized
    @Throws(Exception::class)
    private fun initAll() {
        val currentTimeMillis = System.currentTimeMillis()
        if (this.forceRefresh || this.lastGenerationTime + this.sessionDurability <= currentTimeMillis) {
            webClient = webClientBuilder.build()
            this.sessionObject = this.generateSessionObject(webClient, this.login, this.loginPropertyStore)
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
     * @param loginPropertyStore
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun generateSessionObject(webClient: WebClient, login: String, loginPropertyStore: BotPropertyStore): Any

}
