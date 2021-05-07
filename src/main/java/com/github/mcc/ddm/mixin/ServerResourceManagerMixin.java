package com.github.mcc.ddm.mixin;

import com.github.mcc.ddm.block.BlockDataLoader;
import com.github.mcc.ddm.duck.ServerResourceManagerDuck;
import com.github.mcc.ddm.event.EventDataLoader;
import com.github.mcc.ddm.item.ItemDataLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public abstract class ServerResourceManagerMixin implements ServerResourceManagerDuck {
    private final BlockDataLoader blockManager = new BlockDataLoader();
    private final ItemDataLoader itemManager = new ItemDataLoader();
    private final EventDataLoader eventManager = new EventDataLoader();

    @Accessor("resourceManager")
    abstract ReloadableResourceManager getResourceManager();

    @Inject(
            method = "<init>(Lnet/minecraft/server/command/CommandManager$RegistrationEnvironment;I)V",
            at = @At("RETURN")
    )
    private void init(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        getResourceManager().registerListener(blockManager);
        getResourceManager().registerListener(itemManager);
        getResourceManager().registerListener(eventManager);
    }

    @NotNull
    @Override
    public BlockDataLoader getBlockManager() {
        return blockManager;
    }

    @NotNull
    @Override
    public ItemDataLoader getItemManager() {
        return itemManager;
    }

    @NotNull
    @Override
    public EventDataLoader getEventManager() {
        return eventManager;
    }
}
