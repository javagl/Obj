package de.javagl.obj;

import java.util.Objects;

public class Rect3D {

    public final float xMin;
    public final float xMax;
    public final float yMin;
    public final float yMax;
    public final float zMin;
    public final float zMax;

    private float mWidthX = Float.NaN;
    private float mWidthY = Float.NaN;
    private float mWidthZ = Float.NaN;

    private float mCenterX = Float.NaN;
    private float mCenterY = Float.NaN;
    private float mCenterZ = Float.NaN;

    private float mDiagonalLength = Float.NaN;

    public Rect3D(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    public float getWidthX() {
        if (mWidthX == Float.NaN) {
            mWidthX = xMax - xMin;
        }
        return mWidthX;
    }

    public float getWidthY() {
        if (mWidthY == Float.NaN) {
            mWidthY = yMax - yMin;
        }
        return mWidthY;
    }

    public float getWidthZ() {
        if (mWidthZ == Float.NaN) {
            mWidthZ = zMax - zMin;
        }
        return mWidthZ;
    }

    public float getCenterX() {
        if (mCenterX == Float.NaN) {
            mCenterX = xMin + getWidthX() / 2f;
        }
        return mCenterX;
    }

    public float getCenterY() {
        if (mCenterY == Float.NaN) {
            mCenterY = yMin + getWidthY() / 2f;
        }
        return mCenterY;
    }

    public float getCenterZ() {
        if (mCenterZ == Float.NaN) {
            mCenterZ = zMin + getWidthZ() / 2f;
        }
        return mCenterZ;
    }

    public float getDiagonalLength() {
        if (mDiagonalLength == Float.NaN) {
            mDiagonalLength = (float) Math.sqrt(
                    getWidthX() * getWidthX() + getWidthY() * getWidthY() + getWidthZ() * getWidthZ());
        }
        return mDiagonalLength;
    }

    public boolean contains(float x, float y, float z) {
        return xMin <= x && x <= xMax &&
                yMin <= y && y <= yMax &&
                zMin <= z && z <= zMax;
    }

    public boolean contains(FloatTuple tuple) {
        if (tuple.getDimensions() != 3) {
            return false;
        }
        return contains(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Rect3D add(float x, float y, float z) {
        if (contains(x, y, z)) {
            return this;
        }
        return new Rect3D(Math.min(xMin, x), Math.max(xMax, x),
                Math.min(yMin, y), Math.max(yMax, y),
                Math.min(zMin, z), Math.max(zMax, z));
    }

    public Rect3D add(FloatTuple tuple) {
        if (tuple.getDimensions() != 3 || contains(tuple)) {
            return this;
        }
        return add(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Rect3D add(Rect3D other) {
        if (contains(other.xMin, other.yMin, other.zMin) &&
                contains(other.xMin, other.yMin, other.zMax) &&
                contains(other.xMin, other.yMax, other.zMin) &&
                contains(other.xMin, other.yMax, other.zMax) &&
                contains(other.xMax, other.yMin, other.zMin) &&
                contains(other.xMax, other.yMin, other.zMax) &&
                contains(other.xMax, other.yMax, other.zMin) &&
                contains(other.xMax, other.yMax, other.zMax)) {
            return this;
        }
        return new Rect3D(Math.min(xMin, other.xMin), Math.max(xMax, other.xMax),
                Math.min(yMin, other.yMin), Math.max(yMax, other.yMax),
                Math.min(zMin, other.zMin), Math.max(zMax, other.zMax));
    }

    @Override
    public int hashCode() {
        return Objects.hash(xMin, xMax, yMin, yMax, zMin, zMax);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rect3D)) {
            return false;
        }

        Rect3D other = (Rect3D) obj;
        return xMin == other.xMin &&
                xMax == other.xMax &&
                yMin == other.yMin &&
                yMax == other.yMax &&
                zMin == other.zMin &&
                zMax == other.zMax;
    }
}
