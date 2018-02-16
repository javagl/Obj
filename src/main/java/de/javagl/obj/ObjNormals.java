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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Methods related to the computation of normals of {@link ReadableObj} objects,
 * as used by {@link ObjUtils#addComputedNormals(ReadableObj, WritableObj)}
 */
class ObjNormals
{
    /**
     * Computes normals for the given {@link ReadableObj} and writes
     * the result to the given {@link WritableObj}.<br>
     * <br>
     * This method will make sure that all faces
     * {@link ObjFace#containsNormalIndices() contain normal indices}.
     * If a face already contains normal indices, it will remain unchanged.
     * Otherwise, vertex normals will be added to the output, and the
     * corresponding indices for these normals will be assigned to the
     * face.<br>
     * <br>
     * The vertex normals will be computed as the normalized sum of the
     * normals of all faces that are adjacent to the respective vertex.
     * If the vertex does not have any adjacent face with at least
     * 2 vertices, then the resulting vertex normal will be (0,0,0).
     *
     * @param <T> The type of the output
     * @param input The input {@link ReadableObj}
     * @param output The output {@link WritableObj}
     * @return The given output
     */
    static <T extends WritableObj> T addComputedNormals(
        ReadableObj input, T output)
    {
        output.setMtlFileNames(input.getMtlFileNames());
        ObjUtils.addAll(input, output);

        int numNormals = input.getNumNormals();

        // A mapping from vertex indices to the list of faces
        // that are adjacent to the respective vertex
        Map<Integer, List<ObjFace>> vertexIndexToFaces =
            computeVertexIndexToFacesMap(input);

        // The indices of existing normals. This is used for looking
        // up normals that already exist, to avoid adding them twice
        Map<FloatTuple, Integer> vertexNormalIndices =
            computeVertexNormalIndices(input);

        // A cache for the face normals that are computed
        Map<ObjFace, FloatTuple> faceNormals =
            new LinkedHashMap<ObjFace, FloatTuple>();

        for (int f = 0; f < input.getNumFaces(); f++)
        {
            ObjFace face = input.getFace(f);
            ObjUtils.activateGroups(input, face, output);

            if (face.containsNormalIndices())
            {
                // Skip faces that already contain normal indices
                output.addFace(face);
                continue;
            }

            // Prepare the index arrays for a new face, including the
            // indices for the normals that will be added
            int[] vertexIndices = new int[face.getNumVertices()];
            int[] textureIndices = null;
            if (face.containsTexCoordIndices())
            {
                textureIndices = new int[face.getNumVertices()];
            }
            int[] normalIndices = new int[face.getNumVertices()];

            int numVertices = face.getNumVertices();
            for (int v = 0; v < numVertices; v++)
            {
                // Copy the vertex- and texture coordinate index
                int vertexIndex = face.getVertexIndex(v);
                vertexIndices[v] = vertexIndex;
                if (face.containsTexCoordIndices())
                {
                    textureIndices[v] = face.getTexCoordIndex(v);
                }

                // Compute the vertex normal as the (normalized) sum of the
                // normals of all adjacent faces
                float nx = 0.0f;
                float ny = 0.0f;
                float nz = 0.0f;
                List<ObjFace> vertexFaces =
                    vertexIndexToFaces.get(vertexIndex);
                boolean hasValidNormals = false;
                for (ObjFace vertexFace : vertexFaces)
                {
                    FloatTuple faceNormal = faceNormals.get(vertexFace);
                    if (faceNormal == null)
                    {
                        faceNormal = computeFaceNormal(input, vertexFace);
                        faceNormals.put(vertexFace, faceNormal);
                    }

                    // The face normal may still be null, if the face
                    // contains fewer than 3 vertices.
                    if (faceNormal != null)
                    {
                        nx += faceNormal.getX();
                        ny += faceNormal.getY();
                        nz += faceNormal.getZ();
                        hasValidNormals = true;
                    }
                }
                FloatTuple vertexNormal = null;
                if (hasValidNormals)
                {
                    vertexNormal = FloatTuples.createNormalized(nx, ny, nz);
                }
                else
                {
                    vertexNormal = FloatTuples.create(0.0f, 0.0f, 0.0f);
                }

                // If a normal with the same values does not already exist,
                // add it to the output. Otherwise, re-use the index of the
                // normal that is already present
                Integer newNormalIndex = vertexNormalIndices.get(vertexNormal);
                if (newNormalIndex == null)
                {
                    output.addNormal(vertexNormal);
                    newNormalIndex = numNormals;
                    numNormals++;
                    vertexNormalIndices.put(vertexNormal, newNormalIndex);
                }
                normalIndices[v] = newNormalIndex;
            }
            output.addFace(ObjFaces.create(
                vertexIndices, textureIndices, normalIndices));
        }
        return output;
    }


    /**
     * Computes a mapping from vertex indices to the list of faces that
     * the respective vertex is part of.
     *
     * @param obj The input {@link ReadableObj}
     * @return The mapping from vertex indices to faces
     */
    private static Map<Integer, List<ObjFace>> computeVertexIndexToFacesMap(
        ReadableObj obj)
    {
        Map<Integer, List<ObjFace>> vertexIndexToFaces =
            new LinkedHashMap<Integer, List<ObjFace>>();
        for (int i = 0; i < obj.getNumFaces(); i++)
        {
            ObjFace face = obj.getFace(i);
            for (int j = 0; j < face.getNumVertices(); j++)
            {
                int vertexIndex = face.getVertexIndex(j);
                List<ObjFace> faces = vertexIndexToFaces.get(vertexIndex);
                if (faces == null)
                {
                    faces = new ArrayList<ObjFace>();
                    vertexIndexToFaces.put(vertexIndex, faces);
                }
                faces.add(face);
            }
        }
        return vertexIndexToFaces;
    }

    /**
     * Compute a mapping from normals to their indices in the given OBJ
     *
     * @param obj The input {@link ReadableObj}
     * @return The mapping from normals to indices
     */
    private static Map<FloatTuple, Integer> computeVertexNormalIndices(
        ReadableObj obj)
    {
        Map<FloatTuple, Integer> vertexNormalIndices =
            new LinkedHashMap<FloatTuple, Integer>();
        for (int i = 0; i < obj.getNumNormals(); i++)
        {
            FloatTuple normal = obj.getNormal(i);
            vertexNormalIndices.put(normal, i);
        }
        return vertexNormalIndices;
    }

    /**
     * Compute the normal of the given face of the given OBJ. Returns
     * <code>null</code> if the given face has fewer than 3 vertices.
     *
     * @param obj The input {@link ReadableObj}
     * @param face The {@link ObjFace} to compute the normal for
     * @return The normal
     */
    private static FloatTuple computeFaceNormal(
        ReadableObj obj, ObjFace face)
    {
        // Computing the normal using Newell's method, as described on
        // https://www.khronos.org/opengl/wiki/Calculating_a_Surface_Normal
        int numVertices = face.getNumVertices();
        if (face.getNumVertices() < 3)
        {
            return null;
        }
        float nx = 0.0f;
        float ny = 0.0f;
        float nz = 0.0f;
        for (int i = 0; i < numVertices; i++)
        {
            int i0 = face.getVertexIndex(i);
            int i1 = face.getVertexIndex((i + 1) % numVertices);
            FloatTuple v0 = obj.getVertex(i0);
            FloatTuple v1 = obj.getVertex(i1);
            nx += (v0.getY() - v1.getY()) * (v0.getZ() + v1.getZ());
            ny += (v0.getZ() - v1.getZ()) * (v0.getX() + v1.getX());
            nz += (v0.getX() - v1.getX()) * (v0.getY() + v1.getY());
        }
        return FloatTuples.createNormalized(nx, ny, nz);
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private ObjNormals()
    {
        // Private constructor to prevent instantiation.
    }

}
