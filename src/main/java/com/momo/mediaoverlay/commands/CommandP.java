package com.momo.mediaoverlay.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandP extends CommandBase {

	@Override
	public void execute(MinecraftServer arg0, ICommandSender arg1, String[] arg2) throws CommandException {
		// TODO Auto-generated method stub

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
