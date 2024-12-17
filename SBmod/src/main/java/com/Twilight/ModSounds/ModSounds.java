package com.Twilight.ModSounds;

import com.Twilight.SBMod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static final RegistryObject<SoundEvent> OPERATION_LEAD_SEAL_DISC = registerSoundEvent("operation_lead_seal_disc");
    public static final RegistryObject<SoundEvent> AMBIGUOUS_MORALITY_DISC = registerSoundEvent("ambiguous_morality_disc");
    public static final RegistryObject<SoundEvent> MISTY_MEMORY_DISC = registerSoundEvent("misty_memory_disc");
    public static final RegistryObject<SoundEvent> TIME_FREEZE = registerSoundEvent("time_freeze");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}