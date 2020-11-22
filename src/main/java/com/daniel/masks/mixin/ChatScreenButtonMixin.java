package com.daniel.masks.mixin;

import com.daniel.masks.Masks;
import com.daniel.masks.client.gui.widget.CoughButtonPressAction;
import com.daniel.masks.item.MaskItem;
import com.daniel.masks.item.MaskMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatScreen.class)
@Environment(EnvType.CLIENT)
public abstract class ChatScreenButtonMixin extends Screen {
    private ButtonWidget button;

    protected ChatScreenButtonMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/gui/widget/TextFieldWidget.setChangedListener(Ljava/util/function/Consumer;)V"), method = "init()V")
    private void initButton(CallbackInfo cb) {
        this.button = new ButtonWidget(this.width - 72, this.height - 35, 70, 20, new TranslatableText("chat.cough"), new CoughButtonPressAction(this.client));
        this.children.add(this.button);
    }

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/gui/widget/TextFieldWidget.render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
    private void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo cb) {
        this.button.render(matrices, mouseX, mouseY, delta);
    }
}
