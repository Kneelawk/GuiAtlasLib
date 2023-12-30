package com.kneelawk.guiatlaslib.api.atlas;

import net.minecraft.client.gui.DrawContext;

/**
 * A baked atlas. Theoretically this should be more optimized than the Json representation.
 */
public interface BakedAtlas {
    /**
     * Renders a specific region in this atlas.
     *
     * @param ctx    the draw context used for rendering.
     * @param region the region to render.
     * @param x      the x position to render the region at.
     * @param y      the y position to render the region at.
     * @param width  the width to render the region at.
     * @param height the height to render the region at.
     */
    void render(DrawContext ctx, String region, float x, float y, float width, float height);
}
