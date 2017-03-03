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

    public boolean chatEnabled = true;

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);  //Registro gli eventi
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        if(getConfig().getString("Prefix") == null){ //Controllo se la config è stata creata se no la setto
            getConfig().set("Prefix", "&8[&6DisableChat&8]&r");
            getConfig().set("Message", "&cChat Disabilited."); //Setto nella config la stringa noChat
            getConfig().set("DisabledMessage", "&6The Chat has been disabled!"); //Setto nella config la stringa disableChat
            getConfig().set("EnabledMessage", "&6The Chat has been enabled!");
            getConfig().set("NoPerm", "&cYou don''t have a permission!");
            getConfig().set("ClearedChat", "&bThe Chat has been cleaned!");
            saveConfig(); //Salvo la config
        System.out.print("[DisableChat] Plugin by Stefa98 Enabled");
        this.reloadConfig();
        }
    }

    @Override
    public void onDisable(){
        saveConfig(); //Salvo la config prima di spegnere il plugin
        System.out.print("[DisableChat] Plugin by Stefa98 Disabled");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){//Controllo quando scrivono nella chat
        if(!chatEnabled && !e.getPlayer().isOp()){ //Controllo se la chat e disabilitata e se il player non e' oppato
            for(String s: getConfig().getStringList("Message"))e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s )); //Invio un messaggio con scritto quello nella config
            e.setCancelled(true); //Cancello l'evento cosi non compare il messaggio
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ //Vedo quando eseguono un comando
        if(label.equalsIgnoreCase("dc")) {
            if (args.length != 0 && args[0] != null) {
                if (args[0].equalsIgnoreCase("togglechat")) {
                    if (sender.hasPermission("dc.togglechat")) {
                        if (chatEnabled) {
                            chatEnabled = false;
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix") + getConfig().getString("DisabledMessage")));
                        } else {
                            chatEnabled = true;
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix") + getConfig().getString("EnabledMessage")));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix") + getConfig().getString("NoPerm")));
                    }
                }
                if (args[0].equalsIgnoreCase("clearchat")) {
                    if (sender.hasPermission("dc.clearchat")) {
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix") + getConfig().getString("ClearedChat")));
                    }
                }
                if (args[0].equalsIgnoreCase("help")) {
                    if (sender.hasPermission("dc.help")) {
                        sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                        sender.sendMessage(ChatColor.GRAY + "DisableCommands Help");
                        sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                        sender.sendMessage(ChatColor.GREEN + "/dc togglechat - Enable/Disable Chat");
                        sender.sendMessage(ChatColor.GREEN + "/dc clearchat - Clear Chat");
                    }
                }
            }
            else if (sender.hasPermission("dc.help")) {
                sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
                sender.sendMessage(ChatColor.GRAY + "Plugin Made by:" + " " + ChatColor.BLUE + "stefa98");
                sender.sendMessage(ChatColor.GRAY + "Version:" + " " + "0.1");
                sender.sendMessage(ChatColor.GRAY + "Help:" + " " + ChatColor.GREEN + "/dc help");
                sender.sendMessage(ChatColor.YELLOW + "*----------------------*");
            }
        }

        return true;
    }
}
