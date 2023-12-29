package com.kneelawk.impl;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import com.mojang.serialization.JsonOps;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import com.kneelawk.api.atlas.Atlas;
import com.kneelawk.api.atlas.BakedAtlas;

import static com.kneelawk.impl.GAConstants.id;

public class AtlasResourceManager implements SimpleResourceReloadListener<Map<Identifier, BakedAtlas>> {
    public static final Identifier ID = id("atlas_resource");
    public static final AtlasResourceManager INSTANCE = new AtlasResourceManager();

    private static @Nullable Map<Identifier, BakedAtlas> ATLAS_MAP = null;

    private AtlasResourceManager() {
    }

    private static Map<Identifier, BakedAtlas> getAtlasMap() {
        if (ATLAS_MAP == null)
            throw new IllegalStateException("Attempted to get the atlas map before resources have been loaded.");
        return ATLAS_MAP;
    }

    public static @Nullable BakedAtlas getAtlas(Identifier atlasId) {
        return getAtlasMap().get(atlasId);
    }

    @Override
    public CompletableFuture<Map<Identifier, BakedAtlas>> load(ResourceManager manager, Profiler profiler,
                                                               Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Identifier, BakedAtlas> atlasMap = new Object2ObjectLinkedOpenHashMap<>();

            Map<Identifier, Resource> resources = manager.findResources("textures/", id -> {
                String path = id.getPath();
                return path.endsWith(".atlas.json") || path.endsWith(".json.atlas");
            });

            for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
                Identifier id = entry.getKey();
                try (BufferedReader reader = entry.getValue().getReader()) {
                    Atlas atlas = Atlas.DISPATCH_CODEC.decode(JsonOps.INSTANCE, JsonHelper.deserialize(reader))
                        .getOrThrow(false, GALog.LOG::error).getFirst();

                    BakedAtlas baked = atlas.bake(id);

                    atlasMap.put(id, baked);
                } catch (Exception e) {
                    GALog.LOG.error("Error loading atlas {}", id);
                }
            }

            return atlasMap;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, BakedAtlas> data, ResourceManager manager, Profiler profiler,
                                         Executor executor) {
        return CompletableFuture.runAsync(() -> {
            // is executed on-thread, so static variables are safe to mutate
            ATLAS_MAP = data;
        }, executor);
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }
}
