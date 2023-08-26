package me.toidicakhia.examplemod

import net.ccbluex.liquidbounce.plugin.Plugin
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.ClientShutdownEvent
import net.minusmc.discordrpcplugin.ClientRichPresence
import kotlin.concurrent.thread

class DiscordRPCPlugin: Plugin("DiscordRPCPlugin", "dev") {

	lateinit var clientRichPresence: ClientRichPresence

	override fun init() {
		clientRichPresence = ClientRichPresence()
		if (clientRichPresence.showRichPresenceValue) {
            thread {
                try {
                    clientRichPresence.setup()
                } catch (throwable: Throwable) {
                    ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable)
                }
            }
        }
	}

	@EventTarget
	fun onShutDown(event: ClientShutdownEvent) {
		clientRichPresence.shutdown()
	}

	// } else if (extendedModMode) {
                    //         val rpc = LiquidBounce.clientRichPresence
                    //         rpc.showRichPresenceValue = when (val state = !rpc.showRichPresenceValue) {
                    //             false -> {
                    //                 rpc.shutdown()
                    //                 false
                    //             }
                    //             true -> {
                    //                 var value = true
                    //                 thread {
                    //                     value = try {
                    //                         rpc.setup()
                    //                         true
                    //                     } catch (throwable: Throwable) {
                    //                         ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable)
                    //                         false
                    //                     }
                    //                 }
                    //                 value
                    //             }
                    //         }
                    //     } 
}