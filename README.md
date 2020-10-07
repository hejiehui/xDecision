xDecision
=========

A decision tree editor and runtime engine

# 什么是decision tree
### 商业智能领域常用的决策工具

### 利用树形模型表达复杂的决策制定过程
![overview](https://oscimg.oschina.net/oscnet/up-2d4e231956aa24c47de8d32be0c8f9891a0.png)

# Decision Tree 编辑器可以让开发者
## 以所见即所得的方式生成decision tree
![edit](https://oscimg.oschina.net/oscnet/up-b8bf88e63ebf86110aa599c1ad2c8ddab72.png)

## 依据模型生成单元测试的验证代码
![generate test](https://oscimg.oschina.net/oscnet/up-fa974c5611b93a5c9d5d9f433292541fdce.png)

## 所有决策路径全覆盖
![validation](https://oscimg.oschina.net/oscnet/up-3aa52f77feb022afafbe99e1a2a25d5b897.png)

# 优势
纯模型，无代码

方便重用

替代if/else，极大的简化代码

# 集成说明
[参考样例POM](https://github.com/hejiehui/xDecision/blob/master/com.xrosstools.xdecision.sample/pom.xml)

Depenency

	<dependency>
		<groupId>com.xrosstools</groupId>
		<artifactId>xdecision</artifactId>
		<version>${latest}</version>
	</dependency>


# Demo project
[Demo](https://github.com/hejiehui/xDecision/tree/master/com.xrosstools.xdecision.sample)

# IDEA编辑器
[plugin](https://github.com/hejiehui/xDecision/blob/master/com.xrosstools.xdecision.idea.editor/com.xrosstools.xdecision.idea.editor.zip)

# 模型文件结构详解
[model-file-structure](https://github.com/hejiehui/xDecision/wiki/model-file-structure)

# 自定义插件
从xdecision2.0.0开始支持用户自定义插件。包括决策树结构的自定义解析和执行过程路径自定义选择

[结构解析plugin](https://github.com/hejiehui/xDecision/wiki/model-file-structure#parser)

[路径选择plugin](https://github.com/hejiehui/xDecision/wiki/model-file-structure#evaluator)
