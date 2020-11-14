package com.daniel.masks;

import com.daniel.masks.item.MaskMaterial;
import com.daniel.masks.item.MaskItem;
import com.daniel.masks.particle.VirusParticle;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Masks implements ModInitializer, ClientModInitializer {

    public static final String MOD_ID = "masks";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup MASK_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "masks"),
            () -> new ItemStack(Registry.ITEM.get(new Identifier(MOD_ID, "surgical_mask")))
    );

    public static final Item SURGICAL_MASK = new MaskItem(new MaskMaterial("surgical_mask"));
    public static final Item BETTER_MASK = new MaskItem(new MaskMaterial("better_mask"));

    public static final DefaultParticleType VIRUS_PARTICLE = FabricParticleTypes.simple();

    @Override
    public void onInitialize() {
        System.out.println("Initializing " + MOD_ID + " " + VERSION);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "virus"), VIRUS_PARTICLE);

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "surgical_mask"), SURGICAL_MASK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "better_mask"), BETTER_MASK);
    }

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(VIRUS_PARTICLE, VirusParticle.Factory::new);
    }
}
