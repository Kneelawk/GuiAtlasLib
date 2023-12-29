package com.kneelawk.guiatlaslib.api.atlas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;

public record SimpleAtlasRegion(int u, int v, int width, int height) implements AtlasRegion {
    public static final Codec<SimpleAtlasRegion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("u").forGetter(SimpleAtlasRegion::u),
        Codec.INT.fieldOf("v").forGetter(SimpleAtlasRegion::v),
        Codec.INT.fieldOf("width").forGetter(SimpleAtlasRegion::width),
        Codec.INT.fieldOf("height").forGetter(SimpleAtlasRegion::height)
    ).apply(instance, SimpleAtlasRegion::new));

    @Override
    public Codec<? extends AtlasRegion> getCodec() {
        return CODEC;
    }

    @Override
    public BakedAtlasRegion bake(Identifier textureId, int textureWidth, int textureHeight) {
        return new SimpleBakedAtlasRegion(textureId, textureWidth, textureHeight, u, v, width, height);
    }
}
