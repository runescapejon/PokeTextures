package com.runescapejon.poketexture.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.runescapejon.poketexture.command.PTGive;
import com.runescapejon.poketexture.command.Spawn;

 
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;

@Plugin(id = "poketextures", name = "pokeTexture", description = "This plugin for Editting Pokemon Texture with CustomTextures", version = "1.5", authors = "runescapejon")
public class PokeTexture {

	private PokeTexture plugin;
	private Logger logger;
	private Config configmsg;
	GuiceObjectMapperFactory factory;
	private final File configDirectory;
	public static PokeTexture instance;

	@Inject
	public PokeTexture(Logger logger, @ConfigDir(sharedRoot = false) File configDir, GuiceObjectMapperFactory factory) {
		this.logger = logger;

		this.configDirectory = configDir;
		this.factory = factory;
		instance = this;
	}

	@Listener
	public void onStartUp(GameInitializationEvent e) {
		instance = this;
		loadConfig();
		loadCommands();
		Pixelmon.EVENT_BUS.register(new ListenerSpawn());
	}

	@Listener
	public void onPreInit(GamePreInitializationEvent event) {
		plugin = this;
		loadConfig();
	}

	@Listener
	public void onReload(GameReloadEvent event) {
		loadConfig();
	}

	public boolean loadConfig() {
		if (!plugin.getConfigDirectory().exists()) {
			plugin.getConfigDirectory().mkdirs();
		}
		try {
			File configFile = new File(getConfigDirectory(), "configuration.conf");
			if (!configFile.exists()) {
				configFile.createNewFile();
				logger.info("Creating Config for PokeTexture");
			}
			ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
					.setFile(configFile).build();
			CommentedConfigurationNode config = loader.load(ConfigurationOptions.defaults()
					.setObjectMapperFactory(plugin.getFactory()).setShouldCopyDefaults(true));
			configmsg = config.getValue(TypeToken.of(Config.class), new Config());
			loader.save(config);
			return true;
		} catch (Exception error) {
			getLogger().error("coudnt make the config", error);

			return false;
		}
	}

	public File getConfigDirectory() {
		return configDirectory;
	}

	public Config getLangCfg() {
		return configmsg;
	}

	public GuiceObjectMapperFactory getFactory() {
		return factory;
	}

	@SuppressWarnings("rawtypes")
	private void loadCommands() {
		@SuppressWarnings("unchecked")
		Map<String, EnumSpecies> m = new HashMap();
		for (EnumSpecies p : EnumSpecies.values()) {
			m.put(p.name.toUpperCase(), p);
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
						GenericArguments.seq(GenericArguments.enumValue(Text.of("Pokemon"), EnumSpecies.class),
								GenericArguments.player(Text.of("PlayerName"))),
						GenericArguments.string(Text.of("Value")))
				.executor(new PTGive()).build();

		Sponge.getCommandManager().register(this, PGive, "poketexturegive", "ptgive");

	}

	public Logger getLogger() {
		return this.logger;
	}

}
