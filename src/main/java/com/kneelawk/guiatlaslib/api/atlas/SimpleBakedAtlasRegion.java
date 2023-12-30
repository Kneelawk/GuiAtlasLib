package com.kneelawk.guiatlaslib.api.atlas;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

/**
 * A simple baked atlas region that stretches its texture if rendered at a different size.
 */
public class SimpleBakedAtlasRegion implements BakedAtlasRegion {
    private final Identifier textureId;

    private final float u0;
    private final float u1;
    private final float v0;
    private final float v1;

    /**
     * Constructs a simple baked atlas region.
     *
     * @param textureId     the texture this region will render from.
     * @param textureWidth  the width of the overall atlas texture.
     * @param textureHeight the height of the overall atlas texture.
     * @param u             the x position of this region.
     * @param v             the y position of this region.
     * @param width         the width of this region.
     * @param height        the height of this region.
     */
    public SimpleBakedAtlasRegion(Identifier textureId, int textureWidth, int textureHeight, int u, int v, int width,
                                  int height) {
        this.textureId = textureId;

        this.u0 = (float) u / (float) textureWidth;
        this.u1 = (float) (u + width) / (float) textureWidth;
        this.v0 = (float) v / (float) textureHeight;
        this.v1 = (float) (v + height) / (float) textureHeight;
    }

    @Override
    public void render(DrawContext ctx, float x, float y, float width, float height) {
        Matrix4f mat = ctx.getMatrices().peek().getPositionMatrix();
        float x1 = x + width;
        float y1 = y + height;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, textureId);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder consumer = tess.getBuffer();

        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        consumer.vertex(mat, x, y1, 0f).texture(u0, v1).next();
        consumer.vertex(mat, x1, y1, 0f).texture(u1, v1).next();
        consumer.vertex(mat, x1, y, 0f).texture(u1, v0).next();
        consumer.vertex(mat, x, y, 0f).texture(u0, v0).next();

        tess.draw();
    }
}
