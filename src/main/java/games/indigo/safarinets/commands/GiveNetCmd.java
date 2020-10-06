package games.indigo.safarinets.commands;

import games.indigo.safarinets.api.NetType;
import games.indigo.safarinets.api.SafariNet;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveNetCmd implements CommandExecutor {

    public String givenet = "givenet";
    private String[] helpMsg = {ChatColor.AQUA + "/givenet " +
            ChatColor.DARK_AQUA + "[player] " +
            ChatColor.AQUA + "<single_use|multi_use> " +
            ChatColor.DARK_AQUA + "[amount]"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("safarinets.command.give")) {
            if (args.length >= 1) {
                Player giveTo = null;
                NetType type = null;
                int amount = 1;
                for (String arg : args) {
                    if (Bukkit.getPlayer(arg) != null) {
                        giveTo = Bukkit.getPlayer(arg);
                    } else if (NetType.getNetType(arg) != null) {
                        type = NetType.valueOf(arg.toUpperCase());
                    } else if (StringUtils.isNumeric(arg)) {
                        amount = Integer.parseInt(arg);
                    }
                }
                if (giveTo == null && sender instanceof Player) {
                    giveTo = (Player) sender;
                }
                if (giveTo != null && type != null) {
                    ItemStack net = SafariNet.getEmptyNet(type);
                    net.setAmount(amount);
                    giveTo.getInventory().addItem(net);
                    return true;
                } else {
                    sender.sendMessage(helpMsg);
                    return true;
                }
            } else {
                sender.sendMessage(helpMsg);
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to use this command.");
            return true;
        }
    }
}
