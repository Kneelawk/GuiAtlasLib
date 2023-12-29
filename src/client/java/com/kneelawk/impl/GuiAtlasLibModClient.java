package com.kneelawk.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;

import com.kneelawk.api.GuiAtlasLib;
import com.kneelawk.api.atlas.NinePatchAtlasRegion;
import com.kneelawk.api.atlas.SimpleAtlas;
import com.kneelawk.api.atlas.SimpleAtlasRegion;

import static com.kneelawk.impl.GAConstants.id;

public class GuiAtlasLibModClient implements ClientModInitializer {
    @Override
    @SuppressWarnings("unchecked")
    public void onInitializeClient() {
        GALog.LOG.info("Initializing GuiAtlasLib...");

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(AtlasResourceManager.INSTANCE);

        Registry.register((Registry<? super Registry<?>>) Registries.REGISTRIES, GuiAtlasLib.ATLAS.getKey().getValue(),
            GuiAtlasLib.ATLAS);
        Registry.register((Registry<? super Registry<?>>) Registries.REGISTRIES,
            GuiAtlasLib.ATLAS_REGION.getKey().getValue(), GuiAtlasLib.ATLAS_REGION);

        Registry.register(GuiAtlasLib.ATLAS, id("simple"), SimpleAtlas.CODEC);
        Registry.register(GuiAtlasLib.ATLAS_REGION, id("simple"), SimpleAtlasRegion.CODEC);
        Registry.register(GuiAtlasLib.ATLAS_REGION, id("ninepatch"), NinePatchAtlasRegion.CODEC);

        GALog.LOG.info("GuiAtlasLib Initialized.");
    }
}
