package com.kneelawk.guiatlaslib.api.atlas;

import net.minecraft.client.gui.DrawContext;

/**
 * A baked atlas region. Theoretically this should be more optimized than the Json representation.
 */
public interface BakedAtlasRegion {
    /**
     * Renders this region.
     *
     * @param ctx    the draw context for rendering.
     * @param x      the x position to render at.
     * @param y      the y position to render at.
     * @param width  the width to render at.
     * @param height the height to render at.
     */
    void render(DrawContext ctx, float x, float y, float width, float height);
}
