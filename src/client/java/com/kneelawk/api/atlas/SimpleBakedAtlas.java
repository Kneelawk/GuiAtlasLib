package com.kneelawk.api.atlas;

import java.util.Map;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class SimpleBakedAtlas implements BakedAtlas {
    private final Identifier textureId;
    private final Map<String, BakedAtlasRegion> regions;

    SimpleBakedAtlas(Identifier textureId, Map<String, BakedAtlasRegion> regions) {
        this.textureId = textureId;
        this.regions = regions;
    }

    @Override
    public void render(DrawContext ctx, String region, int x, int y, int width, int height) {
        BakedAtlasRegion atlasRegion = regions.get(region);
        if (atlasRegion == null)
            throw new IllegalStateException("Tried to render missing region '" + region + "' in atlas: " + textureId);
        atlasRegion.render(ctx, x, y, width, height);
    }
}
