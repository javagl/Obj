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
     * Returns the name of the diffuse map of the material,
     * or null if it has not map.
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
    




    
}
