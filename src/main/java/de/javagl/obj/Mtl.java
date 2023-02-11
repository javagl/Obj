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

import java.util.List;

/**
 * An in-memory representation of an MTL file. For details about the
 * semantics of the properties in this interface, refer to the MTL
 * specification.
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
     * Returns the illumination mode (<code>-illum</code>) of the material,
     * or <code>null</code> if it was was not specified
     *
     * @return The illumination mode of the material.
     */
    Integer getIllum();

    /**
     * Set the illumination mode of the material
     *
     * @param illum The illumination mode of the material.
     */
    void setIllum(Integer illum);

    /**
     * Returns the optical density, also known as index of refraction, of
     * the material, or <code>null</code> if it was not specified
     *
     * @return The optical density
     */
    Float getNi();

    /**
     * Set the optical density of the material
     *
     * @param ni The optical density
     */
    void setNi(Float ni);

    /**
     * Returns the transmission filter of the material,
     * or <code>null</code> if it was was not specified
     *
     * @return The transmission filter
     */
    FloatTuple getTf();

    /**
     * Set the transmission filter of this material
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     */
    void setTf(Float r, Float g, Float b);

    /**
     * Returns the sharpness of reflections from the reflection map,
     * or <code>null</code> if it was was not specified
     *
     * @return The sharpness
     */
    Float getSharpness();

    /**
     * Set the sharpness of reflections
     *
     * @param sharpness The sharpness
     */
    void setSharpness(Float sharpness);


    //--------------------------------------------------------------------------
    // Ambient

    /**
     * Returns the ambient component of the material,
     * or <code>null</code> if it was not specified
     *
     * @return The ambient component of the material
     */
    FloatTuple getKa();

    /**
     * Set the ambient part of this material
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     */
    void setKa(Float r, Float g, Float b);

    /**
     * Returns the name of the ambient map of the material,
     * or <code>null</code> if it has no such map.
     *
     * @return The name of the ambient map of the material
     */
    String getMapKa();

    /**
     * Set the ambient map name of this material
     *
     * @param mapKa The ambient map name of this material
     */
    void setMapKa(String mapKa);

    /**
     * Returns the ambient map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The ambient map {@link TextureOptions}
     */
    TextureOptions getMapKaOptions();

    /**
     * Set the ambient map {@link TextureOptions}
     *
     * @param options The ambient map {@link TextureOptions}
     */
    void setMapKaOptions(TextureOptions options);




    //--------------------------------------------------------------------------
    // Diffuse

    /**
     * Returns the diffuse component of the material,
     * or <code>null</code> if it was not specified
     *
     * @return The diffuse component of the material
     */
    FloatTuple getKd();

    /**
     * Set the diffuse part of this material
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     */
    void setKd(Float r, Float g, Float b);


    /**
     * Returns the name of the diffuse map of the material,
     * or <code>null</code> if it has no such map.
     *
     * @return The name of the diffuse map of the material
     */
    String getMapKd();

    /**
     * Set the diffuse map name of this material
     *
     * @param mapKd The diffuse map name of this material
     */
    void setMapKd(String mapKd);

    /**
     * Returns the diffuse map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The diffuse map {@link TextureOptions}
     */
    TextureOptions getMapKdOptions();

    /**
     * Set the diffuse map {@link TextureOptions}
     *
     * @param options The diffuse map {@link TextureOptions}
     */
    void setMapKdOptions(TextureOptions options);



    //--------------------------------------------------------------------------
    // Specular reflectivity

    /**
     * Returns the specular component of the material,
     * or <code>null</code> if it was not specified
     *
     * @return The specular component of the material
     */
    FloatTuple getKs();

    /**
     * Set the specular part of this material
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     */
    void setKs(Float r, Float g, Float b);

    /**
     * Returns the name of the specular reflectivity map of the material,
     * or <code>null</code> if it has no such map.
     *
     * @return The name of the specular reflectivity map of the material
     */
    String getMapKs();

    /**
     * Set the specular reflectivity map name of this material
     *
     * @param mapKs The specular reflectivity map name of this material
     */
    void setMapKs(String mapKs);

    /**
     * Returns the specular reflectivity map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The specular reflectivity map {@link TextureOptions}
     */
    TextureOptions getMapKsOptions();

    /**
     * Set the specular reflectivity map {@link TextureOptions}
     *
     * @param options The specular reflectivity map {@link TextureOptions}
     */
    void setMapKsOptions(TextureOptions options);



    //--------------------------------------------------------------------------
    // Specular exponent (shininess)

    /**
     * Returns the shininess of the material.
     *
     * @return The shininess of the material.
     */
    Float getNs();

    /**
     * Set the shininess of this material,
     * or <code>null</code> if it was not specified
     *
     * @param ns The shininess of this material
     */
    void setNs(Float ns);

    /**
     * Returns the name of the shininess map of the material,
     * or <code>null</code> if it has no map.
     *
     * @return The name of the shininess map of the material
     */
    String getMapNs();

    /**
     * Set the shininess map name of this material
     *
     * @param mapNs The shininess map name of this material
     */
    void setMapNs(String mapNs);

    /**
     * Returns the shininess map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The shininess map {@link TextureOptions}
     */
    TextureOptions getMapNsOptions();

    /**
     * Set the shininess map {@link TextureOptions}
     *
     * @param options The shininess map {@link TextureOptions}
     */
    void setMapNsOptions(TextureOptions options);


    //--------------------------------------------------------------------------
    // Opacity

    /**
     * Returns the opacity of the material,
     * or <code>null</code> if it was not specified
     *
     * @return The opacity of the material.
     */
    Float getD();

    /**
     * Set the opacity of the material
     *
     * @param d The opacity of the material
     */
    void setD(Float d);

    /**
     * Returns whether dissolve (opacity) is dependent on the surface
     * orientation relative to the viewer
     *
     * @return The halo flag
     */
    Boolean isHalo();

    /**
     * Set the halo flag
     *
     * @param halo The halo flag
     */
    void setHalo(Boolean halo);

    /**
     * Returns the name of the opacity map of the material,
     * or <code>null</code> if it has no map.
     *
     * @return The name of the opacity map of the material
     */
    String getMapD();

    /**
     * Set the opacity map name of this material
     *
     * @param mapD The opacity map name of this material
     */
    void setMapD(String mapD);

    /**
     * Returns the opacity map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The opacity map {@link TextureOptions}
     */
    TextureOptions getMapDOptions();

    /**
     * Set the opacity map {@link TextureOptions}
     *
     * @param options The opacity map {@link TextureOptions}
     */
    void setMapDOptions(TextureOptions options);


    //--------------------------------------------------------------------------
    // Bump

    /**
     * Returns the name of the bump map of the material,
     * or <code>null</code> if it has no map.
     *
     * @return The name of the bump map of the material
     */
    String getBump();

    /**
     * Set the bump map name of this material
     *
     * @param bump The bump map name of this material
     */
    void setBump(String bump);

    /**
     * Returns the bump map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The bump map {@link TextureOptions}
     */
    TextureOptions getBumpOptions();

    /**
     * Set the bump map {@link TextureOptions}
     *
     * @param options The bump map {@link TextureOptions}
     */
    void setBumpOptions(TextureOptions options);



    //--------------------------------------------------------------------------
    // Disp (displacement)

    /**
     * Returns the name of the displacement map of the material,
     * or <code>null</code> if it has no map.
     *
     * @return The name of the displacement map of the material
     */
    String getDisp();

    /**
     * Set the displacement map name of this material
     *
     * @param disp The displacement map name of this material
     */
    void setDisp(String disp);

    /**
     * Returns the displacement map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The displacement map {@link TextureOptions}
     */
    TextureOptions getDispOptions();

    /**
     * Set the displacement map {@link TextureOptions}
     *
     * @param options The displacement map {@link TextureOptions}
     */
    void setDispOptions(TextureOptions options);



    //--------------------------------------------------------------------------
    // Decal

    /**
     * Returns the name of the decal map of the material,
     * or <code>null</code> if it has no map.
     *
     * @return The name of the decal map of the material
     */
    String getDecal();

    /**
     * Set the decal map name of this material
     *
     * @param decal The decal map name of this material
     */
    void setDecal(String decal);

    /**
     * Returns the decal map options of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The decal map {@link TextureOptions}
     */
    TextureOptions getDecalOptions();

    /**
     * Set the decal map {@link TextureOptions}
     *
     * @param options The decal map {@link TextureOptions}
     */
    void setDecalOptions(TextureOptions options);


    //--------------------------------------------------------------------------
    // Refl (reflection)

    /**
     * Returns the list of {@link TextureOptions} objects for the reflection
     * maps of the material. This will never be <code>null</code>, but may
     * be an empty list if no reflection maps have been defined.
     *
     * @return The reflection map {@link TextureOptions}
     */
    List<TextureOptions> getReflOptions();

    /**
     * Set the roughness of this material
     *
     */
    void setPr(Float pr);

    /**
     * Returns the roughness of the material,
     * or <code>null</code> if it has no options.
     *
     * @return The roughness
     */
    Float getPr();
}
