package bonnett.commands;

import bonnett.Main;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI {
    public static String generate_name = ChatColor.YELLOW + "Generate Dungeon";
    public static String createPalette_name = ChatColor.GREEN + "Create Palette";
    public static String deletePalette_name = ChatColor.RED + "Delete Palette";
    public static String addSchematic_name = ChatColor.GREEN + "Add Schematic";
    public static String removeSchematic_name = ChatColor.RED + "Remove Schematic";
    public static String loadSchematic_name = ChatColor.YELLOW + "Load Schematic";
    public static String reload_name = ChatColor.DARK_AQUA + "Reload";
    public static String reloadPalettes_name = ChatColor.DARK_AQUA + "Reload Schematics";
    public static String reloadConfig_name = ChatColor.DARK_AQUA + "Reload Config";

    Player player;

    public GUI(CommandSender sender) {
        player = (Player) sender;
    }

    public GUI(Player plyr) {
        player = plyr;
    }

    public void openMainGUI() {
        Inventory gui = Bukkit.createInventory(player, 9, Main.chatID + " Commands");

        ItemStack generate = new ItemStack(Material.SPAWNER);
        ItemStack createPalette = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
        ItemStack deletePalette = new ItemStack(Material.RED_CONCRETE);
        ItemStack addSchematic = new ItemStack(Material.PAPER);
        ItemStack removeSchematic = new ItemStack(Material.PAPER);
        ItemStack loadSchematic = new ItemStack(Material.FILLED_MAP);
        ItemStack reload = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack reloadPalettes = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack reloadConfig = new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta generate_meta = generate.getItemMeta();
        assert generate_meta != null;
        generate_meta.setDisplayName(generate_name);
        ArrayList<String> generate_lore = new ArrayList<>();
        generate_lore.add(ChatColor.GRAY + "Generate a new dungeon.");
        generate_meta.setLore(generate_lore);
        generate.setItemMeta(generate_meta);

        ItemMeta createPalette_meta = createPalette.getItemMeta();
        assert createPalette_meta != null;
        createPalette_meta.setDisplayName(createPalette_name);
        ArrayList<String> createPalette_lore = new ArrayList<>();
        createPalette_lore.add(ChatColor.GRAY + "Creates an empty palette.");
        createPalette_meta.setLore(createPalette_lore);
        createPalette.setItemMeta(createPalette_meta);

        ItemMeta deletePalette_meta = deletePalette.getItemMeta();
        assert deletePalette_meta != null;
        deletePalette_meta.setDisplayName(deletePalette_name);
        ArrayList<String> deletePalette_lore = new ArrayList<>();
        deletePalette_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        deletePalette_meta.setLore(deletePalette_lore);
        deletePalette.setItemMeta(deletePalette_meta);

        ItemMeta addSchematic_meta = addSchematic.getItemMeta();
        assert addSchematic_meta != null;
        addSchematic_meta.setDisplayName(addSchematic_name);
        ArrayList<String> addSchematic_lore = new ArrayList<>();
        addSchematic_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        addSchematic_meta.setLore(addSchematic_lore);
        addSchematic.setItemMeta(addSchematic_meta);

        ItemMeta removeSchematic_meta = removeSchematic.getItemMeta();
        assert removeSchematic_meta != null;
        removeSchematic_meta.setDisplayName(removeSchematic_name);
        ArrayList<String> removeSchematic_lore = new ArrayList<>();
        removeSchematic_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        removeSchematic_meta.setLore(removeSchematic_lore);
        removeSchematic.setItemMeta(removeSchematic_meta);


        ItemMeta loadSchematic_meta = loadSchematic.getItemMeta();
        assert loadSchematic_meta != null;
        loadSchematic_meta.setDisplayName(loadSchematic_name);
        ArrayList<String> loadSchematic_lore = new ArrayList<>();
        loadSchematic_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        loadSchematic_meta.setLore(loadSchematic_lore);
        loadSchematic.setItemMeta(loadSchematic_meta);

        ItemMeta reload_meta = reload.getItemMeta();
        assert reload_meta != null;
        reload_meta.setDisplayName(reload_name);
        ArrayList<String> reload_lore = new ArrayList<>();
        reload_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        reload_meta.setLore(reload_lore);
        reload.setItemMeta(reload_meta);

        ItemMeta reloadPalettes_meta = reloadPalettes.getItemMeta();
        assert reloadPalettes_meta != null;
        reloadPalettes_meta.setDisplayName(reloadPalettes_name);
        ArrayList<String> reloadPalettes_lore = new ArrayList<>();
        addSchematic_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        addSchematic_meta.setLore(reloadPalettes_lore);
        reloadPalettes.setItemMeta(reloadPalettes_meta);

        ItemMeta reloadConfig_meta = reloadConfig.getItemMeta();
        assert reloadConfig_meta != null;
        reloadConfig_meta.setDisplayName(reloadConfig_name);
        ArrayList<String> reloadConfig_lore = new ArrayList<>();
        reloadConfig_lore.add(ChatColor.GRAY + "Delete an existing palette.");
        reloadConfig_meta.setLore(reloadConfig_lore);
        reloadConfig.setItemMeta(reloadConfig_meta);

        ItemStack[] menu_items = {generate, createPalette, deletePalette,
                addSchematic, removeSchematic, loadSchematic,
                reload, reloadPalettes, reloadConfig
        };
        gui.setContents(menu_items);
        player.openInventory(gui);
    }

    public String generationPalette;
    public int generationSize;
    public void openGenerateGUI(int page) {
        switch (page) {
            case 1: {
                Inventory gui = Bukkit.createInventory(player, 63, Main.chatID + " [Generate] Available Palettes");
                ItemStack[] menu_items = null;
                List<ItemStack> menu_itemsList = new ArrayList<>();
                for (String palette : Main.validPalettes) {
                    ItemStack newItem = new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta newItem_meta = newItem.getItemMeta();
                    assert newItem_meta != null;
                    newItem_meta.setDisplayName(palette);
                    newItem.setItemMeta(newItem_meta);

                    menu_itemsList.add(newItem);
                }
                assert false;
                menu_items = menu_itemsList.toArray(menu_items);
                gui.setContents(menu_items);
                player.openInventory(gui);
            }
            case 2: {
                Inventory gui = Bukkit.createInventory(player, 63, Main.chatID + " Dungeon Size");
            }
        }
    }
}
