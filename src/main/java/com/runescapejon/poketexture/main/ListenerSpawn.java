package com.runescapejon.poketexture.main;

import java.util.Random;

import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnActionPokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ListenerSpawn {

	@SubscribeEvent
	public void onSpawnEntityEvent(SpawnEvent event) {
		if (Config.AllowTexturePokemonToSpawn) {
			Random rand = new Random();
			if (rand.nextInt(100) + 1 <= Config.Chance) {
				if (event.action instanceof SpawnActionPokemon) {
					EntityPixelmon entityPixelmon = ((SpawnActionPokemon) event.action).getOrCreateEntity();
					for (String name : Config.PokemonList) {
						if (entityPixelmon.getSpecies().name().equalsIgnoreCase(name)) {
							entityPixelmon.canDespawn = false;
							entityPixelmon.getPokemonData().setCustomTexture(Config.CustomTexture);
							entityPixelmon.getPokemonData().setNickname(
									Config.SetColorName.replace("&", "\u00A7") + entityPixelmon.getSpecies().name);
						}
					}
				}
			}
		}
	}
}
