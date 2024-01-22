# 一、DSL 的语言设计

## 1.设计理念

如今市面上的各种平台系统，如京东、淘宝等，为了提升客服效率，都设有客服机器人的功能，即根据用户的输入，可以按照一定的逻辑给予回复。而这个逻辑的产生是由一种特定领域的语言所决定（**DSL** **语言**），因此，我们需要设计一套 DSL 语法，这个语法需要**遵循简洁**、**用户体验优先**、**易上手**、**扩展性强**、**易集成**等特点。

本项目从上述特点出发，设计了一套 DSL 语言，旨在帮助系统开发一个可以对用户输入做出自动回复的客服机器人，提升用户体验的同时降低人工客服的成本。

## 2.词法定义

在进行语法定义之前，需要设计 DSL 语言所用到的词法，本项目中所包含的词法类型共有以下几种：

![](.\docs\images\词法类型.PNG)

对于每一种词法类型，又包含具体的词汇。

关键字：

![](.\docs\images\关键字.PNG)

标识符：合法的变量命名

字符串：以 “” 包裹的词汇

数字：整数（不支持小数，且在 int 范围内）

布尔值：false 或 true

分隔符：

![](.\docs\images\分隔符.PNG)

运算符：

![](.\docs\images\运算符.PNG)

以上就是本项目中所用到的词法，这些词法是用于描述 DSL 中的词法结构，即如何将输入的字符序列划分为有意义的标记（tokens），在后续的词法分析、语法分析中有着重要的作用。

## 3.语法定义

在设计一套 DSL 语言时，语法的定义是其中很重要的一环，清晰的语法定义可以帮助脚本开发者快速上手并开发一套完备的脚本。

为了提升语法的简洁性，本项目规定一个完整的脚本由**若干个完整的句子**构成，而一个完整的句子会由**一个关键字、一个标识符、{ 分隔符、若干个属性、属性值、} 分隔符**组成，以 **global** 关键字为例，如果要定义一个关键字为 **global** 的句子，则这个句子应该如下所示：

```Python
global 招呼语 {
    type: "string";
    value: "您好！";
}
```

从上面可以看出，global 关键字有 type、value 两种属性，每个属性用一个冒号 : 后面紧跟的是它的值，每定义完一个属性之后，用一个分号 ; 表示结束一行，继续定义下一个属性。

因此，总结出一个完整的句子定义为以下格式：

```Plain
<keyword> <identifier> {
    <attribute_name>: <attribute_value>;
    ......
    <attribute_name>: <attribute_value>;
}
```

在词法分析中，共定义了 **6** 种关键字，分别是 **env****、global、****query****、intent、action、status**。

下面我将对这 6 种关键字进行逐一描述，包括**设计理由、属性解释、示例用法**三个方面。

### 关键字分析

#### env 

- 设计理由

顾名思义，这是一个表示**环境变量**的关键字，那么为什么会有这个关键字呢？显然，系统所面对的用户不是一个固定的值，也就是说每个用户应该对应一个唯一的执行环境，这个执行环境有单独的变量表，是单个用户独有的，因此，设计 env 变量可以帮助我们捕获每个用户特有的信息。

- 属性解释

| 属性名       | 属性值类型 | 是否必须 | 属性描述             |
| ------------ | ---------- | -------- | -------------------- |
| package_name | 字符串     | 是       | 对应到 Java 中的包名 |
| class_name   | 字符串     | 是       | 对应到 Java 中的类名 |

- 示例用法

比如我想捕获到**每个用户的独有信息**，那么我可以进行以下的定义（package_name 和改成自己程序中 User 类所在的包路径）：

```Java
env user {
    package_name: "com.jhc.csbot.model.entity"; // 对应的包名
    class_name: "User"; // 对应的类名
}
```

#### global

- 设计理由

顾名思义，这是表示**全局变量**的关键字，那么为什么会有这个关键字呢？首先，这是所有执行环境共享的变量，那么考虑如果有一个词在脚本的多个地方用到，比如招呼语，难道我们每次都需要写一个具体的招呼语？显然这是非常麻烦的，因此用 global 关键字可以把一些在多个地方用到的词定义为一个变量，在之后用到的时候直接使用这个变量即可。

- 属性解释

| 属性名 | 属性值类型     | 是否必须 | 属性描述                                    |
| ------ | -------------- | -------- | ------------------------------------------- |
| type   | 字符串         | 是       | 表示变量的类型，只支持 int、string 两种类型 |
| value  | 字符串 \| 数字 | 是       | 变量的值，类型需要保证和上面定义的类型一致  |

- 示例用法

```Java
global 招呼语 {
    type: "string";
    value: "您好！";
}
```

#### query

- 设计理由

顾名思义，这是表示**查询变量**的关键字，那么为什么会有这个关键字呢？首先，搞清楚为什么需要查询？由于每个用户的独特性，比如一个用户需要查询他的账单，那显然这时候需要去数据库查询。那脚本怎么能告诉比如查询账单时需要去数据库查询呢？因此，这个关键字就是用来告诉程序一些需要通过数据库查询的变量。

- 属性解释

| 属性名  | 属性值类型 | 是否必须 | 属性描述               |
| ------- | ---------- | -------- | ---------------------- |
| table   | 字符串     | 是       | 指明去数据库哪张表查询 |
| filters | 对象数组   | 否       | 过滤条件               |
| field   | 字符串     | 是       | 查询的字段             |

- 示例用法

```Java
query 账单 {
    table: "user"; // 从哪张表
    filters: {
        user_id: user.id;
    };
    field: "账单";
}
```

上述句子在程序中会转换为一条 SQL：select 账单 from user where user_id = user.id

#### intent

- 设计理由

顾名思义，这是表示**目的**的关键字，那么为什么会有这个关键字呢？**识别用户的目的啊！**

- 属性解释

| 属性名      | 属性值类型  | 是否必须 | 属性描述                                               |
| ----------- | ----------- | -------- | ------------------------------------------------------ |
| pattern     | 字符串数组  | 否       | 匹配规则：正则表达式进行匹配                           |
| include     | 字符串数组  | 否       | 匹配规则：简单的包含关系进行匹配                       |
| priority    | 数字        | 是       | 目的的优先级，用于确定当多个目的匹配到时，应该选择哪个 |
| exec_action | action 类型 | 是       | 匹配到此目的时执行的动作（**具体见下方 action 定义**） |

- 示例用法

```Java
intent 问候 {
    pattern: ["^你好", "^您好"];
    include: ["早上好", "中午好", "晚上好"];
    priority: -1;
    exec_action: 执行问候;
}
```

#### action

- 设计理由

用于描述机器人的行动。

- 属性解释

| 属性名       | 属性值类型  | 是否必须 | 属性描述                                             |
| ------------ | ----------- | -------- | ---------------------------------------------------- |
| default      | 布尔值      | 是       | 是否是默认回复（**就是没匹配的用户意图执行的动作**） |
| reply        | 字符串      | 是       | 机器人回复的话                                       |
| after_status | status 类型 | 是       | 执行完动作进入的状态（**具体见下方 status 定义**）   |

- 示例用法

```Java
action 执行问候 {
    default: false;
    reply: "你好！很高兴为您服务";
    after_status: 欢迎;
}
```

#### status

- 设计理由

用于描述用户当前的状态。

- 属性解释

| 属性名 | 属性值类型 | 是否必须 | 属性描述                                         |
| ------ | ---------- | -------- | ------------------------------------------------ |
| init   | 布尔值     | 是       | 是否为初始状态                                   |
| speak  | 字符串     | 否       | 机器人主动回复的话（**就是进入这个状态说的话**） |
| option | 对象数组   | 否       | 机器人提供给用户的选项                           |

其中 option 中的每个对象属性解释为：

| 属性名  | 属性值类型  | 是否必须 | 属性描述             |
| ------- | ----------- | -------- | -------------------- |
| input   | 字符串      | 是       | 用户输入             |
| text    | 字符串      | 是       | 在 option 显示的文本 |
| purpose | intent 类型 | 是       | 对应的用户意图       |

- 示例用法

```Java
status 欢迎 {
    init: true;
    speak: "您好！请问有什么可以帮助您吗?";
    option：[{
        input: "1";
        text: "查询账单";
        purpose: 查账单;
    }, {
        input: "2";
        text: "查余额";
        purpose: 查余额;
    }];
}
```

在对每个关键字分析完后，可以得到本项目设计的 DSL 语法的**形式化定义**如下：

```Go
Program            ::= Statement*
Statement          ::= EnvironmentDeclaration | GlobalDeclaration | QueryDeclaration | IntentDeclaration | ActionDeclaration | StatusDeclaration
EnvironmentDeclaration ::= "env" Identifier "{" PropertyDeclaration* "}"
GlobalDeclaration  ::= "global" Identifier "{" PropertyDeclaration* "}"
QueryDeclaration   ::= "query" Identifier "{" PropertyDeclaration* "}"
IntentDeclaration  ::= "intent" Identifier "{" PropertyDeclaration* "}"
ActionDeclaration  ::= "action" Identifier "{" PropertyDeclaration* "}"
StatusDeclaration  ::= "status" Identifier "{" PropertyDeclaration* "}"
PropertyDeclaration ::= Identifier ":" PropertyValue ";"
PropertyValue      ::= NumberLiteral | StringLiteral | BooleanLiteral | Identifier | ArrayLiteral | ObjectLiteral
NumberLiteral      ::= [0-9]+
StringLiteral      ::= "\"" .* "\""
BooleanLiteral     ::= "true" | "false"
Identifier         ::= [a-zA-Z][a-zA-Z0-9]*
ArrayLiteral       ::= "[" (Value ("," Value)*)? "]"
ObjectLiteral      ::= "{" (Identifier ":" Value ("," Identifier ":" Value)*)? "}"
```

1. ## 示例脚本以及运行效果

### 示例脚本

```Go
// user 环境变量
env user {
    package_name: "com.jhc.csbot.model.entity";
    class_name: "User";
}

global 存款 {
    type: "int";
    value: 1000000;
}

global 招呼语 {
    type: "string";
    value: "您好";
}

// 头像
query 头像地址 {
    table: "user";
    filters: {
        id: user.id;
    };
    field: "avatar";
}

// 手机号
query 手机号 {
    table: "user";
    filters: {
        id: user.id;
    };
    field: "phone";
}

/*
    打招呼
*/
intent 问候 {
    pattern: ["^你好", "^您好"];
    include: ["早上好", "中午好", "晚上好"];
    priority: -1;
    exec_action: 执行问候;
}
action 执行问候 {
    default: false;
    reply: "你好！很高兴为您服务";
    after_status: 欢迎;
}

/*
    查头像
*/
intent 查头像 {
    include: ["头像", "照片"];
    priority: 1;
    exec_action: 执行查头像;
}
action 执行查头像 {
    default: false;
    reply: "您的头像地址是：${头像地址}";
    after_status: 欢迎;
}

/*
    查手机号
*/
intent 查手机号 {
    include: ["手机号", "电话"];
    priority: 1;
    exec_action: 执行查手机号;
}
action 执行查手机号 {
    default: false;
    reply: "您的手机号是：${手机号}";
    after_status: 欢迎;
}

// 默认回复
action 默认 {
    default: true;
    reply: "抱歉，我不太明白您的意思, 请换个说法试试";
    after_status: 常规;
}

// 初始状态
status 欢迎 {
    init: true;
    speak: "${user.nickName}, ${招呼语}！请问有什么可以帮助您吗?";
    option: [{
        input: "1";
        text: "查头像";
        purpose: 查头像;
    }, {
        input: "2";
        text: "查手机号";
        purpose: 查手机号;
    }];
}

// 常规状态
status 常规 {
    init: false;
}
```

# 二、模块设计

## 1.系统架构

本项目采用的是**前后端分离架构模式**，前后端用到的技术栈以及对应职责为：

前端：

- 技术栈：Vue3 + Ts
- 职责：负责构建一个美观、用户体验好的页面，提供输入框、对话框实现用户与客服机器人的对话展示

后端：

- 技术栈：Java 17 + Spring Boot 3.1 + Netty
- 职责：接收前端传来的用户输入，并调用**应答模块中的解释器**，机器人产生的回复内容通过 netty 搭建的 ws 服务器向前端推送

### 系统架构图

![](.\docs\images\系统架构图.png)

## 2.应答机器人模块

本项目的重点是根据上面定义的 DSL 语言实现一个解释器，这个解释器可以根据用户输入，执行脚本逻辑，并产生相应的应答。

因此，这一块的模块设计就起到非常重要的作用。为了降低模块之间的耦合性，我将脚本解释器的实现分为了以下目录：

![](.\docs\images\解释器目录结构.png)

其中每一个目录都有自己的职责，如下表所示：

| 目录       | 职责                                                       |
| ---------- | ---------------------------------------------------------- |
| common     | 存储一些通用的信息，包括模型定义、枚举类定义、异常类定义等 |
| core       | 脚本解释器的核心模块                                       |
| test       | 脚本解释器的测试模块                                       |
| utils      | 一些工具类                                                 |
| script.txt | DSL 脚本文件                                               |

对于 common，utils 这两个目录，里面分别包含数据结构的定义、工具类等，这里不做赘述。

对于 test 目录，主要在下面的系统测试中介绍。

对于 core 目录，这是解释器的核心模块，也是脚本解释器最关键的一部分，下面将对这个 core 模块进行详细的介绍说明。

core 总共分为 **3** 个主要模块，分别是词法分析器、语法分析器、 解释器。在每个模块中，又分别有个 modules 目录，表示该模块的子模块，如下图所示：

![](.\docs\images\解释器核心模块划分.png)

由于每个主模块中包含若干个子模块，因此这里用一张**模块结构图**来表示各个模块之间的调用关系以及包含关系：

![](.\docs\images\解释器模块关系图.png)

以上就是脚本解释器所有的模块划分，每个模块都有具体的职责，模块之间可以相互调用，将一个独立的功能封装为一个独立的类，提供接口给外面模块调用，大大降低了系统的冗余度，提高了系统的扩展性。

## 3.设计模式的运用

本项目主要运用了设计模式中的策略模式、工厂模式。

对于语法分析器中的语法检查器就用到了这两种模式，由于系统中包含多种关键字，每种关键字理论上有着不同的检查方式，假如现在有一个关键字句子需要检查，如果不运用设计模式，那代码大概如下所示：

```Java
if (keyword == "env"） {
    // 进行 env 检查
} else if (keyword == "global") {
    // 进行 global 检查
} else if (keyword == "query") {
    // 进行 query 检查
} ......
```

显然，上面的代码中出现了很多 if、else 的嵌套（用 switch 其实也算嵌套了），不优雅的同时显然系统扩展性也变差了（**假设突然系统需要多加一个关键字，难道还需要再写一个 if else ?**）

因此，采用策略模式、工厂模式即可解决这一问题，具体的代码格式如下：

![](.\docs\images\设计模式.png)

现在，只需要用两行行代码就可以根据关键字类型进行相应的检查逻辑，如下所示：

```Java
// 根据 key 从工厂中获取相应的检查器
AbstractKeywordChecker checker = KeywordCheckerFactory.getStrategy(key);
checker.check(value, variables);
```

除了代码变得优雅很多，系统的扩展性也增强了，现在只要每增加一个关键字，都去写一个这样的类，然后继承基类、注册进工厂就可以完成程序的扩展，降低了耦合度。

解释器中的变量构造器也用到了类似的设计模式，这里不做赘述，基本思想是一致的。
