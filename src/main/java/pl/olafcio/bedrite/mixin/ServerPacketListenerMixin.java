package pl.olafcio.bedrite.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPacketListener.class)
public class ServerPacketListenerMixin {
    @Shadow
    private MinecraftServer server;

    @ModifyVariable(at = @At(value = "INVOKE", target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"), method = "onChatMessage",
                    name = "var2", index = 2, ordinal = 0)
    public String onChatMessage__formatMessage(String msg) {
        String[] players = server.getPlayerNames();
        for (String nick : players)
            msg = msg.replace(" " + nick, " §e" + nick + "§f");

        return msg;
    }
}
