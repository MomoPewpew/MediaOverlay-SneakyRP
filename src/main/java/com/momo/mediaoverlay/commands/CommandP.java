package com.momo.mediaoverlay.commands;

import com.momo.mediaoverlay.Server;
import com.momo.mediaoverlay.constants.EnumPackets;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class CommandP extends CommandBase {

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0)
			return;

		String url = args[0];
		double radius;

		radius = (args.length > 1 && args[1].matches("^\\d+(\\.\\d+)*$")) ? Double.valueOf(args[1]) : 8.0D;

		NBTTagCompound compound = new NBTTagCompound();

		compound.setString("url", url);

		Server.sendAssociatedData((EntityPlayer) sender, EnumPackets.PICTURE_PLAY, radius, compound);
	}

	@Override
	public String getName() {
		return "p";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "/p [url]";
	}

}
