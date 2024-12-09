# Concepts

### PersistenceManager

We rap our database with the PersistenceManager. Here we have better exceptions and a view very helpful Request, e.g. getById.
Very special is the method persistEntitySuperClass here we add Entities by their external id.

### Exceptions

The utils could throw a lot of different exceptions. To handle this, we have a UtilsBaseException and for every different use case we have new BaseExceptions and Special Exceptions.  E.g.:

UtilsBaseException -> DatabaseBaseException -> DatabaseQueryException

With a good exception management, we could write good and debuggable software.

### ZipFileService

With the zipFileService we could work nice with files inside a zip. For example, you could load this file:

"C:/HiCuMES/wildfly-24.0.1.Final/standalone/deployments/mappingBackend.war/WEB-INF/lib/mappingEngine-1.0-SNAPSHOT.jar/lodash.min.js"


### JsonTransformer

Load and parse Json things with JsonViews

### Reflection

Helper for reflection with Classes.

