package com.github.mcc.ddm.mixin;

import com.github.mcc.ddm.block.BlockDataLoader;
import com.github.mcc.ddm.duck.MinecraftServerDuck;
import com.github.mcc.ddm.duck.ServerResourceManagerDuck;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
abstract public class MinecraftServerMixin implements MinecraftServerDuck {

    @Shadow
    private ServerResourceManager serverResourceManager;

    @NotNull
    @Override
    public ServerResourceManager getServerResourceManager() {
        return serverResourceManager;
    }

    @Inject(
            method = "reloadResources(Ljava/util/Collection;)Ljava/util/concurrent/CompletableFuture;",
            at = @At("TAIL")
    )
    void registerResources(Collection<String> datapacks, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        ((ServerResourceManagerDuck) serverResourceManager).getBlockManager().registerAll((MinecraftServer) (Object) this);
    }

    @Inject(
            method = "reloadResources(Ljava/util/Collection;)Ljava/util/concurrent/CompletableFuture;",
            at = @At("HEAD")
    )
    void unregisterResources(Collection<String> datapacks, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        ((ServerResourceManagerDuck) serverResourceManager).getBlockManager().unregisterAll((MinecraftServer) (Object) this);
    }
}
