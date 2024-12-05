package com.Twilight.ModEntities.client;

import com.Twilight.SBMod.Main;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation TWILIGHT_BUILDER_LAYER = new ModelLayerLocation(
            new ResourceLocation(Main.MOD_ID, "twilight_builder"), "main");
    public static final ModelLayerLocation EXPLOSION_SHEEP_LAYER = new ModelLayerLocation(
            new ResourceLocation(Main.MOD_ID, "explosion_sheep"), "main");
}
