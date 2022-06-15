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
package net.runelite.client.plugins.oneclickcooking;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.Notifier;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.NPCQuery;
import net.runelite.api.MenuEntry;
import net.runelite.api.util.Text;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.apache.commons.lang3.ArrayUtils;
import org.pf4j.Extension;
import net.runelite.api.NPC;


import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Extension
@PluginDescriptor(
		name = "Oneclick Cooking" ,
		enabledByDefault = false,
		description = "One click cooking in kourend",
		tags = {"cooking", "oneclick","Dog22"}
)

public class OneClickCookingPlugin extends Plugin {
	@Inject
	private Notifier notifier;

	@Inject
	private OneClickUtils utils;

	@Inject
	private ItemManager itemManager;

	@Inject
	private Client client;

	@Inject
	private OneClickCookingConfig config;


	@Provides
	OneClickCookingConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(OneClickCookingConfig.class);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (event.getMenuOption().equals("<col=00ff00>One Click Cooking")) {
			handleClick(event);
		}
	}

	@Subscribe
	private void onClientTick(ClientTick event) {
		if (client.getGameState() != GameState.LOGGED_IN ) {
			return;
		}
		String text = "<col=00ff00>One Click Cooking";
		this.client.insertMenuItem(text, "", MenuAction.UNKNOWN
				.getId(), 0, 0, 0, true);
	}

	@Subscribe
	private void onGameTick(GameTick event) {

	}


	@Subscribe
	private void onChatMessage(ChatMessage event) {

	}

	@Subscribe
	private void handleClick(MenuOptionClicked event) {
		MenuEntry me = null;
		if(utils.getEmptySlots() == 28 || utils.getInventoryItem(3142) == null){ // banking
			me = utils.gameObjectMES(21301,MenuAction.GAME_OBJECT_FIRST_OPTION);
		}
		if(utils.bankOpen() && utils.getInventoryItem(3142) == null) {
			me = utils.depositItems(2,MenuAction.CC_OP,5,983043,false);
		}
		if(utils.bankOpen() && utils.getEmptySlots() == 28){
			me = utils.withdrawItems(1,MenuAction.CC_OP,utils.getBankIndex(3142),WidgetInfo.BANK_ITEM_CONTAINER.getId(),false);
		}
		if(utils.bankOpen() && utils.getInventoryItem(3142) != null){
			me = utils.gameObjectMES(21302,MenuAction.GAME_OBJECT_FIRST_OPTION);
		}
		if(client.getWidget(270,1) != null){
			// 17694735 is id of 270.15 widget
			me = utils.createMenuEntry(1,MenuAction.CC_OP,-1,17694735,false);
		}

		if(me != null) {
			event.setMenuEntry(me);
		}
	}

}

