package com.kneelawk.guiatlaslib.api.atlas;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.util.Identifier;

import com.kneelawk.guiatlaslib.api.GuiAtlasLib;

public interface Atlas {
    Codec<Atlas> DISPATCH_CODEC = GuiAtlasLib.ATLAS.getCodec().dispatch(Atlas::getCodec, Function.identity());

    Codec<? extends Atlas> getCodec();

    BakedAtlas bake(Identifier textureId);
}
