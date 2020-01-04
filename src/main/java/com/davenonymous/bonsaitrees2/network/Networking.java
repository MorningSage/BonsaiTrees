package com.davenonymous.bonsaitrees2.network;

import com.davenonymous.bonsaitrees2.BonsaiTrees2;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    private static final String CHANNEL_NAME = "channel";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BonsaiTrees2.MODID, CHANNEL_NAME), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), PacketOpenTreeCreator.class, PacketOpenTreeCreator::toBytes, PacketOpenTreeCreator::new, PacketOpenTreeCreator::handle);
    }

    public static void openTreeCreator(ServerPlayerEntity to) {
        INSTANCE.sendTo(new PacketOpenTreeCreator(), to.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
