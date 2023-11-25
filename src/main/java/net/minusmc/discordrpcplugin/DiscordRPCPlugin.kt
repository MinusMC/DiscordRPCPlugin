package net.minusmc.discordrpcplugin

import net.minusmc.minusbounce.MinusBounce
import net.minusmc.minusbounce.plugin.Plugin
import net.minusmc.minusbounce.plugin.PluginAPIVersion
import net.minusmc.minusbounce.event.EventTarget
import net.minusmc.minusbounce.event.ClientShutdownEvent
import net.minusmc.minusbounce.utils.ClientUtils
import kotlin.concurrent.thread

object DiscordRPCPlugin: Plugin("DiscordRPCPlugin", version = "dev", minApiVersion = PluginAPIVersion.VER_01) {

	lateinit var clientRichPresence: ClientRichPresence

	override fun init() {
		MinusBounce.addMenuButton("DiscordRPC", GuiDiscordRPC::class.java)

		clientRichPresence = ClientRichPresence()
		if (clientRichPresence.showRichPresenceValue) {
            thread {
                try {
                    clientRichPresence.setup()
                } catch (throwable: Throwable) {
                    ClientUtils.logger.error("Failed to setup Discord RPC.", throwable)
                }
            }
        }
	}

	@EventTarget
	fun onShutDown(event: ClientShutdownEvent) {
		clientRichPresence.shutdown()
	}
}