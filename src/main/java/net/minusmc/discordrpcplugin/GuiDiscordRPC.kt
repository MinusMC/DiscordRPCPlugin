package net.minusmc.discordrpcplugin

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minusmc.minusbounce.ui.client.BoolButton
import net.minusmc.minusbounce.ui.font.Fonts
import net.minusmc.minusbounce.utils.render.RenderUtils
import java.awt.Color

class GuiDiscordRPC(private val prevGui: GuiScreen) : GuiScreen() {
	override fun initGui() {
		buttonList.add(BoolButton(0, width / 2 + 100, height / 2 - 60, DiscordRPCPlugin.clientRichPresence.showRichPresenceValue))
	}

	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		drawBackground(0)
		RenderUtils.drawRoundedRect((width / 2 - 200).toFloat(), (height / 2 - 120).toFloat(), (width / 2 + 200).toFloat(), (height / 2 + 120).toFloat(), 4f, Color(249, 246, 238, 120).rgb)
		Fonts.font72.drawCenteredString("Discord RPC Settings", width.toFloat() / 2f, (height / 2 - mc.fontRendererObj.FONT_HEIGHT - 90).toFloat(), Color(54, 69, 79).rgb, false)
		Fonts.font50.drawString("Enable", (width / 2 - 140).toFloat(), (height / 2 - 56).toFloat(), Color(54, 69, 79).rgb, false)
		super.drawScreen(mouseX, mouseY, partialTicks)
	}

	override fun actionPerformed(button: GuiButton) {
		when (button.id) {
			0 -> {
				val button = button as BoolButton
				button.state = !button.state
				DiscordRPCPlugin.clientRichPresence.showRichPresenceValue = !DiscordRPCPlugin.clientRichPresence.showRichPresenceValue
			}
		}
	}
}