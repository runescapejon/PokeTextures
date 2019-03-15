package com.runescapejon.poketexture.main;

import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import ninja.leaping.configurate.objectmapping.Setting;

@ConfigSerializable
public class Config {
	@Setting(value = "PokeSpawnMessage", comment = "Edit your PokeSpawn command display message. Also placeholder for pokemon name is %pokemon%")
	public static String PokeSpawnMessage = "&c[ServerName]&b You have spawn a %pokemon%!";

	@Setting(value = "PokeGiveMessage", comment = "Edit your PokeGive command display message for giving textured pokemon. Also placeholder for pokemon name is %pokemon% Placeholder for player name is %player%")
	public static String PokeGiveMessage = "&c[ServerName]&b You've given %player% a %pokemon%!";

	@Setting(value = "PokeGiveTargetMessage", comment = "Edit your PokeGive command display message for players that been given the textured pokemon. Also placeholder for pokemon name is %pokemon% Placeholder for player name is %player%")
	public static String PokeGiveTargetMessage = "&c[ServerName]&b You've received a %pokemon% enjoy!";

	@Setting(value = "AllowTexturePokemonToSpawn", comment = "Allow custom texture pokemon to spawn.")
	public static boolean AllowTexturePokemonToSpawn = true;

	@Setting(value = "CustomTexture", comment = "Custom Texture this option give the ability to choose what custom texture folder it will use")
	public static String CustomTexture = "test";

	@SuppressWarnings("serial")
	@Setting(value = "PokemonList", comment = "List of pokemon to allow them to spawn with a custom Texture")
	public static List<String> PokemonList = new ArrayList<String>() {
		{
			add(EnumSpecies.Wingull.name);
			add(EnumSpecies.Nidoranfemale.name);
		}
	};
	@Setting(value = "Chance", comment = "Attempt chanaces between 1-100 it will attempt to spawn in custom texture pokemon.")
	public static int Chance = 2;

	@Setting(value = "SetColorName", comment = "Set a color name to a custom texture pokemon to help better in indicate a texture pokemon")
	public static String SetColorName = "&b";
}
