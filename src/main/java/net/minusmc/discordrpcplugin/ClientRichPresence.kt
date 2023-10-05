package net.minusmc.discordrpcplugin

import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import com.jagrosh.discordipc.entities.pipe.PipeStatus
import net.minusmc.minusbounce.MinusBounce
import net.minusmc.minusbounce.utils.ClientUtils
import net.minusmc.minusbounce.utils.MinecraftInstance
import org.json.JSONObject
import java.io.IOException
import java.time.OffsetDateTime
import kotlin.concurrent.thread

import org.lwjgl.opengl.Display

class ClientRichPresence : MinecraftInstance() {
    var showRichPresenceValue = true

    private var ipcClient: IPCClient? = null

    private var appID = 0L
    private val assets = mutableMapOf<String, String>()
    private val timestamp = OffsetDateTime.now()

    private var running: Boolean = false

    fun setup() {
        try {
            running = true

            loadConfiguration()

            ipcClient = IPCClient(appID)
            ipcClient?.setListener(object : IPCListener {
                override fun onReady(client: IPCClient?) {
                    thread {
                        while (running) {
                            update()
                            try {
                                Thread.sleep(1000L)
                            } catch (ignored: InterruptedException) {}
                        }
                    }
                }

                override fun onClose(client: IPCClient?, json: JSONObject?) {
                    running = false
                }

            })
            ipcClient?.connect()
        } catch (e: Throwable) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", e)
        }

    }

    fun update() {
        val builder = RichPresence.Builder()

        builder.setStartTimestamp(timestamp)

        if (assets.containsKey("logo"))
            builder.setLargeImage("https://i.imgur.com/Wputa6T.png", "MinusBounce " + MinusBounce.CLIENT_VERSION)

        val serverData = mc.currentServerData

        builder.setState("MinusBounce")
        builder.setState("Playing Minecraft")

        if (mc.isIntegratedServerRunning || serverData != null) 
            builder.setSmallImage(assets["sus"], "${if (mc.isIntegratedServerRunning || serverData == null) "Lonely.." else serverData.serverIP}")
        else
            builder.setSmallImage(assets["sus"], "Enabled ${MinusBounce.moduleManager.modules.count { it.state }}/${MinusBounce.moduleManager.modules.size}.")

        if (ipcClient?.status == PipeStatus.CONNECTED)
            ipcClient?.sendRichPresence(builder.build())
    }

    fun shutdown() {
        if (ipcClient?.status != PipeStatus.CONNECTED) return
        
        try {
            ipcClient?.close()
        } catch (e: Throwable) {
            ClientUtils.getLogger().error("Failed to close Discord RPC.", e)
        }
    }

    private fun loadConfiguration() {
        appID = 1131467194056855703
        assets["logo"] = "logo"
        assets["sus"] = "sus"
    }
}
