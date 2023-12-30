package com.kneelawk.guiatlaslib.api.atlas;

import net.minecraft.util.StringIdentifiable;

/**
 * Justification for a partial region.
 */
public enum Justification implements StringIdentifiable {
    /**
     * The render is aligned with the top left of the region.
     */
    TOP_LEFT("topLeft", 0f, 1f, 0f, 1f),
    /**
     * The render is aligned with the top right of the region.
     */
    TOP_RIGHT("topRight", 1f, 0f, 0f, 1f),
    /**
     * The render is aligned with the bottom left of the region.
     */
    BOTTOM_LEFT("bottomLeft", 0f, 1f, 1f, 0f),
    /**
     * The render is aligned with the bottom right of the region.
     */
    BOTTOM_RIGHT("bottomRight", 1f, 0f, 1f, 0f);

    private final String identifier;
    private final float horizontalOffset;
    private final float horizontalFinalOffset;
    private final float verticalOffset;
    private final float verticalFinalOffset;

    Justification(String identifier, float horizontalOffset, float horizontalFinalOffset, float verticalOffset,
                  float verticalFinalOffset) {
        this.identifier = identifier;
        this.horizontalOffset = horizontalOffset;
        this.horizontalFinalOffset = horizontalFinalOffset;
        this.verticalOffset = verticalOffset;
        this.verticalFinalOffset = verticalFinalOffset;
    }

    @Override
    public String asString() {
        return identifier;
    }

    /**
     * Gets the minimum texture x position for this justification.
     *
     * @param w  the width of the patch being rendered.
     * @param u0 the min x of the region being rendered from.
     * @param u1 the max x of the region being rendered from.
     * @param ul the width of the region being rendered from.
     * @param rw the width of the render space that would cover one whole region.
     * @return the minimum texture x position.
     */
    public float getUMin(float w, float u0, float u1, float ul, float rw) {
        return switch (this) {
            case TOP_LEFT, BOTTOM_LEFT -> u0;
            case TOP_RIGHT, BOTTOM_RIGHT -> u1 - w / rw * ul;
        };
    }

    /**
     * Gets the minimum texture y position for this justification.
     *
     * @param h  the height of the patch being rendered.
     * @param v0 the min y of the region being rendered from.
     * @param v1 the max y of the region being rendered from.
     * @param vl the height of the region being rendered from.
     * @param rh the height of the render space that would cover one whole region.
     * @return the minimum texture y position.
     */
    public float getVMin(float h, float v0, float v1, float vl, float rh) {
        return switch (this) {
            case TOP_LEFT, TOP_RIGHT -> v0;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> v1 - h / rh * vl;
        };
    }

    /**
     * Gets the maximum texture x position for this justification.
     *
     * @param w  the width of the patch being rendered.
     * @param u0 the min x of the region being rendered from.
     * @param u1 the max x of the region being rendered from.
     * @param ul the width of the region being rendered from.
     * @param rw the width of the render space that would cover one whole region.
     * @return the maximum texture x position.
     */
    public float getUMax(float w, float u0, float u1, float ul, float rw) {
        return switch (this) {
            case TOP_LEFT, BOTTOM_LEFT -> u0 + w / rw * ul;
            case TOP_RIGHT, BOTTOM_RIGHT -> u1;
        };
    }

    /**
     * Gets the maximum texture y position for this justification.
     *
     * @param h  the height of the patch being rendered.
     * @param v0 the min y of the region being rendered from.
     * @param v1 the max y of the region being rendered from.
     * @param vl the height of the region being rendered from.
     * @param rh the height of the render space that would cover one whole region.
     * @return the maximum texture y position.
     */
    public float getVMax(float h, float v0, float v1, float vl, float rh) {
        return switch (this) {
            case TOP_LEFT, TOP_RIGHT -> v0 + h / rh * vl;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> v1;
        };
    }

    /**
     * Gets the offset factor for the repeated portion of a horizontal strip.
     *
     * @return the repeated horizontal offset factor.
     */
    public float getHorizontalOffset() {
        return horizontalOffset;
    }

    /**
     * Gets the offset factor for the final portion of a horizontal strip.
     *
     * @return the final horizontal offset factor.
     */
    public float getHorizontalFinalOffset() {
        return horizontalFinalOffset;
    }

    /**
     * Gets the offset factor for the repeated portion of a vertical strip.
     *
     * @return the repeated vertical offset factor.
     */
    public float getVerticalOffset() {
        return verticalOffset;
    }

    /**
     * Gets the offset factor for the final portion of a vertical strip.
     *
     * @return the final vertical offset factor.
     */
    public float getVerticalFinalOffset() {
        return verticalFinalOffset;
    }
}
