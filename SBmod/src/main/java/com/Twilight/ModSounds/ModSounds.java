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
    public static final RegistryObject<SoundEvent> LASER_CANNON_IN_HAND = registerSoundEvent("laser_cannon_in_hand");
    public static final RegistryObject<SoundEvent> LASER_CANNON_SHOOTING = registerSoundEvent("laser_cannon_shooting");
    public static final RegistryObject<SoundEvent> THUNDER_ROD_CHARGE = registerSoundEvent("thunder_rod_charge");
    public static final RegistryObject<SoundEvent> RESPLENDENT_BLADE_DASHING = registerSoundEvent("resplendent_blade_dashing");
    public static final RegistryObject<SoundEvent> RESPLENDENT_BLADE_DASHING_START = registerSoundEvent("resplendent_blade_dashing_start");
    public static final RegistryObject<SoundEvent> RESPLENDENT_BLADE_DEFENDING = registerSoundEvent("resplendent_blade_defending");
    public static final RegistryObject<SoundEvent> RESPLENDENT_BLADE_NORMAL = registerSoundEvent("resplendent_blade_normal");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}