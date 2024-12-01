package com.Twilight.Renderer;
import com.Twilight.ModEntity.TwilightBuilderEntity;
import com.Twilight.ModModel.TwilightBuilderModel;
import com.Twilight.SBMod.Main;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TwilightBuilderRenderer extends MobRenderer<TwilightBuilderEntity, TwilightBuilderModel<TwilightBuilderEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/twilight_builder.png");

    public TwilightBuilderRenderer(EntityRendererProvider.Context context) {
        super(context, new TwilightBuilderModel<>(context.bakeLayer(TwilightBuilderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TwilightBuilderEntity entity) {
        return TEXTURE;
    }
}
