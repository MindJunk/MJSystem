package de.mindjunk.mjsystem.scripts;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;

public class ClickChat implements Listener {

	public static void travelPoints(Player player) {
		String[] list = TravelpointsFile.get().get("List").toString().split(" ");
		String pointName = MessagesFile.get().getString("Messages.Modules.Travelpoints.name");
		for (int i = 1; i < list.length; i++) {
			IChatBaseComponent comp = ChatSerializer.a(
					"[\"\",{\"text\":\"§7|| " + list[i] + "\",\"bold\":true,\"color\":\"gold\"},{\"text\":\" §7[§3TP§7]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trv " + list[i] + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Teleportieren\",\"italic\":true,\"color\":\"aqua\"}]}},{\"text\":\" §7[§cDEL§7]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/dtrv " + list[i] + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Entfernen\",\"italic\":true,\"color\":\"red\"}]}}]");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		IChatBaseComponent comp = ChatSerializer.a(
				"{\"text\":\"§7|| =======[§aErstellen§7]=======\",\"bold\":true,\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/strv <name>\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"" + pointName + " erstellen\",\"italic\":true,\"color\":\"green\"}]}}");
		PacketPlayOutChat packet = new PacketPlayOutChat(comp);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

	}

}
