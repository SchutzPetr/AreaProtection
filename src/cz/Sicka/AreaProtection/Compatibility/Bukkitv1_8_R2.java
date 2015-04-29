package cz.Sicka.AreaProtection.Compatibility;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Bukkitv1_8_R2 implements Compatibility{
	
	public void sendActionBar(Player p, String msg){
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(icbc, (byte)2);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	}
}
