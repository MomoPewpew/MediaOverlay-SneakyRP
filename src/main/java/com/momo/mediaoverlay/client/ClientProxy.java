package com.momo.mediaoverlay.client;

import com.momo.mediaoverlay.CommonProxy;
import com.momo.mediaoverlay.MediaOverlay;
import com.momo.mediaoverlay.PacketHandlerClient;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    	MediaOverlay.Channel.register(new PacketHandlerClient());
    }

    @Override
    public void init(FMLInitializationEvent event) {
    	//Minecraft.getMinecraft().displayGuiScreen(new BrowserScreen());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}