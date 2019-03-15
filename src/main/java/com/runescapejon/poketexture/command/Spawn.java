package com.runescapejon.poketexture.command;

import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.runescapejon.poketexture.main.Config;
import com.runescapejon.poketexture.main.PokeTexture;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Spawn implements CommandExecutor {
	PokeTexture plugin;

	public Spawn() {
		this.plugin = PokeTexture.instance;
	}

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		EnumSpecies p = (EnumSpecies) args.getOne("pokemon").get();
		String value = (String) args.getOne("Value").get();
		WorldServer world;
		if ((src instanceof EntityPlayerMP)) {
			world = ((EntityPlayerMP) src).getServerWorld();
		} else {
			world = (WorldServer) Sponge.getServer().getDefaultWorld().orElse(null);
		}

		EntityPixelmon pokemon = (EntityPixelmon) PixelmonEntityList.createEntityByName(p.name, world);
		NBTTagCompound nbt = new NBTTagCompound();
		pokemon.writeToNBT(nbt).setString("CustomTexture", value);
		pokemon.readFromNBT(nbt);
		pokemon.canDespawn = false;

		if (src instanceof Player) {
			Player player = (Player) src;
			Location<World> loc = (Location<World>) player.getLocation();
			pokemon.setLocationAndAngles(loc.getX(), loc.getY(), loc.getZ(), 0.0f, 0.0f);
			world.spawnEntity(pokemon);
			src.sendMessage(TextSerializers.FORMATTING_CODE
					.deserialize(Config.PokeSpawnMessage.replace("%pokemon%", p.name())));
		}

		return CommandResult.success();
	}
}