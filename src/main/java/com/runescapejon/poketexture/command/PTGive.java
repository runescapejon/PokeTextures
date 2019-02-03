package com.runescapejon.poketexture.command;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.Entity1Base;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.runescapejon.poketexture.main.PokeTexture;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class PTGive implements CommandExecutor {
	PokeTexture plugin;

	public PTGive() {
		this.plugin = PokeTexture.instance;
	}

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		EnumSpecies p = (EnumSpecies) args.getOne("pokemon").get();
		Optional<Player> target = args.<Player>getOne("PlayerName");
		String value = (String) args.getOne("Value").get();
		WorldServer world;
		if (src instanceof EntityPlayerMP) {
			world = ((EntityPlayerMP) src).getServerWorld();
		} else {
			world = (WorldServer) Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorld().get().getUniqueId())
					.get();
		}
		NBTTagCompound nbt = new NBTTagCompound();
		EntityPixelmon  pokemonEntity = (EntityPixelmon) PixelmonEntityList.createEntityByName(p.name, world);
		pokemonEntity.writeToNBT(nbt).setString("CustomTexture", value);
		pokemonEntity.readFromNBT(nbt);
		pokemonEntity.canDespawn = false;
		if (target.isPresent()) {
			Player player = target.get();
		//	//Optional<PlayerStorage> optstorage = PixelmonStorage.pokeBallManager
		//			.getPlayerStorage((EntityPlayerMP) player);
		//	PlayerPartyStorage storage = (PlayerPartyStorage) optstorage.get();
			PlayerPartyStorage storage = Pixelmon.storageManager.getParty((EntityPlayerMP) player);
			Pokemon pokemon = ((Entity1Base) pokemonEntity).getPokemonData();
			storage.add(pokemon);
			src.sendMessage(Text.of(TextColors.WHITE, "[Server Name]", TextColors.GOLD, " - You've given ",
					player.getName(), " a ", TextColors.AQUA, p.name, TextColors.WHITE, "."));

			// target player Message and sound effects
			player.sendMessage(
					Text.of(TextColors.LIGHT_PURPLE, "You've received a Pokemon enjoy! ", TextColors.AQUA, p.name));
			player.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, player.getLocation().getPosition(), 1);
		}

		return CommandResult.success();
	}
}