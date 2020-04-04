package fr.lewon.bot.runner.session

import org.springframework.web.reactive.function.client.WebClient

class SessionHolder(val sessionObject: Any, val webClient: WebClient)