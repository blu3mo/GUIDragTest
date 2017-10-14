package red.man10.guidragtest

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.logging.Logger

class GUIDragTest : JavaPlugin(), Listener{

    override fun onEnable() {
        // Plugin startup logic
        Logger.getLogger("aaa")
        Bukkit.getPluginManager().registerEvents(this, this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        showGUI(sender)

        return true
    }

    fun showGUI(sender: CommandSender?){
        val p = sender as Player
        val testGUI = Bukkit.getServer().createInventory(null, 9, "TestGUI")

        var nolores: List<String> = ArrayList()

        createItem(3, testGUI, Material.STONE, 0, 1, "§b§lDraggable only in GUI", nolores)
        createItem(5, testGUI, Material.WOOD, 0, 1, "§c§lNOT Draggable", nolores)
        p.openInventory(testGUI)
    }

    fun createItem(place: Int?, gui: Inventory, material: Material, itemtype: Short?, amount: Int?, itemName: String, loreList: List<String>) {
        val CIitemStack = ItemStack(material, amount!!, itemtype!!)
        val CIitemMeta = CIitemStack.itemMeta
        CIitemMeta.displayName = itemName
        CIitemMeta.lore = loreList
        CIitemStack.itemMeta = CIitemMeta
        gui.setItem(place!!, CIitemStack)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        Bukkit.broadcastMessage("INVENTORY CLICKED")
        if (e.inventory.name == "TestGUI") {
            //When inv name is "TestGUI" (checking both player inv and GUI)
            e.isCancelled = true
            if (e.currentItem != null) {
                if (e.currentItem.type == Material.STONE) {
                    //When currentItem is STONE
                    e.isCancelled = false
                }
            }
            if (e.cursor != null) {
                if (e.cursor.type == Material.STONE && e.clickedInventory.type != InventoryType.PLAYER) {
                    // When cursor is STONE & Clicked Inventory isn't Player INV
                    e.isCancelled = false
                }
            }
        }
    }
}
