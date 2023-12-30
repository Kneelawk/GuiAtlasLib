package com.kneelawk.guiatlaslib.api.atlas;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.util.Identifier;

import com.kneelawk.guiatlaslib.api.GuiAtlasLib;

/**
 * Json representations of a region in an atlas representing a texture to be rendered.
 */
public interface AtlasRegion {
    /**
     * Dynamic lookup codec that selects the right type of atlas region based on {@code "type"}.
     */
    Codec<AtlasRegion> DISPATCH_CODEC =
        GuiAtlasLib.ATLAS_REGION.getCodec().dispatch(AtlasRegion::getCodec, Function.identity());

    /**
     * Gets the codec for this type of atlas region.
     *
     * @return this type's codec.
     */
    Codec<? extends AtlasRegion> getCodec();

    /**
     * Bakes this atlas region into a baked atlas region.
     *
     * @param textureId     the texture this atlas region is associated with, which may or may not be the same as the atlas id.
     * @param textureWidth  the width of the whole atlas texture.
     * @param textureHeight the height of the whole atlas texture.
     * @return a baked atlas region.
     */
    BakedAtlasRegion bake(Identifier textureId, int textureWidth, int textureHeight);
}
