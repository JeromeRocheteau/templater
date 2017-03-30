# templater

Java Library for template engine processing

## Getting Started

- [How to install this library?] (https://github.com/JeromeRocheteau/templater#how-to-install-this-library)
- [How to use this library?] (https://github.com/JeromeRocheteau/templater#how-to-use-this-library)
- [How to write templates?] (https://github.com/JeromeRocheteau/templater#how-to-write-templates)

### How to install this library?

Currently, you have to retrieve source code from the GitHub repository and install the Java library locally thanks to Maven.

```
git clone https://github.com/JeromeRocheteau/templater.git
cd templater
mvn install
```

Once done, you need to insert the following dependency into your file `pom.xml`:

```xml
    <dependency>
      <groupId>com.github.jeromerocheteau</groupId>
      <artifactId>templater</artifactId>
      <version>1.0</version>
    </dependency>
```

### How to use this library?

There are only 2 steps to follow in order to process templates i.e. it requires a template (file or string)
and provides a string buffer that corresponds to the input template that has been fulfilled by some scope values:

1. create only once a `Templater` object by the means of its constructor in providing a `Reader` object build from an input source that corresponds to the template;
2. call as often as wanted the `doProcess` method in providing values to inject into the tempate map from strings to objects.

The following source code can help:

```java
public class MyTemplater {

  private Templater templater;
	
  public void setUp(String path) throws Exception {
    InputStream stream = new FileInputStream(path);
    Reader reader = new InputStreamReader(stream);
    templater = new Templater(reader);
  }

  public void doTemplate(Map<String, Object> scope) throws Exception {
    StringBuffer buffer = templater.doProcess(scope);
  }
  
}
```

### How to write templates?

Templates are text files with special instructions defined by the pattern `${...}`. 
Values can be injected into the template if they belong to the scope of the `doProcess` method. 
Scope elements can be either:

- values `${value}`, 
- object fields `${object.field}`, 
  - field paths can go through several objects `${object.innerObject.field}`;
- collections iterable according to a loop `${for item : collection} ... ${for}`, 
  - use `${item-index}` within a loop to get the index of the item in the iterared collection;
  - use `${item-first}` within a loop to get a boolean value `true` if the item is the first of the collection;
  - use `${item-last}` within a loop to get a boolean value `true` if the item is the last of the collection;
- maps iterable  according to a loop `${for entry : map} ... ${for}`.
  - use `${entry.key}` within a loop to get the entry key of iterared map;
  - use `${entry.value}` within a loop to get the entry value of iterared map;

Moreover, a test instruction `${if test} ... ${if}` can be used in order to process the embedded template
only if the testable value `test` is true. Testable values can be either:

- booleans: the test is true if and only if the boolean is true;
- objects: the test is true if and only if the object is not null;
- collections or maps: the test is true if and only if the object is not null and not empty;

There is no `else` instruction. Instead use the negation of a test `${if not test} .... ${if}`.

The example below can help:

```
${task.name}

${if task.items}
  ${for item : task.items}
    ${item-index}. ${item}
    ${if item-last}.${if}
    ${if not item-last};${if}
  ${for}
${if}
```

Enjoy!
