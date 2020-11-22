package com.daniel.masks.mixin;

import com.daniel.masks.item.MaskItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/render/entity/feature/ArmorFeatureRenderer.setVisible(Lnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/entity/EquipmentSlot;)V"), method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V")
    public void deflateMask(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T livingEntity, EquipmentSlot equipmentSlot, int i, A bipedEntityModel, CallbackInfo cb) {
        if (livingEntity.getEquippedStack(equipmentSlot).getItem() instanceof MaskItem) {
            matrices.scale(0.85f, 0.85f, 0.85f);
            //matrices.translate(0, -0.05f, 0);
        }
    }
}
