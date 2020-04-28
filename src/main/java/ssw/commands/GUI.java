package ssw.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ssw.Main;
import ssw.data.enums.GUICommands;
import ssw.data.enums.GUIType;

import java.util.ArrayList;

public class GUI {
  public static String generate_name = ChatColor.DARK_PURPLE + "Generate Dungeon";
  public static String createPalette_name = ChatColor.GREEN + "Create Palette";
  public static String deletePalette_name = ChatColor.RED + "Delete Palette";
  public static String addSchematic_name = ChatColor.GREEN + "Add Schematic";
  public static String removeSchematic_name = ChatColor.RED + "Remove Schematic";
  public static String loadSchematic_name = ChatColor.YELLOW + "Load Schematic";
  public static String reload_name = ChatColor.DARK_AQUA + "Reload";
  public static String reloadPalettes_name = ChatColor.DARK_AQUA + "Reload Palettes";
  public static String reloadConfig_name = ChatColor.DARK_AQUA + "Reload Config";

  private Player player;

  Inventory gui = null;
  GUIType guiType = null;
  GUICommands guiCommands = null;

  public GUI(Player plyr) {
    player = plyr;
    String[] str = null;
  }

  public void openMainGUI() {
    guiType = GUIType.MAIN;
    gui = Bukkit.createInventory(player, 27, Main.chatID + " Commands");

    ItemStack generate = new ItemStack(Material.SPAWNER);
    ItemStack createPalette = new ItemStack(Material.GRASS_BLOCK);
    ItemStack deletePalette = new ItemStack(Material.GRASS_BLOCK);
    ItemStack addSchematic = new ItemStack(Material.PAPER);
    ItemStack removeSchematic = new ItemStack(Material.PAPER);
    ItemStack loadSchematic = new ItemStack(Material.PAPER);
    ItemStack reload = new ItemStack(Material.BOOK);
    ItemStack reloadPalettes = new ItemStack(Material.BOOK);
    ItemStack reloadConfig = new ItemStack(Material.BOOK);

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
    addSchematic_lore.add(ChatColor.GRAY + "Add a new schematic to a palette.");
    addSchematic_meta.setLore(addSchematic_lore);
    addSchematic.setItemMeta(addSchematic_meta);

    ItemMeta removeSchematic_meta = removeSchematic.getItemMeta();
    assert removeSchematic_meta != null;
    removeSchematic_meta.setDisplayName(removeSchematic_name);
    ArrayList<String> removeSchematic_lore = new ArrayList<>();
    removeSchematic_lore.add(ChatColor.GRAY + "Remove a schematic from a palette.");
    removeSchematic_meta.setLore(removeSchematic_lore);
    removeSchematic.setItemMeta(removeSchematic_meta);


    ItemMeta loadSchematic_meta = loadSchematic.getItemMeta();
    assert loadSchematic_meta != null;
    loadSchematic_meta.setDisplayName(loadSchematic_name);
    ArrayList<String> loadSchematic_lore = new ArrayList<>();
    loadSchematic_lore.add(ChatColor.GRAY + "Copy a schematic to your chipboard.");
    loadSchematic_meta.setLore(loadSchematic_lore);
    loadSchematic.setItemMeta(loadSchematic_meta);

    ItemMeta reload_meta = reload.getItemMeta();
    assert reload_meta != null;
    reload_meta.setDisplayName(reload_name);
    ArrayList<String> reload_lore = new ArrayList<>();
    reload_lore.add(ChatColor.GRAY + "Reload the plugin.");
    reload_meta.setLore(reload_lore);
    reload.setItemMeta(reload_meta);

    ItemMeta reloadPalettes_meta = reloadPalettes.getItemMeta();
    assert reloadPalettes_meta != null;
    reloadPalettes_meta.setDisplayName(reloadPalettes_name);
    ArrayList<String> reloadPalettes_lore = new ArrayList<>();
    addSchematic_lore.add(ChatColor.GRAY + "Reload all existing palettes.");
    addSchematic_meta.setLore(reloadPalettes_lore);
    reloadPalettes.setItemMeta(reloadPalettes_meta);

    ItemMeta reloadConfig_meta = reloadConfig.getItemMeta();
    assert reloadConfig_meta != null;
    reloadConfig_meta.setDisplayName(reloadConfig_name);
    ArrayList<String> reloadConfig_lore = new ArrayList<>();
    reloadConfig_lore.add(ChatColor.GRAY + "Reload the plugin config.");
    reloadConfig_meta.setLore(reloadConfig_lore);
    reloadConfig.setItemMeta(reloadConfig_meta);

    ItemStack[] menu_items = {null, generate, createPalette, deletePalette,
        addSchematic, removeSchematic, loadSchematic,
        reload, reloadPalettes, reloadConfig
    };
    gui.setContents(menu_items);
  }

  public void openGenerateGUI() {
    player.closeInventory();
    switch (guiType) {
      case MAIN: {
        gui = Bukkit.createInventory(player, 9, Main.chatID + "Available Palettes");

        ItemStack palette = new ItemStack(Material.GRASS_BLOCK);
        ItemStack size = new ItemStack(Material.STRUCTURE_BLOCK);
        ItemStack generate = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemMeta palette_meta = palette.getItemMeta();
        assert palette_meta != null;
        palette_meta.setDisplayName("Select Dungeon Palette");
        ArrayList<String> palette_lore = new ArrayList<>();
        palette_lore.add("No palette selected");
        palette_meta.setLore(palette_lore);
        palette.setItemMeta(palette_meta);

        ItemMeta size_meta = size.getItemMeta();
        assert size_meta != null;
        size_meta.setDisplayName("Select Dungeon Size");
        ArrayList<String> size_lore = new ArrayList<>();
        size_lore.add("No size selected");
        size_meta.setLore(size_lore);
        size.setItemMeta(size_meta);

        ItemMeta generate_meta = generate.getItemMeta();
        assert generate_meta != null;
        size_meta.setDisplayName("Generate Dungeon");
        ArrayList<String> generate_lore = new ArrayList<>();
        generate_lore.add("Generates the current dungeon");
        generate_meta.setLore(generate_lore);
        generate.setItemMeta(generate_meta);

        ItemMeta back_meta = back.getItemMeta();
        assert back_meta != null;
        back_meta.setDisplayName("Cancel dungeon generation");
        ArrayList<String> back_lore = new ArrayList<>();
        back_lore.add("Go back to the MAIN GUI");
        back_meta.setLore(back_lore);
        back.setItemMeta(back_meta);

        ItemStack[] menu_items = {null, palette, size, generate, null, null, null, back, null};
        gui.setContents(menu_items);
      }
    }
  }
}
