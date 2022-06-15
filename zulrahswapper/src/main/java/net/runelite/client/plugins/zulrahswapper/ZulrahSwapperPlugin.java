/*
 * Copyright (c) 2018 gazivodag <https://github.com/gazivodag>
 * Copyright (c) 2019 lucwousin <https://github.com/lucwousin>
 * Copyright (c) 2019 infinitay <https://github.com/Infinitay>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.zulrahswapper;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;


import javax.inject.Inject;

@Extension
@PluginDescriptor(
		name = "Zulrah Swapper" ,
		enabledByDefault = false,
		description = "Swaps gear for you at zulrah",
		tags = {"Gear", "Zulrah","Dog","Swapper"}
)
public class ZulrahSwapperPlugin extends Plugin {
	private int zulrahid;

	@Inject
	private Notifier notifier;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ZulrahSwapperConfig config;
	@Inject
	private Client client;

	private long nextKnockOutTick = 0;
	private boolean knockout = true;

	@Provides
	ZulrahSwapperConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(ZulrahSwapperConfig.class);
	}


	@Subscribe
	private void onClientTick(ClientTick event) {
		if (client.getGameState() != GameState.LOGGED_IN) {
			return;
		}

	}

	@Subscribe
	private void onGameTick(GameTick event) {

	}
	@Subscribe
	private void onNpcSpawned(NpcSpawned event){
		zulrahid = event.getNpc().getId();

		switch(zulrahid){
			case 2042: //range

				break;
			case 2043: //meele
				break;

			case 2044: //mage
				break;

		}
	}
	@Subscribe
	private void onChatMessage(ChatMessage event) {

	}

	private void switchGear(){

	}




}

