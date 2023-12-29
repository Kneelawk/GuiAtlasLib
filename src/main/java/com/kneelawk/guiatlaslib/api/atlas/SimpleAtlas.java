package com.kneelawk.guiatlaslib.api.atlas;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;

public record SimpleAtlas(int width, int height, Map<String, AtlasRegion> regions) implements Atlas {
    public static final Codec<SimpleAtlas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.optionalFieldOf("width", 256).forGetter(SimpleAtlas::width),
        Codec.INT.optionalFieldOf("height", 256).forGetter(SimpleAtlas::height),
        Codec.unboundedMap(Codec.STRING, AtlasRegion.DISPATCH_CODEC).fieldOf("regions").forGetter(SimpleAtlas::regions)
    ).apply(instance, SimpleAtlas::new));

    @Override
    public Codec<? extends Atlas> getCodec() {
        return CODEC;
    }

    @Override
    public BakedAtlas bake(Identifier textureId) {
        ImmutableMap<String, BakedAtlasRegion> bakedRegions = regions.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), entry.getValue().bake(textureId, width, height)))
            .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
        return new SimpleBakedAtlas(textureId, bakedRegions);
    }
}
