# Concepts

## Idea of entities

We have a data schema that holds the data for the production, order, ...

## Logical Concepts

### EntitySuperClass

The EntitySuperClass has 2 id properties. The first is for JPA as database persistence id. The second is for "own" ids.
This is for example if you take a dataset from an ERP System, you don't know the format and the datatype. So only string could be used.
For JPA string as key is not good. So JPA manages all his ids and the application the "externalId".

### References between Entity objects

We have fully connected Objects. That means we have a @ManyToOne and @OneToMany connection. The different direction are needed for different use cases.

### Json Views

Objects could be transformed to Json. But the problem is, that no circular dependency are allowed.
For different uses cases the solving of this is different. So we use @JsonView(JsonViews.SchemaMapperDefault.class) to get different Views of data.

### Unit

Represents a number with a unit. For example 6 kg.


## Technical Concepts

### @Data
@Data generates Constructor, getter and setter for a class. With the constructor there are often problems.
The empty constructor could be generated with @NoArgsConstructor.

### @MappedSuperclass

You could extend a class with a @MappedSuperclass and declare the internal fields as database properties.
So every class has the properties defined in the @MappedSuperclass.

### @CustomerField and @CustomerMethod

We are able to extend every Class with additional proper's. Every additional property/method is annotated with these classes.
This means that @CustomerField and @CustomerMethod is not used inside the entities project.

