package com.momo.mediaoverlay.client;

import com.momo.mediaoverlay.CommonProxy;
import com.momo.mediaoverlay.client.gui.GuiMedia;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
    	Minecraft.getMinecraft().displayGuiScreen(new GuiMedia());
    }
}
