package net.runelite.client.plugins.oneclickcooking;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.queries.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
        name = "OneClickUtils",
        hidden = true
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class OneClickUtils extends Plugin
{
    @Inject
    public Client client;

    @Inject
    public ClientThread clientThread;

    //------------------------------------------------------------//
    // Ganom Utils
    //------------------------------------------------------------//

    public int[] stringToIntArray(String string)
    {
        return Arrays.stream(string.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
    }

    @Nullable
    public GameObject findNearestGameObject(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    @Nullable
    public WallObject findNearestWallObject(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new WallObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    @Nullable
    public DecorativeObject findNearestDecorObject(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new DecorativeObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    @Nullable
    public GroundObject findNearestGroundObject(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GroundObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public List<GameObject> getGameObjects(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return new ArrayList<>();
        }

        return new GameObjectQuery()
                .idEquals(ids)
                .result(client)
                .list;
    }

    public List<WallObject> getWallObjects(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return new ArrayList<>();
        }

        return new WallObjectQuery()
                .idEquals(ids)
                .result(client)
                .list;
    }

    public List<DecorativeObject> getDecorObjects(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return new ArrayList<>();
        }

        return new DecorativeObjectQuery()
                .idEquals(ids)
                .result(client)
                .list;
    }

    public List<GroundObject> getGroundObjects(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return new ArrayList<>();
        }

        return new GroundObjectQuery()
                .idEquals(ids)
                .result(client)
                .list;
    }

    @Nullable
    public TileObject findNearestObject(int... ids)
    {
        GameObject gameObject = findNearestGameObject(ids);

        if (gameObject != null)
        {
            return gameObject;
        }

        WallObject wallObject = findNearestWallObject(ids);

        if (wallObject != null)
        {
            return wallObject;
        }
        DecorativeObject decorativeObject = findNearestDecorObject(ids);

        if (decorativeObject != null)
        {
            return decorativeObject;
        }

        return findNearestGroundObject(ids);
    }

    public List<WidgetItem> getItems(int... itemIDs)
    {
        assert client.isClientThread();

        return new InventoryWidgetItemQuery()
                .idEquals(itemIDs)
                .result(client)
                .list;
    }

    public List<Widget> getEquippedItems(int[] itemIds)
    {
        assert client.isClientThread();

        Widget equipmentWidget = client.getWidget(WidgetInfo.EQUIPMENT);

        List<Integer> equippedIds = new ArrayList<>();

        for (int i : itemIds)
        {
            equippedIds.add(i);
        }

        List<Widget> equipped = new ArrayList<>();

        if (equipmentWidget.getStaticChildren() != null)
        {
            for (Widget widgets : equipmentWidget.getStaticChildren())
            {
                for (Widget items : widgets.getDynamicChildren())
                {
                    if (equippedIds.contains(items.getItemId()))
                    {
                        equipped.add(items);
                    }
                }
            }
        }
        else
        {
            System.out.println("Children is Null!");
        }

        return equipped;
    }

//    public int getTabHotkey(Tab tab)
//    {
//        assert client.isClientThread();
//
//        final int var = client.getVarbitValue(client.getVarps(), tab.getVarbit());
//        final int offset = 111;
//
//        switch (var)
//        {
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//            case 8:
//            case 9:
//            case 10:
//            case 11:
//            case 12:
//                return var + offset;
//            case 13:
//                return 27;
//            default:
//                return -1;
//        }
//    }
//
//    public WidgetInfo getSpellWidgetInfo(String spell)
//    {
//        assert client.isClientThread();
//        return Spells.getWidget(spell);
//    }
//
//    public WidgetInfo getPrayerWidgetInfo(String spell)
//    {
//        assert client.isClientThread();
//        return PrayerMap.getWidget(spell);
//    }
//
//    public Widget getSpellWidget(String spell)
//    {
//        assert client.isClientThread();
//        return client.getWidget(Spells.getWidget(spell));
//    }
//
//    public Widget getPrayerWidget(String spell)
//    {
//        assert client.isClientThread();
//        return client.getWidget(PrayerMap.getWidget(spell));
//    }

    //------------------------------------------------------------//
    // Key
    //------------------------------------------------------------//

    /**
     * This method must be called on a new
     * thread, if you try to call it on
     * {@link net.runelite.client.callback.ClientThread}
     * it will result in a crash/desynced thread.
     */
    public void typeString(String string)
    {
        assert !client.isClientThread();

        for (char c : string.toCharArray())
        {
            pressKey(c);
        }
    }

    public void pressKey(char key)
    {
        keyEvent(401, key);
        keyEvent(402, key);
        keyEvent(400, key);
    }

    public void keyEvent(int id, char key)
    {
        KeyEvent e = new KeyEvent(
                client.getCanvas(), id, System.currentTimeMillis(),
                0, KeyEvent.VK_UNDEFINED, key
        );

        client.getCanvas().dispatchEvent(e);
    }

    //------------------------------------------------------------//
    // Mouse
    //------------------------------------------------------------//

    /**
     * This method must be called on a new
     * thread, if you try to call it on
     * {@link net.runelite.client.callback.ClientThread}
     * it will result in a crash/desynced thread.
     */
    public void click(Rectangle rectangle)
    {
        assert !client.isClientThread();
        Point point = getClickPoint(rectangle);
        click(point);
    }

    public void click(Point p)
    {
        assert !client.isClientThread();

        if (client.isStretchedEnabled())
        {
            final Dimension stretched = client.getStretchedDimensions();
            final Dimension real = client.getRealDimensions();
            final double width = (stretched.width / real.getWidth());
            final double height = (stretched.height / real.getHeight());
            final Point point = new Point((int) (p.getX() * width), (int) (p.getY() * height));
            mouseEvent(501, point);
            mouseEvent(502, point);
            mouseEvent(500, point);
            return;
        }
        mouseEvent(501, p);
        mouseEvent(502, p);
        mouseEvent(500, p);
    }

    public Point getClickPoint(@NotNull Rectangle rect)
    {
        final int x = (int) (rect.getX() + getRandomIntBetweenRange((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + getRandomIntBetweenRange((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

        return new Point(x, y);
    }

    public int getRandomIntBetweenRange(int min, int max)
    {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public void mouseEvent(int id, @NotNull Point point)
    {
        MouseEvent e = new MouseEvent(
                client.getCanvas(), id,
                System.currentTimeMillis(),
                0, point.getX(), point.getY(),
                1, false, 1
        );

        client.getCanvas().dispatchEvent(e);
    }

    //------------------------------------------------------------//
    // Utils
    //------------------------------------------------------------//
    public Point getLocation(TileObject tileObject) {
        if (tileObject == null) {
            return new Point(0, 0);
        }
        if (tileObject instanceof GameObject) {
            return ((GameObject) tileObject).getSceneMinLocation();
        }
        return new Point(tileObject.getLocalLocation().getSceneX(), tileObject.getLocalLocation().getSceneY());
    }

    public Point getLocation(WallObject wallObject) {
        return new Point(wallObject.getLocalLocation().getSceneX(),
                wallObject.getLocalLocation().getSceneY());
    }

    public GameObject getGameObject(int id)
    {
        return new GameObjectQuery()
                .idEquals(id)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public GroundObject getGroundObject(int ID) {
        return new GroundObjectQuery()
                .idEquals(ID)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public TileItem getNearestTileItem(List<TileItem> tileItems)
    {
        int currentDistance;
        if (tileItems.size()==0 || tileItems.get(0) == null)
        {
            return null;
        }
        TileItem closestTileItem = tileItems.get(0);
        int closestDistance = closestTileItem.getTile().getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation());
        for (TileItem tileItem : tileItems)
        {
            currentDistance = tileItem.getTile().getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation());
            if (currentDistance < closestDistance)
            {
                closestTileItem = tileItem;
                closestDistance = currentDistance;
            }
        }
        return closestTileItem;
    }

    public Widget getInventoryItem(int id) {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        Widget bankInventoryWidget = client.getWidget(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER);
        if (inventoryWidget!=null && !inventoryWidget.isHidden())
        {
            return getWidgetItem(inventoryWidget,id);
        }
        if (bankInventoryWidget!=null && !bankInventoryWidget.isHidden())
        {
            return getWidgetItem(bankInventoryWidget,id);
        }
        return null;
    }

    private Widget getWidgetItem(Widget widget,int id) {
        for (Widget item : widget.getDynamicChildren())
        {
            if (item.getItemId() == id)
            {
                return item;
            }
        }
        return null;
    }
    public int getEmptySlots() {
        Widget inventory = client.getWidget(WidgetInfo.INVENTORY.getId());
        Widget bankInventory = client.getWidget(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getId());

        if (inventory!=null && !inventory.isHidden()
                && inventory.getDynamicChildren()!=null)
        {
            List<Widget> inventoryItems = Arrays.asList(client.getWidget(WidgetInfo.INVENTORY.getId()).getDynamicChildren());
            return (int) inventoryItems.stream().filter(item -> item.getItemId() == 6512).count();
        }

        if (bankInventory!=null && !bankInventory.isHidden()
                && bankInventory.getDynamicChildren()!=null)
        {
            List<Widget> inventoryItems = Arrays.asList(client.getWidget(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getId()).getDynamicChildren());
            return (int) inventoryItems.stream().filter(item -> item.getItemId() == 6512).count();
        }
        return -1;
    }

    public boolean bankOpen() {
        return client.getItemContainer(InventoryID.BANK) != null;
    }

    public int getBankIndex(int ID){
        WidgetItem bankItem = new BankItemQuery()
                .idEquals(ID)
                .result(client)
                .first();
        if (bankItem != null) {
            return bankItem.getWidget().getIndex();
        }
        return -1;
    }

    public MenuEntry depositAllMES(){
        return createMenuEntry(
                1,
                MenuAction.CC_OP,
                -1,
                WidgetInfo.BANK_DEPOSIT_INVENTORY.getId(),
                false);
    }

    public MenuEntry depositItems(int identifier, MenuAction menuAction, int param0, int param1, boolean forceLeftClick){
        return createMenuEntry(
                identifier,
                menuAction,
                param0,
                param1,
                forceLeftClick);
    }

    public MenuEntry withdrawItems(int identifier, MenuAction menuAction, int param0, int param1, boolean forceLeftClick){
        return createMenuEntry(
                identifier,
                menuAction,
                param0,
                param1,
                forceLeftClick);
    }

    public MenuEntry gameObjectMES(int objectID, MenuAction menuAction) {
        GameObject gameObject = getGameObject(objectID);
        return createMenuEntry(
                gameObject.getId(),
                menuAction,
                getLocation(gameObject).getX(),
                getLocation(gameObject).getY(),
                false);
    }

    public Point getNPCLocation(NPC npc) {
        return new Point(npc.getLocalLocation().getSceneX(),npc.getLocalLocation().getSceneY());
    }

    public NPC getClosestNPC(int id) {
        return new NPCQuery()
                .idEquals(id)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public GameObject getClosestGameObject(int id) {
        return new GameObjectQuery()
                .idEquals(id)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    //------------------------------------------------------------//
    // Menu Entry Creation
    //------------------------------------------------------------//
    public MenuEntry createMenuEntry(int identifier, MenuAction type, int param0, int param1, boolean forceLeftClick) {
        return client.createMenuEntry(0).setOption("").setTarget("").setIdentifier(identifier).setType(type)
                .setParam0(param0).setParam1(param1).setForceLeftClick(forceLeftClick);
    }

    public MenuEntry createHumidifyMenuEntry() {
        return createMenuEntry(
                1,
                MenuAction.CC_OP,
                -1,
                WidgetInfo.SPELL_HUMIDIFY.getId(),
                true);
    }

    public boolean shouldCastHumidify(Collection<Integer> ids) {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY.getId());
        if (inventoryWidget != null) {
            Collection<WidgetItem> items = inventoryWidget.getWidgetItems();
            for (WidgetItem item : items) {
                if (ids.contains(item.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public MenuEntry specAtk(){
        Widget specAtk = client.getWidget(WidgetInfo.MINIMAP_SPEC_CLICKBOX);
        return createMenuEntry(
                1,
                MenuAction.CC_OP,
                -1,
                specAtk.getId(),
                false);
    }
}
