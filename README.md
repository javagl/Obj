# Obj - a simple Wavefront OBJ file loader and writer

Maven dependency:

    <dependency>
      <groupId>de.javagl</groupId>
      <artifactId>obj</artifactId>
      <version>0.3.0</version>
    </dependency>
    
Direct JAR link: [https://oss.sonatype.org/content/repositories/releases/de/javagl/obj/0.3.0/obj-0.3.0.jar](https://oss.sonatype.org/content/repositories/releases/de/javagl/obj/0.3.0/obj-0.3.0.jar)

# Samples

Samples showing how to use this library are available in the [ObjSamples project](https://github.com/javagl/ObjSamples).
    

# Overview

This is a simple loader and writer for Wavefront `.OBJ` files. The elements
that are currently supported are

 - Vertices
 - Texture coordinates
 - Normals
 - Faces (with positive or negative indices)
 - Groups
 - Material groups
 - MTL files
 
The `Obj` interface is basically an in-memory representation of an OBJ file.
It combines a `ReadableObj`, which provides the contents of the OBJ file,
and a `WritableObj`, which may receive elements like vertices and faces
in order to build an OBJ in memory.

The `ObjReader` class may either create a new `Obj` object directly 
from an input stream, or pass the elements that are read from the input 
stream to a `WritableObj`.

The `ObjWriter` class offers a method to write a `ReadableObj` object
to an output stream.

The `ObjData` class offers various methods to obtain the data that is
stored in a `ReadableObj` as plain arrays or *direct* buffers. 

The `ObjUtils` class offers basic utility methods for general operations
on the OBJ data. 

## Rendering OBJ data with OpenGL
   
The `ObjUtils` class contains methods that aim at preparing the OBJ so 
that it may easily be rendered with OpenGL. These methods may...

 - convert a single group of an OBJ into a new OBJ
 - triangulate OBJ data
 - make sure that texture coordinates or or normal coordinates are unique
   for each vertex
 - convert an OBJ to an OBJ that is uses the same index sets for vertices,
   texture coordinates and normals

The latter operations are also summarized in one dedicated method, namely
the `ObjUtils.convertToRenderable` method:

    InputStream inputStream = ...;

    Obj obj = ObjUtils.convertToRenderable(
        ObjReader.read(inputStream));

    IntBuffer indices = ObjData.getFaceVertexIndices(obj);
    FloatBuffer vertices = ObjData.getVertices(obj);
    FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
    FloatBuffer normals = ObjData.getNormals(obj);

These buffers may directly be used as the data for vertex buffer objects (VBO)
in OpenGL. 

### Extracting material groups

An OBJ may contain multiple material definitions. When such an OBJ should
be rendered with OpenGL, this usually means that there will be one shader
for each material - or at least, different textures may have to be used
for different parts of the objects. This library offers methods to extract 
the parts of the OBJ that have the same material. In the OBJ format, these 
groups consist of the triangles that follow one `usemtl` directive.

When such an OBJ file is read, the resulting material groups may be obtained
from the `ReadableObj` object, and each of them can be converted into a new
`Obj` object using the `ObjUtils#groupToObj` method. 

The `ObjSplitting` class contains a convenience method for this:

    Obj obj = ObjReader.read(...);
    Map<String, Obj> mtlObjs = ObjSplitting.splitByMaterialGroups(obj);;

Each of these `Obj` objects may then be converted into a renderable OBJ,
using the `ObjUtils.convertToRenderable` method as described above, 
and then be rendered with the appropriate shader for the respective
material.

### Limiting the number of vertices per OBJ

In certain environments, the number of vertices that may be involved in
one rendering call is limited. Particularly, in WebGL or OpenGL ES 2.0,
the indices that are used for indexed draw calls may only be of the type
`GL_UNSIGNED_SHORT`, which means that no object may have more than
65k vertices. In these cases, larger OBJ files have to be split into
multiple parts. Additionally, the index buffers that are passed to 
the rendering API may not contain (4-byte) `int` elements, but only
(2-byte) `short` elements. 

The `ObjSplitting` class contains a method that allows splitting an
OBJ into multiple parts, each having only a maximum number of vertices.
Additionally, the `ObjData` class contains methods for converting 
an `IntBuffer` into a `ShortBuffer`. 
 
So in order to split a large OBJ into multiple parts, and render each
part with WebGL or OpenGL ES 2.0, the following code can be used:

    Obj largeObj = ObjReader.read(...);
    Obj renderableObj = ObjUtils.convertToRenderable(largeObj);
    
    if (renderableObj.getNumVertices() > 65000)
    {
        // If this has to be rendered with OpenGL ES 2.0, then
        // the object may not contain more than 65k vertices!
        // Split it into multiple parts: 
        List<Obj> renderableParts = 
            ObjSplitting.splitByMaxNumVertices(renderableObj, 65000);
        for (Obj renderablePart : renderableParts)
        {
        
            // Obtain the indices as a "short" buffer that may
            // be used for OpenGL rendering with the index 
            // type GL_UNSIGNED_SHORT
            ShortBuffer indices = ObjData.convertToShortBuffer(
                ObjData.getFaceVertexIndices(renderablePart));
            ...
            
            sendToRenderer(indices, ...);
        }
     }
     ...


--- 

# Change log

**0.3.0** (2018-01-12)

- Added `ObjSplitting` class for splitting OBJs
- Added `ObjData#convertToShortBuffer` method
- Added `ObjUtils#add` method for combining OBJs 

**0.2.1** (2015-10-26)

- Bugfix: Made `AbstractWritableObj#addNormal(FloatTuple)` non-final
- Added methods in `ObjReader` and `MtlReader` that accept a `Reader`
  instead of an `InputStream`, and methods in `ObjWriter` and 
  `MtlWriter` that accept a `Writer` instead of an `OutputStream`
- Added implementation of `ObjUtils.convertToRenderable` that
   receives a `WritableObj`  
  
**0.2.0** (2015-10-05) : 

- Initial public release on GitHub and Maven Central
   

