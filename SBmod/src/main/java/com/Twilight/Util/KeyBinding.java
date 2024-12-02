package com.Twilight.Util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORIES_TWILIGHT = "key.categories.Twilight";
    public static final String KEY_SHIT = "key.Twilight.SHIT";

    public static final KeyMapping SHIT_KEY = new KeyMapping(KEY_SHIT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM , GLFW.GLFW_KEY_H, KEY_CATEGORIES_TWILIGHT);



}
