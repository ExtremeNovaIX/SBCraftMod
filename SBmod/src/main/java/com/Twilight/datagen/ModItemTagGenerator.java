package com.Twilight.datagen;

import com.Twilight.ModItems.ModItems;
import com.Twilight.SBMod.Main;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import com.Twilight.ModItems.ModItems;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Main.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.MUSIC_DISCS)
                .add(Main.AMBIGUOUS_MORALITY_DISC.get());
        this.tag(ItemTags.MUSIC_DISCS)
                .add(Main.OPERATION_LEAD_SEAL_DISC.get());
        this.tag(ItemTags.MUSIC_DISCS)
                .add(Main.MISTY_MEMORY_DISC.get());

    }
}
