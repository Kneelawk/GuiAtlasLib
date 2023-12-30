package com.kneelawk.guiatlaslib.api.atlas;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

/**
 * Json representation of a region that renders part of its texture or repeats if rendered at a different size.
 *
 * @param u             the x position of this region.
 * @param v             the y position of this region.
 * @param width         the width of this region.
 * @param height        the height of this region.
 * @param renderWidth   the width this region would be rendered at full size.
 * @param renderHeight  the height this region would be rendered at full size.
 * @param justification the justification when rendering this region.
 */
public record PartialAtlasRegion(int u, int v, int width, int height, Optional<Integer> renderWidth,
                                 Optional<Integer> renderHeight, Justification justification)
    implements AtlasRegion {
    /**
     * This atlas region type's codec.
     */
    public static final Codec<PartialAtlasRegion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("u").forGetter(PartialAtlasRegion::u),
        Codec.INT.fieldOf("v").forGetter(PartialAtlasRegion::v),
        Codec.INT.fieldOf("width").forGetter(PartialAtlasRegion::width),
        Codec.INT.fieldOf("height").forGetter(PartialAtlasRegion::height),
        Codec.INT.optionalFieldOf("renderWidth").forGetter(PartialAtlasRegion::renderWidth),
        Codec.INT.optionalFieldOf("renderHeight").forGetter(PartialAtlasRegion::renderHeight),
        StringIdentifiable.createCodec(Justification::values).fieldOf("justification")
            .forGetter(PartialAtlasRegion::justification)
    ).apply(instance, PartialAtlasRegion::new));

    @Override
    public Codec<? extends AtlasRegion> getCodec() {
        return CODEC;
    }

    @Override
    public BakedAtlasRegion bake(Identifier textureId, int textureWidth, int textureHeight) {
        return new PartialBakedAtlasRegion(textureId, textureWidth, textureHeight, u, v, width, height,
            renderWidth.orElse(width), renderHeight.orElse(height), justification);
    }
}
