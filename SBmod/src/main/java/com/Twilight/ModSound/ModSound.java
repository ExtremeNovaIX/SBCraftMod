package com.Twilight.ModSound;

import com.Twilight.SBMod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModSound {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    private static final List<MusicDisc> MUSIC_DISCS = new ArrayList<>();

    public static List<MusicDisc> getMusicDiscs() {
        return MUSIC_DISCS;
    }
    public static class MusicDisc {
        private final String name;
        private final int lengthInTicks;
        private final RegistryObject<SoundEvent> sound;
        private final RegistryObject<Item> item;

        public MusicDisc(String name, int lengthInTicks) {
            this.name = name;
            this.lengthInTicks = lengthInTicks;
            this.sound = registerSoundEvent(name);
            this.item = registerDiscItem(name, sound, lengthInTicks);
            MUSIC_DISCS.add(this);
            LOGGER.info("Registered music disc: {}", name);
        }

        public RegistryObject<SoundEvent> getSound() {
            return sound;
        }

        public RegistryObject<Item> getItem() {
            return item;
        }
    }

    // 预定义的音乐唱片
    public static final MusicDisc OPERATION_LEAD_SEAL = new MusicDisc("operation_lead_seal", 5000);
    // 在这里添加更多唱片
    // public static final MusicDisc ANOTHER_DISC = new MusicDisc("another_disc", 4000);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, name)));
    }

    private static RegistryObject<Item> registerDiscItem(String name, RegistryObject<SoundEvent> sound, int lengthInTicks) {
        return ITEMS.register(name, () -> new RecordItem(1, sound, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), lengthInTicks));
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            MUSIC_DISCS.forEach(disc -> event.accept(disc.getItem().get()));
        }
    }

    public static void register(IEventBus eventBus) {
        LOGGER.info("Registering ModSound");
        SOUND_EVENTS.register(eventBus);
        ITEMS.register(eventBus);
        LOGGER.info("ModSound registration complete");
    }
}
