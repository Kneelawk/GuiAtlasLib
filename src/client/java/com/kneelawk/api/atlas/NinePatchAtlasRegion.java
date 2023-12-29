package com.kneelawk.api.atlas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;

public record NinePatchAtlasRegion(int u, int v, int width, int height, int leftWidth, int rightWidth, int topHeight,
                                   int bottomHeight, boolean tiling) implements AtlasRegion {
    public static final Codec<NinePatchAtlasRegion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("u").forGetter(NinePatchAtlasRegion::u),
        Codec.INT.fieldOf("v").forGetter(NinePatchAtlasRegion::v),
        Codec.INT.fieldOf("width").forGetter(NinePatchAtlasRegion::width),
        Codec.INT.fieldOf("height").forGetter(NinePatchAtlasRegion::height),
        Codec.INT.fieldOf("leftWidth").forGetter(NinePatchAtlasRegion::leftWidth),
        Codec.INT.fieldOf("rightWidth").forGetter(NinePatchAtlasRegion::rightWidth),
        Codec.INT.fieldOf("topHeight").forGetter(NinePatchAtlasRegion::topHeight),
        Codec.INT.fieldOf("bottomHeight").forGetter(NinePatchAtlasRegion::bottomHeight),
        Codec.BOOL.optionalFieldOf("tiling", true).forGetter(NinePatchAtlasRegion::tiling)
    ).apply(instance, NinePatchAtlasRegion::new));

    @Override
    public Codec<? extends AtlasRegion> getCodec() {
        return CODEC;
    }

    @Override
    public BakedAtlasRegion bake(Identifier textureId, int textureWidth, int textureHeight) {
        return new NinePatchBakedAtlasRegion(textureId, textureWidth, textureHeight, u, v, width, height, leftWidth,
            rightWidth, topHeight, bottomHeight, tiling);
    }
}
