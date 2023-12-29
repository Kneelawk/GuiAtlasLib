package com.kneelawk.api;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import com.kneelawk.api.atlas.BakedAtlas;

/**
 * A reference to a given region of a given atlas.
 */
public class TextureHandle {
    private final Identifier textureId;
    private final String region;

    /**
     * Constructs a texture handle with the given atlas and given atlas region.
     *
     * @param textureId the atlas to look in.
     * @param region    the name of the region in the atlas.
     */
    public TextureHandle(Identifier textureId, String region) {
        this.textureId = textureId;
        this.region = region;
    }

    /**
     * Render the texture to the screen.
     *
     * @param ctx    the draw context.
     * @param x      the x position to render the texture at.
     * @param y      the y position to render the texture at.
     * @param width  the width of the area to render the texture over.
     * @param height the height of the area to render the texture over.
     */
    public void render(DrawContext ctx, int x, int y, int width, int height) {
        BakedAtlas atlas = GuiAtlasLib.getAtlas(textureId);
        if (atlas == null) throw new IllegalStateException("Attempting to render missing atlas: " + textureId);
        atlas.render(ctx, region, x, y, width, height);
    }
}
