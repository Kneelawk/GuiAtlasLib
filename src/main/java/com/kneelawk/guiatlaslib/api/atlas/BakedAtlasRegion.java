package com.kneelawk.guiatlaslib.api.atlas;

import net.minecraft.client.gui.DrawContext;

public interface BakedAtlasRegion {
    void render(DrawContext ctx, int x, int y, int width, int height);
}
