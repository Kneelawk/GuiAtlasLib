package com.kneelawk.guiatlaslib.api.atlas;

import java.util.Map;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

/**
 * A simple baked atlas.
 */
public class SimpleBakedAtlas implements BakedAtlas {
    private final Identifier atlasId;
    private final Map<String, BakedAtlasRegion> regions;

    /**
     * Constructs a simple baked atlas with its atlas id and baked regions.
     *
     * @param atlasId the id of this atlas.
     * @param regions the baked regions in this atlas.
     */
    public SimpleBakedAtlas(Identifier atlasId, Map<String, BakedAtlasRegion> regions) {
        this.atlasId = atlasId;
        this.regions = regions;
    }

    @Override
    public void render(DrawContext ctx, String region, int x, int y, int width, int height) {
        BakedAtlasRegion atlasRegion = regions.get(region);
        if (atlasRegion == null)
            throw new IllegalStateException("Tried to render missing region '" + region + "' in atlas: " + atlasId);
        atlasRegion.render(ctx, x, y, width, height);
    }
}
