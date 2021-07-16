package com.momo.mediaoverlay;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;

import com.momo.mediaoverlay.commands.CommandP;

@Mod(modid = MediaOverlay.MODID, name = MediaOverlay.NAME, version = MediaOverlay.VERSION)
public class MediaOverlay
{
    public static final String MODID = "mediaoverlay";
    public static final String NAME = "MediaOverlay";
    public static final String VERSION = "1.12.2";

    @SidedProxy(
            clientSide = "com.momo.mediaoverlay.client.ClientProxy",
            serverSide = "com.momo.mediaoverlay.CommonProxy"
       )
    public static CommonProxy proxy;

    public static FMLEventChannel Channel;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LogWriter.info("Loading");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);;
    }

    @EventHandler
    public void serverstart(FMLServerStartingEvent event) {
         event.registerServerCommand(new CommandP());
    }
}
