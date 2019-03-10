package com.runescapejon.poketexture.main;

import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import ninja.leaping.configurate.objectmapping.Setting;

@ConfigSerializable
public class Config {
	@Setting(value = "PokeSpawnMessage", comment = "Edit your PokeSpawn command display message. Also placeholder for pokemon name is %pokemon%")
	public static String PokeSpawnMessage = "&c[ServerName]&b You have spawn a %pokemon%!";

	@Setting(value = "PokeGiveMessage", comment = "Edit your PokeGive command display message for giving textured pokemon. Also placeholder for pokemon name is %pokemon% Placeholder for player name is %player%")
	public static String PokeGiveMessage = "&c[ServerName]&b You've given %player% a %pokemon%!";

	@Setting(value = "PokeGiveTargetMessage", comment = "Edit your PokeGive command display message for players that been given the textured pokemon. Also placeholder for pokemon name is %pokemon% Placeholder for player name is %player%")
	public static String PokeGiveTargetMessage = "&c[ServerName]&b You've received a %pokemon% enjoy!";

}
