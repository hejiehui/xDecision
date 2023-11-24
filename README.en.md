xDecision
=========

A decision tree editor and runtime engine.

# What is decision tree
### Commonly used decision-making tools in the field of business intelligence

### Express complex decision-making processes using tree models
![overview](https://oscimg.oschina.net/oscnet/up-2d4e231956aa24c47de8d32be0c8f9891a0.png)

# The Decision Tree editor allows developers to
## Generate decision trees in a WYSIWYG manner
![edit](https://oscimg.oschina.net/oscnet/up-b8bf88e63ebf86110aa599c1ad2c8ddab72.png)

## Generate verification code for unit tests based on the model
![generate test](https://oscimg.oschina.net/oscnet/up-fa974c5611b93a5c9d5d9f433292541fdce.png)

## Full coverage of all decision paths
![validation](https://oscimg.oschina.net/oscnet/up-3aa52f77feb022afafbe99e1a2a25d5b897.png)

# Advantage

* Pure model, no code

* Easy to reuse

* Replace if/else, greatly simplifying code

# Integration instructions

[Reference sample POM](https://github.com/hejiehui/xDecision/blob/master/com.xrosstools.xdecision.sample/pom.xml)

Dependency

```xml
<dependency>
    <groupId>com.xrosstools</groupId>
    <artifactId>xdecision</artifactId>
    <version>${latest}</version>
</dependency>
```

Starting from 2.3.1, all new versions will be published to the [Maven central repository](https://s01.oss.sonatype.org/#nexus-search;quick~xdecision)

# Demo project
[Demo](https://github.com/hejiehui/xDecision/tree/master/com.xrosstools.xdecision.sample)

# IDEA editor
[plugin](https://github.com/hejiehui/xDecision/blob/master/com.xrosstools.xdecision.idea.editor/com.xrosstools.xdecision.idea.editor.zip)

# Detailed explanation of model file structure
[model-file-structure](https://github.com/hejiehui/xDecision/wiki/model-file-structure)

# Custom plugin
Starting from xdecision2.0.0, user-defined plug-ins are supported. Including custom analysis of decision tree structures and custom selection of execution process paths

[Structure analysis plugin](https://github.com/hejiehui/xDecision/wiki/model-file-structure#parser)

[Path selection plugin](https://github.com/hejiehui/xDecision/wiki/model-file-structure#evaluator)
