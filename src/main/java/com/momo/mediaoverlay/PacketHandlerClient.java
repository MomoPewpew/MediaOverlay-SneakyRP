package com.momo.mediaoverlay;

import io.netty.buffer.ByteBuf;

import com.momo.mediaoverlay.constants.EnumPackets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

public class PacketHandlerClient {
     @SubscribeEvent
     public void onPacketData(ClientCustomPacketEvent event) {
          EntityPlayer player = Minecraft.getMinecraft().player;
          ByteBuf buf = event.getPacket().payload();
          Minecraft.getMinecraft().addScheduledTask(() -> {
               EnumPackets en = null;

               try {
                    en = EnumPackets.values()[buf.readInt()];
                    this.handlePacket(buf, player, en);
               } catch (Exception var5) {
                    LogWriter.error("Packet error: " + en, var5);
               }

          });
     }

     private void handlePacket(ByteBuf buffer, EntityPlayer player, EnumPackets type) throws Exception {
           EntityPlayer pl;
           if (type == EnumPackets.PICTURE_PLAY) {

           }
     }
}
