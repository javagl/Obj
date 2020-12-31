/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.obj;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An in-memory representation of an MTL file. The data represented by this 
 * interface is:
 * <br>
 * <ul>
 *   <li><b>Ka</b> - Ambient component</li>
 *   <li><b>Ks</b> - Specular component</li>
 *   <li><b>Kd</b> - Diffuse component</li>
 *   <li><b>map_Kd</b> - Diffuse map</li>
 *   <li><b>Ns</b> - Shininess</li>
 *   <li><b>D</b> - Opacity</li>
 * </ul>
 * 
 */
public interface Mtl
{
    /**
     * Return the name of the material
     * 
     * @return The name of the material
     */
    String getName();

    /**
     * Returns the ambient component of the material
     * 
     * @return The ambient component of the material
     */
    FloatTuple getKa();

    /**
     * Set the ambient part of this material
     * 
     * @param ka0 The ambient component 0
     * @param ka1 The ambient component 1
     * @param ka2 The ambient component 2
     */
    void setKa(float ka0, float ka1, float ka2);
    
    /**
     * Returns the specular component of the material
     * 
     * @return The specular component of the material
     */
    FloatTuple getKs();

    /**
     * Set the specular part of this material
     * 
     * @param ks0 The specular component 0
     * @param ks1 The specular component 1
     * @param ks2 The specular component 2
     */
    void setKs(float ks0, float ks1, float ks2);
    
    /**
     * Returns the diffuse component of the material
     * 
     * @return The diffuse component of the material
     */
    FloatTuple getKd();

    /**
     * Set the diffuse part of this material
     * 
     * @param kd0 The diffuse component 0
     * @param kd1 The diffuse component 1
     * @param kd2 The diffuse component 2
     */
    void setKd(float kd0, float kd1, float kd2);
    
    /**
     * Returns the diffuse map options of the material, or null if it has no map.
     * 
     * @return The diffuse texture map.
     */
    @Nullable
    TextureOptions getMapKd();

    /**
     * Set the diffuse map options of this material
     * 
     * @param mapKd The diffuse map options of this material
     */
    void setMapKd(@Nullable TextureOptions mapKd);

    /**
     * Get the ambient map options of this material, or null if it has no map.
     *
     * @return The ambient texture map.
     */
    @Nullable
    TextureOptions getMapKa();

    /**
     * Sets the ambient map options of this material.
     *
     * @param mapKa The ambient map options of this material.
     */
    void setMapKa(@Nullable TextureOptions mapKa);

    /**
     * Gets the specular map options of this material, or null if it has no map.
     *
     * @return The specular texture map.
     */
    @Nullable
    TextureOptions getMapKs();

    /**
     * Sets the specular map options of this material.
     *
     * @param mapKs The specular map options of this material.
     */
    void setMapKs(@Nullable TextureOptions mapKs);

    /**
     * Gets the specular highlight component options of this material, or null if it has no map.
     *
     * @return The specular highlight texture map.
     */
    @Nullable
    TextureOptions getMapNs();

    /**
     * Sets the specular highlight component options of this material.
     *
     * @param mapNs The specular highlight options of this material.
     */
    void setMapNs(@Nullable TextureOptions mapNs);

    /**
     * Gets the alpha texture map options of this material, or null if it has no map.
     *
     * @return The alpha texture map options of this material.
     */
    @Nullable
    TextureOptions getMapD();

    /**
     * Sets the alpha texture map options of this material.
     *
     * @param mapD The alpha texture map options of this material.
     */
    void setMapD(@Nullable TextureOptions mapD);

    /**
     * Gets the bump map texture options of this material, or null if it has no map.
     *
     * @return The bump map texture map options of this material.
     */
    @Nullable
    TextureOptions getBumpMap();

    /**
     * Sets the bump map texture options of this material.
     *
     * @param bumpMap The bump map texture options.
     */
    void setBumpMap(@Nullable TextureOptions bumpMap);

    /**
     * Gets the displacement map texture options of this material, or null if it has no map.
     *
     * @return The displacement map of this material.
     */
    @Nullable
    TextureOptions getDisplacementMap();

    /**
     * Sets the displacement map texture options of this material.
     *
     * @param displacementMap The new displacement map options.
     */
    void setDisplacementMap(@Nullable TextureOptions displacementMap);

    /**
     * Gets the stencil decal map texture options of this material, or null if it has no map.
     *
     * @return The decal map of this material.
     */
    @Nullable
    TextureOptions getDecalMap();

    /**
     * Sets the stencil decal map texture options of this material.
     *
     * @param decalMap Th new decal map options.
     */
    void setDecalMap(TextureOptions decalMap);
    
    /**
     * Returns the shininess of the material.
     * 
     * @return The shininess of the material.
     */
    float getNs();

    /**
     * Set the shininess of this material
     * 
     * @param ns The shininess of this material
     */
    void setNs(float ns);
    
    /**
     * Returns the opacity of the material
     * 
     * @return The opacity of the material.
     */
    float getD();
    
    /**
     * Set the opacity of the material
     * 
     * @param d The opacity of the material
     */
    void setD(float d);

    /**
     * Returns the illumination mode of the material
     *
     * @return The illumination mode of the material.
     */
    @NotNull
    IlluminationMode getIlluminationMode();

    /**
     * Set the illumination mode of the material
     *
     * @param illum The illumination mode of the material.
     */
    void setIlluminationMode(@NotNull IlluminationMode illum);

    enum IlluminationMode {
        COLOR_ON_AMBIENT_OFF,
        COLOR_ON_AMBIENT_ON,
        HIGHLIGHT_ON,
        REFLECTION_ON_RAY_TRACE_ON,
        TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_ON,
        REFLECTION_FRESNEL_ON_RAY_TRACE_ON,
        TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_OFF_RAY_TRACE_ON,
        TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_ON_RAY_TRACE_ON,
        REFLECTION_ON_RAY_TRACE_OFF,
        TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_OFF,
        SHADOW_ON_INVISIBLE_SURFACES;

        public int getIntValue() {
            switch (this) {
                case COLOR_ON_AMBIENT_OFF:
                    return 0;
                case COLOR_ON_AMBIENT_ON:
                    return 1;
                case HIGHLIGHT_ON:
                    return 2;
                case REFLECTION_ON_RAY_TRACE_ON:
                    return 3;
                case TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_ON:
                    return 4;
                case REFLECTION_FRESNEL_ON_RAY_TRACE_ON:
                    return 5;
                case TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_OFF_RAY_TRACE_ON:
                    return 6;
                case TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_ON_RAY_TRACE_ON:
                    return 7;
                case REFLECTION_ON_RAY_TRACE_OFF:
                    return 8;
                case TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_OFF:
                    return 9;
                case SHADOW_ON_INVISIBLE_SURFACES:
                    return 10;
                default:
                    throw new IllegalStateException("Illumination mode not recognized");
            }
        }

        static IlluminationMode fromIntValue(int illum) {
            switch (illum) {
                case 0:
                    return COLOR_ON_AMBIENT_OFF;
                case 1:
                    return COLOR_ON_AMBIENT_ON;
                case 2:
                    return HIGHLIGHT_ON;
                case 3:
                    return REFLECTION_ON_RAY_TRACE_ON;
                case 4:
                    return TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_ON;
                case 5:
                    return REFLECTION_FRESNEL_ON_RAY_TRACE_ON;
                case 6:
                    return TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_OFF_RAY_TRACE_ON;
                case 7:
                    return TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_ON_RAY_TRACE_ON;
                case 8:
                    return REFLECTION_ON_RAY_TRACE_OFF;
                case 9:
                    return TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_OFF;
                case 10:
                    return SHADOW_ON_INVISIBLE_SURFACES;
                default:
                    throw new IllegalArgumentException("Unknown illumination mode: " + illum);
            }
        }
    }
    
}
