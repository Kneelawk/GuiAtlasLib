package com.kneelawk.guiatlaslib.api;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import com.kneelawk.guiatlaslib.api.atlas.Atlas;
import com.kneelawk.guiatlaslib.api.atlas.AtlasRegion;
import com.kneelawk.guiatlaslib.api.atlas.BakedAtlas;
import com.kneelawk.guiatlaslib.impl.AtlasResourceManager;

import static com.kneelawk.guiatlaslib.impl.GAConstants.id;

/**
 * Public static methods and fields for interacting with GuiAtlasLib.
 */
public class GuiAtlasLib {
    private GuiAtlasLib() {
    }

    /**
     * Registry of atlas codecs.
     */
    public static final Registry<Codec<? extends Atlas>> ATLAS =
        new SimpleRegistry<>(RegistryKey.ofRegistry(id("atlas")), Lifecycle.stable());

    /**
     * Registry of atlas region codecs.
     */
    public static final Registry<Codec<? extends AtlasRegion>> ATLAS_REGION =
        new SimpleRegistry<>(RegistryKey.ofRegistry(id("atlas_region")), Lifecycle.stable());

    /**
     * Gets the atlas for the given texture.
     *
     * @param atlasId the id of the atlas to look up.
     * @return the atlas of the given texture.
     */
    public static @Nullable BakedAtlas getAtlas(Identifier atlasId) {
        return AtlasResourceManager.getAtlas(atlasId);
    }
}
