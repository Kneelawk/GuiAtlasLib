package com.kneelawk.guiatlaslib.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import net.minecraft.util.Identifier;

public class GAConstants {
    public static final String MOD_ID = "guiatlaslib";

    @Contract("_ -> new")
    public static @NotNull Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
