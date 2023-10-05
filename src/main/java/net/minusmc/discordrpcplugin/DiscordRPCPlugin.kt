package net.minusmc.discordrpcplugin

import net.minusmc.minusbounce.plugin.Plugin
import net.minusmc.minusbounce.event.EventTarget
import net.minusmc.minusbounce.event.ClientShutdownEvent
import net.minusmc.minusbounce.utils.ClientUtils
import kotlin.concurrent.thread

class DiscordRPCPlugin: Plugin("DiscordRPCPlugin", "20231005") {

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