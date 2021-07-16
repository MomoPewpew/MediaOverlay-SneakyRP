package com.momo.mediaoverlay.client.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiMedia extends GuiScreen {
    public int guiLeft;
    public int guiTop;
    public int xSize;
    public int ySize;
    protected ResourceLocation resourceLocation = null;

    public GuiMedia() {
        this.xSize = 200;
        this.ySize = 222;
   }

    @Override
    public void initGui() {
         super.initGui();

         this.guiLeft = (this.width - this.xSize) / 2;
         this.guiTop = (this.height - this.ySize) / 2;
         this.buttonList.clear();
         Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
    	//super().drawScreen(i, j, f);
    }

    @Override
    public void updateScreen() {
    	//super().updateScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
         return false;
    }
}
