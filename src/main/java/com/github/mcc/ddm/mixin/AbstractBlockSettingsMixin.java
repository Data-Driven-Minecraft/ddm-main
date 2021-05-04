package com.github.mcc.ddm.mixin;

import com.github.mcc.ddm.duck.AbstractBlockSettingsDuck;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.Settings.class)
abstract public class AbstractBlockSettingsMixin implements AbstractBlockSettingsDuck {
    @Shadow
    private Material material;

    @NotNull
    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(@NotNull Material material) {
        this.material = material;
    }
}
