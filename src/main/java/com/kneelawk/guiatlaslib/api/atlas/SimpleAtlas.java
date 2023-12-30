package com.kneelawk.guiatlaslib.api.atlas;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;

/**
 * Json representation of a simple atlas.
 *
 * @param textureId the optional texture id override.
 * @param width     the width of the atlas texture. Defaults to 256.
 * @param height    the height of the atlas texture. Defaults to 256.
 * @param regions   a map of atlas regions in this atlas.
 */
public record SimpleAtlas(Optional<Identifier> textureId, int width, int height, Map<String, AtlasRegion> regions)
    implements Atlas {
    public static final Codec<SimpleAtlas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.optionalFieldOf("textureId").forGetter(SimpleAtlas::textureId),
        Codec.INT.optionalFieldOf("width", 256).forGetter(SimpleAtlas::width),
        Codec.INT.optionalFieldOf("height", 256).forGetter(SimpleAtlas::height),
        Codec.unboundedMap(Codec.STRING, AtlasRegion.DISPATCH_CODEC).fieldOf("regions").forGetter(SimpleAtlas::regions)
    ).apply(instance, SimpleAtlas::new));

    @Override
    public Codec<? extends Atlas> getCodec() {
        return CODEC;
    }

    @Override
    public BakedAtlas bake(Identifier atlasId) {
        Identifier id = textureId.orElse(atlasId);
        ImmutableMap<String, BakedAtlasRegion> bakedRegions = regions.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), entry.getValue().bake(id, width, height)))
            .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
        return new SimpleBakedAtlas(atlasId, bakedRegions);
    }
}
