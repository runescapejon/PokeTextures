package com.runescapejon.poketexture.main;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.runescapejon.poketexture.command.PTGive;
import com.runescapejon.poketexture.command.Spawn;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id = "poketextures", name = "pokeTexture", description = "This plugin for Editting Pokemon Texture with CustomTextures", version = "1.1", authors = "runescapejon")
public class PokeTexture {
	@Inject
	private Logger logger;

	public static PokeTexture instance;

	@Listener
	public void onStartUp(GameInitializationEvent e) {
		instance = this;

		loadCommands();

	}

	@SuppressWarnings("rawtypes")
	private void loadCommands() {
		@SuppressWarnings("unchecked")
		Map<String, EnumSpecies> m = new HashMap();
		for (EnumSpecies p : EnumSpecies.values()) {
			m.put(p.name, p);
		}
		CommandSpec spawn = CommandSpec.builder().description(Text.of("Spawn pokemon in at the location of the player"))
				.permission("poketexture.spawn")
				.arguments(GenericArguments.seq(GenericArguments.choices(Text.of("pokemon"), m)),
						GenericArguments.string(Text.of("Value")))
				.executor(new Spawn()).build();
		Sponge.getCommandManager().register(this, spawn, "poketspawn", "pts");

		CommandSpec PGive = CommandSpec.builder().description(Text.of("Give a specified player a Textured pokemon"))
				.permission("poketexture.ptgive")
				.arguments(
						GenericArguments.seq(GenericArguments.choices(Text.of("pokemon"), m),
								GenericArguments.player(Text.of("PlayerName"))),
						GenericArguments.string(Text.of("Value")))
				.executor(new PTGive()).build();

		Sponge.getCommandManager().register(this, PGive, "poketexturegive", "ptgive");

	}

	public Logger getLogger() {
		return this.logger;
	}

}
