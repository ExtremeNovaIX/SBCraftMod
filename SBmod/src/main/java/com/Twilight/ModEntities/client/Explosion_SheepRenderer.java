package com.Twilight.ModEntities.client;

import com.Twilight.ModEntities.custom.Explosion_Sheep;
import com.Twilight.SBMod.Main;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class Explosion_SheepRenderer extends MobRenderer<Explosion_Sheep, Explosion_SheepModel<Explosion_Sheep>> {
    public Explosion_SheepRenderer(EntityRendererProvider.Context context) {
        super(context, new Explosion_SheepModel<>(context.bakeLayer(ModModelLayers.EXPLOSION_SHEEP_LAYER)), 0.5f);
        this.addLayer(new Explosion_Sheep_WoolLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Explosion_Sheep p_114482_) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/sheep_explosion.png");
    }


}
