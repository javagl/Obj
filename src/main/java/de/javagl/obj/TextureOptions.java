package de.javagl.obj;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TextureOptions {

    private final String mFileName;
    private final boolean mIsBlenduEnabled;
    private final boolean mIsBlendvEnabled;
    private final float mBoost;
    private final float mMmBrightness;
    private final float mMmContrast;
    private final FloatTuple mOriginOffset;
    private final FloatTuple mScale;
    private final FloatTuple mTurbulence;
    private final float mTextureResolution;
    private final boolean mIsClampEnabled;
    private final float mBumpMultiplier;
    private final ImfChannel mImfChannel;
    private final Type mType;

    public TextureOptions(@NotNull String fileName, boolean isBlenduEnabled, boolean isBlendvEnabled, float boost,
            float mmBrightness, float mmContrast, @NotNull FloatTuple originOffset, @NotNull FloatTuple scale,
            @NotNull FloatTuple turbulence, float textureResolution, boolean isClampEnabled, float bumpMultiplier,
            @Nullable ImfChannel imfChannel, @Nullable Type type) {
        mFileName = fileName;
        mIsBlenduEnabled = isBlenduEnabled;
        mIsBlendvEnabled = isBlendvEnabled;
        mBoost = boost;
        mMmBrightness = mmBrightness;
        mMmContrast = mmContrast;
        mOriginOffset = originOffset;
        mScale = scale;
        mTurbulence = turbulence;
        mTextureResolution = textureResolution;
        mIsClampEnabled = isClampEnabled;
        mBumpMultiplier = bumpMultiplier;
        mImfChannel = imfChannel;
        mType = type;
    }

    /**
     * Gets the texture's file name.
     *
     * @return The texture's file name.
     */
    @NotNull
    public String getFileName() {
        return mFileName;
    }

    /**
     * Gets the horizontal texture blending state.
     *
     * @return Whether or not blendu is on.
     */
    public boolean isBlenduEnabled() {
        return mIsBlenduEnabled;
    }

    /**
     * Gets the vertival texture blending state.
     *
     * @return Whether or not blendv is on.
     */
    public boolean isBlendvEnabled() {
        return mIsBlendvEnabled;
    }

    /**
     * Gets the boost mip-map sharpness value.
     *
     * @return The boost mip-map sharpness.
     */
    public float getBoost() {
        return mBoost;
    }

    /**
     * Gets the modified texture map brightness value.
     *
     * @return The modified texture map brightness.
     */
    public float getMmBrightness() {
        return mMmBrightness;
    }

    /**
     * Gets the modified texture map contrast value
     *
     * @return The modified texture map contrast.
     */
    public float getMmContrast() {
        return mMmContrast;
    }

    /**
     * Gets the origin offset.
     *
     * @return The origin offset.
     */
    @NotNull
    public FloatTuple getOriginOffset() {
        return mOriginOffset;
    }

    /**
     * Gets the scale.
     *
     * @return The scale.
     */
    @NotNull
    public FloatTuple getScale() {
        return mScale;
    }

    /**
     * Gets the turbulence.
     *
     * @return The turbulence.
     */
    @NotNull
    public FloatTuple getTurbulence() {
        return mTurbulence;
    }

    /**
     * Gets the texture resolution.
     *
     * @return The texture resolution.
     */
    public float getTexRes() {
        return mTextureResolution;
    }

    /**
     * Gets the clamping state.
     *
     * @return Whether or not clamping is enabled.
     */
    public boolean isClampEnabled() {
        return mIsClampEnabled;
    }

    /**
     * Gets the bump multiplier (only valid for bump maps).
     *
     * @return The bump multiplier.
     */
    public float getBumpMultiplier() {
        return mBumpMultiplier;
    }

    /**
     * Gets the imf channel to use.
     *
     * @return The imf channel to use.
     */
    @Nullable
    public ImfChannel getImfChannel() {
        return mImfChannel;
    }

    /**
     * Gets the type of texture map (used for reflection maps only)
     *
     * @return The type of texture map
     */
    @Nullable
    public Type getType() {
        return mType;
    }

    @Override
    public String toString() {
        return new StringBuilder("TextureOptions[")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(mFileName, mIsBlenduEnabled, mIsBlendvEnabled, mBoost, mMmBrightness, mMmContrast,
                mOriginOffset, mScale, mTurbulence, mTextureResolution, mIsClampEnabled, mBumpMultiplier, mImfChannel,
                mType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextureOptions)) {
            return false;
        }
        TextureOptions other = (TextureOptions) obj;
        return Objects.equals(mFileName, other.mFileName) &&
                Objects.equals(mIsBlenduEnabled, other.mIsBlenduEnabled) &&
                Objects.equals(mIsBlendvEnabled, other.mIsBlendvEnabled) &&
                Objects.equals(mBoost, other.mBoost) &&
                Objects.equals(mMmBrightness, other.mMmBrightness) &&
                Objects.equals(mMmContrast, other.mMmContrast) &&
                Objects.equals(mOriginOffset, other.mOriginOffset) &&
                Objects.equals(mScale, other.mScale) &&
                Objects.equals(mTurbulence, other.mTurbulence) &&
                Objects.equals(mTextureResolution, other.mTextureResolution) &&
                Objects.equals(mIsClampEnabled, other.mIsClampEnabled) &&
                Objects.equals(mBumpMultiplier, other.mBumpMultiplier) &&
                Objects.equals(mImfChannel, other.mImfChannel) &&
                Objects.equals(mType, other.mType);
    }

    /**
     * The various imf channels available
     */
    enum ImfChannel {
        RED, GREEN, BLUE, MATTE, LUMINANCE, Z_DEPTH;

        public String getStringValue() {
            switch (this) {
                case RED:
                    return "r";
                case GREEN:
                    return "g";
                case BLUE:
                    return "b";
                case MATTE:
                    return "m";
                case LUMINANCE:
                    return "l";
                case Z_DEPTH:
                    return "z";
                default:
                    throw new IllegalStateException("Invalid imfchannel");
            }
        }

        public static ImfChannel fromStringValue(String value) {
            switch (value.trim().toLowerCase()) {
                case "r":
                    return RED;
                case "g":
                    return GREEN;
                case "b":
                    return BLUE;
                case "m":
                    return MATTE;
                case "l":
                    return LUMINANCE;
                case "z":
                    return Z_DEPTH;
                default:
                    throw new IllegalArgumentException("Invalid imgchan: " + value);
            }
        }
    }

    /**
     * The different types of maps available (used for reflection maps only)
     */
    enum Type {
        SPHERE, CUBE_TOP, CUBE_BOTTOM, CUBE_FRONT, CUBE_BACK, CUBE_LEFT, CUBE_RIGHT;

        public String getStringValue() {
            switch (this) {
                case SPHERE:
                    return "sphere";
                case CUBE_TOP:
                    return "cube_top";
                case CUBE_BOTTOM:
                    return "cube_bottom";
                case CUBE_FRONT:
                    return "cube_front";
                case CUBE_BACK:
                    return "cube_back";
                case CUBE_LEFT:
                    return "cube_left";
                case CUBE_RIGHT:
                    return "cube_right";
                default:
                    throw new IllegalStateException("Invalid type");
            }
        }

        public static Type fromStringValue(String value) {
            switch (value.trim().toLowerCase()) {
                case "sphere":
                    return SPHERE;
                case "cube_top":
                    return CUBE_TOP;
                case "cube_bottom":
                    return CUBE_BOTTOM;
                case "cube_front":
                    return CUBE_FRONT;
                case "cube_back":
                    return CUBE_BACK;
                case "cube_left":
                    return CUBE_LEFT;
                case "cube_right":
                    return CUBE_RIGHT;
                default:
                    throw new IllegalArgumentException("Invalid type: " + value);
            }
        }
    }

    public static class Builder {

        private String mFileName = null;
        private boolean mIsBlenduEnabled = true;
        private boolean mIsBlendvEnabled = true;
        private float mBoost = 0f;
        private float mMmBrightness = 0f;
        private float mMmContrast = 1f;
        private DefaultFloatTuple mOriginOffset = new DefaultFloatTuple(0f, 0f, 0f);
        private DefaultFloatTuple mScale = new DefaultFloatTuple(1f, 1f, 1f);
        private DefaultFloatTuple mTurbulence = new DefaultFloatTuple(0f, 0f, 0f);
        private float mTextureResolution = 0f;
        private boolean mIsClampEnabled = false;
        private float mBumpMultiplier = 0f;
        private ImfChannel mImfChannel = null;
        private Type mType = null;

        public Builder() {
        }

        public Builder(TextureOptions base) {
            mFileName = base.mFileName;
            mIsBlenduEnabled = base.mIsBlenduEnabled;
            mIsBlendvEnabled = base.mIsBlendvEnabled;
            mBoost = base.mBoost;
            mMmBrightness = base.mMmBrightness;
            mMmContrast = base.mMmContrast;
            mOriginOffset = new DefaultFloatTuple(base.mOriginOffset);
            mScale = new DefaultFloatTuple(base.mScale);
            mTurbulence = new DefaultFloatTuple(base.mTurbulence);
            mTextureResolution = base.mTextureResolution;
            mIsClampEnabled = base.mIsClampEnabled;
            mBumpMultiplier = base.mBumpMultiplier;
            mImfChannel = base.mImfChannel;
            mType = base.mType;
        }

        @NotNull
        public Builder setFileName(@NotNull String fileName) {
            mFileName = fileName;
            return this;
        }

        @NotNull
        public Builder setIsBlenduEnabled(boolean enabled) {
            mIsBlenduEnabled = enabled;
            return this;
        }

        @NotNull
        public Builder setIsBlendvEnabled(boolean enabled) {
            mIsBlendvEnabled = enabled;
            return this;
        }

        @NotNull
        public Builder setBoost(float boost) {
            mBoost = boost;
            return this;
        }

        @NotNull
        public Builder setModifyMapValues(float brightness, float contrast) {
            mMmBrightness = brightness;
            mMmContrast = contrast;
            return this;
        }

        @NotNull
        public Builder setOriginOffset(float u, float v, float w) {
            mOriginOffset.setX(u);
            mOriginOffset.setY(v);
            mOriginOffset.setZ(w);
            return this;
        }

        @NotNull
        public Builder setScale(float u, float v, float w) {
            mScale.setX(u);
            mScale.setY(v);
            mScale.setZ(w);
            return this;
        }

        @NotNull
        public Builder setTurbulence(float u, float v, float w) {
            mTurbulence.setX(u);
            mTurbulence.setY(v);
            mTurbulence.setZ(w);
            return this;
        }

        @NotNull
        public Builder setTextureResolution(float textureResolution) {
            mTextureResolution = textureResolution;
            return this;
        }

        @NotNull
        public Builder setIsClampEnabled(boolean enabled) {
            mIsClampEnabled = enabled;
            return this;
        }

        @NotNull
        public Builder setBumpMultiplier(float bumpMultiplier) {
            mBumpMultiplier = bumpMultiplier;
            return this;
        }

        @NotNull
        public Builder setImfChannel(@Nullable ImfChannel imfChannel) {
            mImfChannel = imfChannel;
            return this;
        }

        @NotNull
        public Builder setType(@Nullable Type type) {
            mType = type;
            return this;
        }

        @NotNull
        public TextureOptions build() throws IllegalStateException {
            if (mFileName == null || mFileName.isEmpty()) {
                throw new IllegalStateException("Texture file name must be set");
            }
            return new TextureOptions(mFileName, mIsBlenduEnabled, mIsBlendvEnabled, mBoost, mMmBrightness, mMmContrast,
                    mOriginOffset, mScale, mTurbulence, mTextureResolution, mIsClampEnabled, mBumpMultiplier,
                    mImfChannel, mType);
        }
    }
}
