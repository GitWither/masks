package com.daniel.masks;

import com.daniel.masks.item.MaskItem;
import com.daniel.masks.item.MaskMaterial;
import com.daniel.masks.particle.VirusParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Masks implements ModInitializer, ClientModInitializer {

    public static final String MOD_ID = "masks";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final double COUGH_DISTANCE = 0.5D;

    public static final Identifier COUGH_PACKET_ID = new Identifier(MOD_ID, "cough");

    public static final ItemGroup MASK_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "masks"),
            () -> new ItemStack(Registry.ITEM.get(new Identifier(MOD_ID, "surgical_mask")))
    );

    public static final Item SURGICAL_MASK = new MaskItem(new MaskMaterial("surgical_mask"));
    public static final Item BETTER_MASK = new MaskItem(new MaskMaterial("better_mask"));

    public static final SoundEvent ENTITY_PLAYER_COUGH = new SoundEvent(new Identifier(MOD_ID, "entity.player.cough"));

    public static final DefaultParticleType VIRUS_PARTICLE = FabricParticleTypes.simple();

    @Override
    public void onInitialize() {
        System.out.println("Initializing " + MOD_ID + " " + VERSION);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "virus"), VIRUS_PARTICLE);

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "surgical_mask"), SURGICAL_MASK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "better_mask"), BETTER_MASK);

        Registry.register(Registry.SOUND_EVENT, "entity.player.cough", ENTITY_PLAYER_COUGH);

        ServerSidePacketRegistry.INSTANCE.register(COUGH_PACKET_ID, this::coughServer);
    }

    private void coughServer(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        PlayerEntity player = packetContext.getPlayer();
        Vec3d playerPos = player.getPos();

        double yawRadiansWrapped = Math.toRadians(MathHelper.wrapDegrees(player.yaw));

        Vec3d pos = new Vec3d(
                (-Math.sin(yawRadiansWrapped) * COUGH_DISTANCE) + playerPos.getX(),
                1.6D + playerPos.getY(),
                (Math.cos(yawRadiansWrapped) * COUGH_DISTANCE) + playerPos.getZ()
        );


        packetContext.getTaskQueue().execute(() -> {
            if (!player.world.isClient()) {
                ServerWorld world = (ServerWorld) player.world;

                if (!(player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof MaskItem)) {
                    world.spawnParticles(VIRUS_PARTICLE, pos.getX(), pos.getY(), pos.getZ(), 55, 0.1, 0.1, 0.1, 0.5);
                }
                world.playSoundFromEntity(null, player, ENTITY_PLAYER_COUGH, SoundCategory.BLOCKS, 0.5F, 0.1F);
            }
        });
    }

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(VIRUS_PARTICLE, VirusParticle.Factory::new);
    }
}
