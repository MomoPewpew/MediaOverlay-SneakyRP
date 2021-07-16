package com.momo.mediaoverlay;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = MediaOverlay.MODID, name = MediaOverlay.NAME, version = MediaOverlay.VERSION)
public class MediaOverlay
{
    public static final String MODID = "mediaoverlay";
    public static final String NAME = "MediaOverlay";
    public static final String VERSION = "1.12.2";

    public static CommonProxy proxy;
    @SidedProxy(
            clientSide = "com.momo.mediaoverlay.client.ClientProxy",
            serverSide = "com.momo.mediaoverlay.CommonProxy"
       )

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();;
    }
}
