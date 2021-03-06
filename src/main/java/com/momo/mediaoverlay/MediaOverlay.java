package com.momo.mediaoverlay;

import net.minecraftforge.common.MinecraftForge;
import net.montoyo.mcef.utilities.Log;
import org.lwjgl.input.Keyboard;

import com.momo.mediaoverlay.client.gui.BrowserScreen;
import com.momo.mediaoverlay.client.gui.ScreenCfg;
import com.momo.mediaoverlay.commands.CommandP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import net.montoyo.mcef.api.API;
import net.montoyo.mcef.api.IBrowser;
import net.montoyo.mcef.api.IDisplayHandler;
import net.montoyo.mcef.api.IJSQueryCallback;
import net.montoyo.mcef.api.IJSQueryHandler;
import net.montoyo.mcef.api.MCEFApi;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * An example mod that shows you how to use MCEF.
 * Assuming that it is client-side only and that onInit() is called on initialization.
 * This example shows a simple 2D web browser when pressing F6.
 *
 * @author montoyo
 *
 */

@Mod(modid = MediaOverlay.MODID, name = MediaOverlay.NAME, version = MediaOverlay.VERSION)
public class MediaOverlay implements IDisplayHandler, IJSQueryHandler {

    public static final String MODID = "mediaoverlay";
    public static final String NAME = "MediaOverlay";
    public static final String VERSION = "1.12.2";

    public static MediaOverlay INSTANCE;

    public ScreenCfg hudBrowser = null;
    private KeyBinding key = new KeyBinding("Open Browser", Keyboard.KEY_F10, "key.categories.misc");
    private Minecraft mc = Minecraft.getMinecraft();
    private BrowserScreen backup = null;
    private API api;
    public static FMLEventChannel Channel;

    public API getAPI() {
        return api;
    }

    @SidedProxy(
            clientSide = "com.momo.mediaoverlay.client.ClientProxy",
            serverSide = "com.momo.mediaoverlay.CommonProxy"
       )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LogWriter.info("Loading");
    	Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("MediaOverlay");

        //Grab the API and make sure it isn't null.
        api = MCEFApi.getAPI();
        if(api == null)
            return;

        api.registerScheme("mod", ModScheme.class, true, false, false, true, true, false, false);

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        INSTANCE = this;

        //Register key binding and listen to the FML event bus for ticks.
        ClientRegistry.registerKeyBinding(key);
        MinecraftForge.EVENT_BUS.register(this);

    	proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(api != null) {
            //Register this class to handle onAddressChange and onQuery events
            api.registerDisplayHandler(this);
            api.registerJSQueryHandler(this);
        }
        proxy.postInit(event);
    }

    @EventHandler
    public void serverstart(FMLServerStartingEvent event) {
         event.registerServerCommand(new CommandP());
    }

    public void setBackup(BrowserScreen bu) {
        backup = bu;
    }

    public boolean hasBackup() {
        return (backup != null);
    }

    public void showScreen(String url) {
        if(mc.currentScreen instanceof BrowserScreen)
            ((BrowserScreen) mc.currentScreen).loadURL(url);
        else if(hasBackup()) {
            mc.displayGuiScreen(backup);
            backup.loadURL(url);
            backup = null;
        } else
            mc.displayGuiScreen(new BrowserScreen(url));
    }

    public IBrowser getBrowser() {
        if(mc.currentScreen instanceof BrowserScreen)
            return ((BrowserScreen) mc.currentScreen).browser;
        else if(backup != null)
            return backup.browser;
        else
            return null;
    }

    @SubscribeEvent
    public void onTick(TickEvent ev) {
        if(ev.phase == TickEvent.Phase.START && ev.side == Side.CLIENT && ev.type == TickEvent.Type.CLIENT) {
            //Check if our key was pressed
            if(key.isPressed() && !(mc.currentScreen instanceof BrowserScreen)) {
                //Display the web browser UI.
                mc.displayGuiScreen(hasBackup() ? backup : new BrowserScreen());
                backup = null;
            }
        }
    }

    @Override
    public void onAddressChange(IBrowser browser, String url) {
        //Called by MCEF if a browser's URL changes. Forward this event to the screen.
        if(mc.currentScreen instanceof BrowserScreen)
            ((BrowserScreen) mc.currentScreen).onUrlChanged(browser, url);
        else if(hasBackup())
            backup.onUrlChanged(browser, url);
    }

    @Override
    public void onTitleChange(IBrowser browser, String title) {
    }

    @Override
    public void onTooltip(IBrowser browser, String text) {
    }

    @Override
    public void onStatusMessage(IBrowser browser, String value) {
    }

    @Override
    public boolean handleQuery(IBrowser b, long queryId, String query, boolean persistent, IJSQueryCallback cb) {
        if(b != null && query.equalsIgnoreCase("username")) {
            if(b.getURL().startsWith("mod://")) {
                //Only allow MCEF URLs to get the player's username to keep his identity secret

                mc.addScheduledTask(() -> {
                    //Add this to a scheduled task because this is NOT called from the main Minecraft thread...

                    try {
                        String name = mc.getSession().getUsername();
                        cb.success(name);
                    } catch(Throwable t) {
                        cb.failure(500, "Internal error.");
                        Log.warning("Could not get username from JavaScript:");
                        t.printStackTrace();
                    }
                });
            } else
                cb.failure(403, "Can't access username from external page");

            return true;
        }

        return false;
    }

    @Override
    public void cancelQuery(IBrowser b, long queryId) {
    }

    @SubscribeEvent
    public void onDrawHUD(RenderGameOverlayEvent.Post ev) {
        if(hudBrowser != null)
            hudBrowser.drawScreen(0, 0, 0.f);
    }

	public BrowserScreen getBackup() {
		return backup;
	}

}