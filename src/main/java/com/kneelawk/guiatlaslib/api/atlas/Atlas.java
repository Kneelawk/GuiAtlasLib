package com.kneelawk.guiatlaslib.api.atlas;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.util.Identifier;

import com.kneelawk.guiatlaslib.api.GuiAtlasLib;

/**
 * Json representation of an atlas of sprites on texture.
 */
public interface Atlas {
    /**
     * The codec used for dynamic lookup of an atlas based on {@code "type"}.
     */
    Codec<Atlas> DISPATCH_CODEC = GuiAtlasLib.ATLAS.getCodec().dispatch(Atlas::getCodec, Function.identity());

    /**
     * Gets a specific atlas implementation's codec.
     *
     * @return the codec for this type of atlas.
     */
    Codec<? extends Atlas> getCodec();

    /**
     * Bakes this atlas into a baked atlas, passing in the texture this atlas is associated with.
     *
     * @param atlasId the id where this atlas was found and presumably the texture this atlas is associated with.
     * @return a baked atlas.
     */
    BakedAtlas bake(Identifier atlasId);
}
