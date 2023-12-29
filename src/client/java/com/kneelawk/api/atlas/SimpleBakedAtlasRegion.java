package com.kneelawk.api.atlas;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class SimpleBakedAtlasRegion implements BakedAtlasRegion {
    private final Identifier textureId;

    private final float u0;
    private final float u1;
    private final float v0;
    private final float v1;

    SimpleBakedAtlasRegion(Identifier textureId, int textureWidth, int textureHeight, int u, int v, int width,
                           int height) {
        this.textureId = textureId;

        this.u0 = (float) u / (float) textureWidth;
        this.u1 = (float) (u + width) / (float) textureWidth;
        this.v0 = (float) v / (float) textureHeight;
        this.v1 = (float) (v + height) / (float) textureHeight;
    }

    @Override
    public void render(DrawContext ctx, int x, int y, int width, int height) {
        Matrix4f mat = ctx.getMatrices().peek().getPositionMatrix();
        int x1 = x + width;
        int y1 = y + height;

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
