package net.minusmc.discordrpcplugin

import net.minecraft.client.gui.GuiScreen

class GuiKeybindHelper(private val prevGui: GuiScreen) : GuiScreen() {
	override fun initGui() {
		buttonList.add(GuiButton(1, width / 2 + 40, height / 3, "ON"))
	}

	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		Fonts.font40.drawString("Enable", width / 2 - 100, (height / 3 - mc.fontRendererObj.FONT_HEIGHT * 2).toFloat(), 0xffffff, false)
	}
}