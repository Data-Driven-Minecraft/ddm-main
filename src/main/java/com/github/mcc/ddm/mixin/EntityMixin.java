package com.github.mcc.ddm.mixin;

import com.github.mcc.ddm.duck.MinecraftServerDuck;
import com.github.mcc.ddm.duck.ServerResourceManagerDuck;
import com.github.mcc.ddm.item.CustomItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * Mixes in to [Entity] to hook some common events
 */
@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    public World world;
    @Inject(
            method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;",
            at = @At("TAIL")
    )
    void dropStack(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.getItem() instanceof CustomItem) {
            ((CustomItem) stack.getItem()).drop(world, cir.getReturnValue());
        }
    }
}
