package com.daniel.masks.client.gui.widget;

import com.daniel.masks.Masks;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CoughButtonPressAction implements ButtonWidget.PressAction {
    private final MinecraftClient client;

    public CoughButtonPressAction(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void onPress(ButtonWidget button) {
        ClientSidePacketRegistry.INSTANCE.sendToServer(Masks.COUGH_PACKET_ID, new PacketByteBuf(Unpooled.buffer()));
    }
}
