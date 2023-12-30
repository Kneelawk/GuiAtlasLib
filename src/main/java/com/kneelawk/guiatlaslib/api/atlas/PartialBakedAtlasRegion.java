package com.kneelawk.guiatlaslib.api.atlas;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

/**
 * A region that renders part of its texture or repeats if rendered at a different size.
 */
public class PartialBakedAtlasRegion implements BakedAtlasRegion {
    private final Identifier textureId;
    private final float u0, u1, v0, v1;
    private final float ul, vl;
    private final float rw, rh;
    private final Justification justification;

    /**
     * Constructs a new partial baked atlas region.
     *
     * @param textureId     the id of the texture to render from.
     * @param textureWidth  the width of the whole atlas texture.
     * @param textureHeight the height of the whole atlas texture.
     * @param u             the x position of this region.
     * @param v             the y position of this region.
     * @param width         the width of this region.
     * @param height        the height of this region.
     * @param renderWidth   the width this region expects to be rendered at.
     * @param renderHeight  the height this region expects to be rendered at.
     * @param justification the justification of this region if too little or too much is rendered.
     */
    public PartialBakedAtlasRegion(Identifier textureId, int textureWidth, int textureHeight, int u, int v, int width,
                                   int height, int renderWidth, int renderHeight, Justification justification) {
        this.textureId = textureId;

        u0 = (float) u / (float) textureWidth;
        v0 = (float) v / (float) textureHeight;
        u1 = (float) (u + width) / (float) textureWidth;
        v1 = (float) (v + height) / (float) textureHeight;
        ul = (float) width / (float) textureWidth;
        vl = (float) height / (float) textureHeight;
        rw = (float) renderWidth;
        rh = (float) renderHeight;

        this.justification = justification;
    }

    @Override
    public void render(DrawContext ctx, float x, float y, float width, float height) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, textureId);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        renderStrips(ctx.getMatrices().peek().getPositionMatrix(), bufferBuilder, x, y, width, height);
    }

    private void renderStrips(Matrix4f mat, VertexConsumer consumer, float x0, float y0, float width, float height) {
        int stripCount = (int) (height / rh);
        float remainder = height % rh;
        float repeatedOffset = justification.getVerticalOffset() * remainder;
        float finalOffset = justification.getVerticalFinalOffset() * rw * stripCount;
        for (int i = 0; i < stripCount; i++) {
            renderHorizontalStrip(mat, consumer, x0, y0 + rh * i + repeatedOffset, width, rh);
        }
        renderHorizontalStrip(mat, consumer, x0, y0 + finalOffset, width, remainder);
    }

    private void renderHorizontalStrip(Matrix4f mat, VertexConsumer consumer, float x0, float y0, float width,
                                       float height) {
        int patchCount = (int) (width / rw);
        float remainder = width % rw;
        float repeatedOffset = justification.getHorizontalOffset() * remainder;
        float finalOffset = justification.getHorizontalFinalOffset() * rw * patchCount;
        for (int i = 0; i < patchCount; i++) {
            renderPatch(mat, consumer, x0 + rw * i + repeatedOffset, y0, rw, height);
        }
        renderPatch(mat, consumer, x0 + finalOffset, y0, remainder, height);
    }

    private void renderPatch(Matrix4f mat, VertexConsumer consumer, float x0, float y0, float w, float h) {
        float x1 = x0 + w;
        float y1 = y0 + h;

        float uMin = justification.getUMin(w, u0, u1, ul, rw);
        float vMin = justification.getVMin(h, v0, v1, vl, rh);
        float uMax = justification.getUMax(w, u0, u1, ul, rw);
        float vMax = justification.getVMax(h, v0, v1, vl, rh);

        consumer.vertex(mat, x0, y1, 0f).texture(uMin, vMax).next();
        consumer.vertex(mat, x1, y1, 0f).texture(uMax, vMax).next();
        consumer.vertex(mat, x1, y0, 0f).texture(uMax, vMin).next();
        consumer.vertex(mat, x0, y0, 0f).texture(uMin, vMin).next();
    }
}
