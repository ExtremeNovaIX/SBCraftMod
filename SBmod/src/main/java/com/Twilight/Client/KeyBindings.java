package com.Twilight.Client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();
    private KeyBindings() {}

    public final String CATEGORY = "key.categories.sbmod.keys";

    public KeyMapping SHIT_KEY = new KeyMapping("key.sbmod.shit",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_C,-1), CATEGORY
    );
    public KeyMapping RESPLENDENT_BLADE_MODE_SWICHKEY = new KeyMapping("key.sbmod.resplendent_blade_mode_swichkey",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_R,-1), CATEGORY
    );
}
