package com.kneelawk.guiatlaslib.api.atlas;

import net.minecraft.client.gui.DrawContext;

public interface BakedAtlas {
    void render(DrawContext ctx, String region, int x, int y, int width, int height);
}
