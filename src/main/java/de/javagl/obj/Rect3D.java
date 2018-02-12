package de.javagl.obj;

import java.util.Objects;

public class Rect3D {

    final float xMin;
    final float xMax;
    final float yMin;
    final float yMax;
    final float zMin;
    final float zMax;

    private float mWidthX = Float.NaN;
    private float mWidthY = Float.NaN;
    private float mWidthZ = Float.NaN;

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
