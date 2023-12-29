package com.kneelawk.api.atlas;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.util.Identifier;

import com.kneelawk.api.GuiAtlasLib;

public interface AtlasRegion {
    Codec<AtlasRegion> DISPATCH_CODEC =
        GuiAtlasLib.ATLAS_REGION.getCodec().dispatch(AtlasRegion::getCodec, Function.identity());

    Codec<? extends AtlasRegion> getCodec();

    BakedAtlasRegion bake(Identifier textureId, int textureWidth, int textureHeight);
}
