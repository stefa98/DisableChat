package me.stefa98.DisableChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by stefa98 on 02/03/17.
 */

public class Main extends JavaPlugin implements Listener {

    private boolean chatEnabled = true;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (getConfig().getString("Prefix") == null) {
            getConfig().set("Prefix", "&8[&6DisableChat&8]&r");
            getConfig().set("Message", "&cChat Disabilited.");
            getConfig().set("DisabledMessage", "&6The Chat has been disabled!");
            getConfig().set("EnabledMessage", "&6The Chat has been enabled!");
            getConfig().set("NoPerm", "&cYou don''t have a permission!");
            getConfig().set("ClearedChat", "&bThe Chat has been cleaned!");
            saveConfig(); //Salvo la config
            System.out.println("[DisableChat] Plugin by Stefa98 Enabled");
            reloadConfig();
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
        System.out.println("[DisableChat] Plugin by Stefa98 Disabled");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!chatEnabled && !e.getPlayer().isOp()) {
            for (String s : getConfig().getStringList("Message"))
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            e.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("dc")) {
            if (args.length != 0) {
                switch (args[0]) {
                    case "togglechat":
                        if (sender.hasPermission("dc.togglechat")) {
                            if (chatEnabled) {
                                chatEnabled = false;
                                broadcast("DisableMessage");
                            } else {
                                chatEnabled = true;
                                broadcast("EnabledMessage");
                            }
                        } else {
                            sender.sendMessage(getFromConfig("NoPerm"));
                        }
                        break;
                    case "clearchat":
                        if (sender.hasPermission("dc.clearchat")) {
                            for (int n = 0; n < 20; n++) {
                                Bukkit.broadcastMessage("");
                            }
                            sender.sendMessage(getFromConfig("ClearedChat"));
                        } else {
                            sender.sendMessage(getFromConfig("NoPerm"));
                        }
                        break;
                    case "help":
                        if (sender.hasPermission("dc.help")) {
                            sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                            sender.sendMessage(ChatColor.GRAY + "DisableCommands Help");
                            sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                            sender.sendMessage(ChatColor.GREEN + "/dc togglechat - Enable/Disable Chat");
                            sender.sendMessage(ChatColor.GREEN + "/dc clearchat - Clear Chat");
                        } else {
                            sender.sendMessage(getFromConfig("NoPerm"));
                        }
                        break;
                }
            } else if (sender.hasPermission("dc.help")) {
                sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                sender.sendMessage(ChatColor.GRAY + "Plugin Made by:" + " " + ChatColor.BLUE + "stefa98");
                sender.sendMessage(ChatColor.GRAY + "Version:" + " " + "1.3");
                sender.sendMessage(ChatColor.GRAY + "Help:" + " " + ChatColor.GREEN + "/dc help");
                sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
            }
        }
        return true;
    }

    private void broadcast(String key) {
        Bukkit.broadcastMessage(getFromConfig(key));
    }

    private String getFromConfig(String key) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix") + getConfig().getString(key));
    }
}
