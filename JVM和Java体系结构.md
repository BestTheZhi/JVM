# JVM和Java体系结构



## 一、前言

### 1.1 作为Java工程师的你是否也遇到过这些问题？

- 运行着的线上系统突然卡死，系统无法访问，甚至直接OOM！
- 想解决线上JVM GC问题，但却无从下手。
- 新项目上线，对各种JVM参数设置一脸茫然，直接默认吧然后就GG了
- 每次面试之前都要重新背一遍JVM的一些原理概念性的东西，然而面试官却经常问你在实际项目中如何调优VM参数，如何解决GC、OOM等问题，一脸懵逼。



### 1.2 部分开发人员的病态

- 大部分Java开发人员，除了会在项目中使用到与Java平台相关的各种高精尖技术，对于Java技术的核心Java虚拟机了解甚少。
- 一些有一定工作经验的开发人员，打心眼儿里觉得Spring框架、微服务等上层技术才是重点，基础技术并不重要，这其实是一种本末倒置的“病态”。 如果我们把核心类库的API比做数学公式的话，那么Java虚拟机的知识就好比公式的推导过程。
- 计算机系统体系对我们来说越来越远，写过许多代码的程序员对底层的实现方式也不了解。



![1](https://gitee.com/bestthezhi/images/raw/master/jvm/1.png)



### 1.3 为什么要学习JVM？

- 面试的需要（BATJ、TMD，PKQ等面试都爱问）
- 中高级程序员必备技能：项目管理、性能调优的需求
- 追求极客的精神，比如：垃圾回收算法、JIT（即时编译器）、底层原理



### 1.4 Java 与 C++

- 垃圾收集机制为我们打理了很多繁琐的工作，大大提高了开发的效率，但是，垃圾收集也不是万能的，懂得JVM内部的内存结构、工作机制，是设计高扩展性应用和诊断运行时问题的基础，也是Java工程师进阶的必备能力。
- C语言需要自己来分配内存和回收内存，Java全部交给JVM进行分配和回收。



## 二、参考资料

### 2.1 参考视频：

B站：尚硅谷宋红康JVM全套教程（详解java虚拟机）

https://www.bilibili.com/video/BV1PJ411n7xZ?p=13&share_source=copy_web



### 2.2 参考资料：

官方出的JVM的规范:

**Java Language and Virtual Machine Specifications**

![2](https://gitee.com/bestthezhi/images/raw/master/jvm/2.png)



### 2.3 参考书目：

![3](https://gitee.com/bestthezhi/images/raw/master/jvm/3.png)



## 三、Java语言简介

### 3.1  Java生态圈

Java是目前应用最为广泛的软件开发平台之一。随着Java以及Java社区的不断壮大Java 也早已不再是简简单单的一门计算机语言了，它更是一个平台、一种文化、一个社区。

- 作为一个平台，Java虚拟机扮演着举足轻重的作用
  - Groovy、Scala、JRuby、Kotlin等都是Java平台的一部分
- 作为一种文化，Java几乎成为了"开源"的代名词。
  - 第三方开源软件和框架。如Tomcat、Struts，MyBatis，Spring等。
  - 就连JDK和JVM自身也有不少开源的实现，如openJDK、Harmony。
- 作为一个社区，Java拥有全世界最多的技术拥护者和开源社区支持，有数不清的论坛和资料。从桌面应用软件、嵌入式开发到企业级应用、后台服务器、中间件，都可以看到Java的身影。其应用形式之复杂、参与人数之众多也令人咋舌。



### 3.2 字节码

- 我们平时说的java字节码，指的是用java语言编译成的字节码。准确的说任何能在jvm平台上执行的字节码格式都是一样的。所以应该统称为：jvm字节码。
- 不同的编译器，可以编译出相同的字节码文件，字节码文件也可以在不同的JVM上运行。
- Java虚拟机与Java语言并没有必然的联系，它只与特定的二进制文件格式——Class文件格式所关联，Class文件中包含了Java虚拟机指令集（或者称为字节码、Bytecodes）和符号表，还有一些其他辅助信息。



### 3.3 Java的跨平台性

#### Java：跨平台的语言

Java程序编译成字节码文件(.class)之后，在不同平台的JVM上都能解释运行。

可以说 Java语言的跨平台性，归功于JVM

![4](https://gitee.com/bestthezhi/images/raw/master/jvm/4.png)





#### JVM：跨语言的平台

java虚拟机的强大，使得许多语言也使用着JVM

- 每个语言都需要转换成字节码文件，最后转换的字节码文件都能通过Java虚拟机进行运行和处理
- 随着Java7的正式发布，Java虚拟机的设计者们通过JSR-292规范基本实现在Java虚拟机平台上运行非Java语言编写的程序。
- Java虚拟机根本不关心运行在其内部的程序到底是使用何种编程语言编写的，它只关心“字节码”文件。也就是说Java虚拟机拥有语言无关性，并不会单纯地与Java语言“终身绑定”，只要其他编程语言的编译结果满足并包含Java虚拟机的内部指令集、符号表以及其他的辅助信息，它就是一个有效的字节码文件，就能够被虚拟机所识别并装载运行。

![5](https://gitee.com/bestthezhi/images/raw/master/jvm/5.png)



### 3.4  多语言混合编程

- **Java平台上的多语言混合编程正成为主流**，通过特定领域的语言去解决特定领域的问题是当前软件开发应对日趋复杂的项目需求的一个方向。
- 试想一下，在一个项目之中，并行处理用Clojure语言编写，展示层使用JRuby/Rails，中间层则是Java，每个应用层都将使用不同的编程语言来完成，而且，接口对每一层的开发者都是透明的，各种语言之间的交互不存在任何困难，就像使用自己语言的原生API一样方便，因为它们最终都运行在一个虚拟机之上。
- 对这些运行于Java虚拟机之上、Java之外的语言，来自系统级的、底层的支持正在迅速增强，以JSR-292为核心的一系列项目和功能改进（如DaVinci Machine项目、Nashorn引擎、InvokeDynamic指令、java.lang.invoke包等），推动Java虚拟机从"Java语言的虚拟机"向"多语言虚拟机"的方向发展。



### 3.5  如何真正搞懂JVM？

Java虚拟机非常复杂，要想真正理解它的工作原理，最好的方式就是自己动手编写一个！

自己动手写一个Java虚拟机，难吗？

**天下事有难易乎？为之，则难者亦易矣；不为，则易者亦难矣**

![6](https://gitee.com/bestthezhi/images/raw/master/jvm/6.png)



### 3.6  Java发展的重大事件

- 1990年，在Sun计算机公司中，由Patrick Naughton、MikeSheridan及James Gosling领导的小组Green Team，开发出的新的程序语言，命名为Oak，后期命名为Java
- 1995年，Sun正式发布Java和HotJava产品，Java首次公开亮相。
- 1996年1月23日Sun Microsystems发布了JDK 1.0。
- 1998年，JDK1.2版本发布。同时，Sun发布了JSP/Servlet、EJB规范，以及将Java分成了J2EE、J2SE和J2ME。这表明了Java开始向企业、桌面应用和移动设备应用3大领域挺进。
- 2000年，JDK1.3发布，Java HotSpot Virtual Machine正式发布，成为Java的默认虚拟机。
- 2002年，JDK1.4发布，古老的Classic虚拟机退出历史舞台。
- 2003年年底，Java平台的scala正式发布，同年Groovy也加入了Java阵营。
- 2004年，JDK1.5发布。同时JDK1.5改名为JavaSE5.0。
- 2006年，JDK6发布。同年，Java开源并建立了OpenJDK。顺理成章，Hotspot虚拟机也成为了OpenJDK中的默认虚拟机。
- 2007年，Java平台迎来了新伙伴Clojure。
- 2008年，oracle收购了BEA，得到了JRockit虚拟机。
- 2009年，Twitter宣布把后台大部分程序从Ruby迁移到Scala，这是Java平台的又一次大规模应用。
- 2010年，Oracle收购了Sun，获得Java商标和最真价值的HotSpot虚拟机。此时，Oracle拥有市场占用率最高的两款虚拟机HotSpot和JRockit，并计划在未来对它们进行整合：HotRockit
- 2011年，JDK7发布。在JDK1.7u4中，正式启用了新的垃圾回收器G1。
- 2017年，JDK9发布。将G1设置为默认GC，替代CMS
- 同年，IBM的J9开源，形成了现在的Open J9社区
- 2018年，Android的Java侵权案判决，Google赔偿Oracle计88亿美元
- 同年，Oracle宣告JavagE成为历史名词JDBC、JMS、Servlet赠予Eclipse基金会
- 同年，JDK11发布，LTS版本的JDK，发布革命性的ZGC，调整JDK授权许可
- 2019年，JDK12发布，加入RedHat领导开发的Shenandoah GC



![7](https://gitee.com/bestthezhi/images/raw/master/jvm/7.png)

在JDK11之前，OracleJDK中还会存在一些OpenJDK中没有的、闭源的功能。但在JDK11中，我们可以认为OpenJDK和OracleJDK代码实质上已经完全一致的程度。



## 四、Java虚拟机

### 4.1 虚拟机概念

- 所谓虚拟机（Virtual Machine），就是一台虚拟的计算机。它是一款软件，用来执行一系列虚拟计算机指令。大体上，虚拟机可以分为系统虚拟机和程序虚拟机。
  - 大名鼎鼎的Virtual Box，VMware就属于系统虚拟机，它们完全是对物理计算机的仿真，提供了一个可运行完整操作系统的软件平台。
  - 程序虚拟机的典型代表就是Java虚拟机，它专门为执行单个计算机程序而设计，在Java虚拟机中执行的指令我们称为Java字节码指令。
- 无论是系统虚拟机还是程序虚拟机，在上面运行的软件都被限制于虚拟机提供的资源中。



### 4.2 Java虚拟机

- Java虚拟机是一台执行Java字节码的虚拟计算机，它拥有独立的运行机制，其运行的Java字节码也未必由Java语言编译而成。
- JVM平台的各种语言可以共享Java虚拟机带来的跨平台性、优秀的垃圾回器，以及可靠的即时编译器。
- Java技术的核心就是Java虚拟机（JVM，Java Virtual Machine），因为所有的Java程序都运行在Java虚拟机内部。
- Java虚拟机就是二进制字节码的运行环境，负责装载字节码到其内部，解释/编译为对应平台上的机器指令执行。每一条Java指令，Java虚拟机规范中都有详细定义，如怎么取操作数，怎么处理操作数，处理结果放在哪里。

特点：

1. 一次编译，到处运行
2. 自动内存管理
3. 自动垃圾回收功能



### 4.3 JVM的位置

**JVM是运行在操作系统之上的，它与硬件没有直接的交互**

![8](https://gitee.com/bestthezhi/images/raw/master/jvm/8.png)

Java的体系结构：

![9](https://gitee.com/bestthezhi/images/raw/master/jvm/9.png)



### 4.4 JVM的整体结构

- **HotSpot VM**是目前市面上高性能虚拟机的代表作之一。
- 它采用解释器与即时编译器并存的架构。
- 在今天，Java程序的运行性能早已脱胎换骨，已经达到了可以和C/C++程序一较高下的地步。
- 执行引擎包含三部分：**解释器**，**即时编译器**，**垃圾回收器**



![10](https://gitee.com/bestthezhi/images/raw/master/jvm/10.png)



### 4.5 Java代码的执行流程

![11](https://gitee.com/bestthezhi/images/raw/master/jvm/11.png)



###4.6  JVM的架构模型

Java编译器输出的指令流基本上是一种**基于栈的指令集架构**，另外一种指令集架构则是**基于寄存器的指令集架构**。具体来说：这两种架构之间的区别：

- 基于栈式架构的特点
  - 设计和实现更简单，适用于资源受限的系统
  - 避开了寄存器的分配难题：使用零地址指令方式分配
  - 指令流中的指令大部分是零地址指令，其执行过程依赖于操作栈。指令集更小，编译器容易实现
  - 不需要硬件支持，可移植性更好，更好实现跨平台

>特点：跨平台性、指令集小、指令多；执行性能比寄存器差



- 基于寄存器架构的特点
  - 典型的应用是x86的二进制指令集：比如传统的PC以及Android的Davlik虚拟机。
  - 指令集架构则完全依赖硬件，与硬件的耦合度高，可移植性差
  - 性能优秀和执行更高效
  - 花费更少的指令去完成一项操作
  - 在大部分情况下，基于寄存器架构的指令集往往都以一地址指令、二地址指令和三地址指令为主，而基于栈式架构的指令集却是以零地址指令为主

#### 两种架构的举例：

同样执行2+3这种逻辑操作，其指令分别如下：

- 基于栈的计算流程（以Java虚拟机为例）

```tex
iconst_2 //常量2入栈
istore_1
iconst_3 // 常量3入栈
istore_2
iload_1
iload_2
iadd //常量2/3出栈，执行相加
istore_0 // 结果5入栈
```

- 基于寄存器的计算流程

```tex
mov eax,2 //将eax寄存器的值设为1
add eax,3 //使eax寄存器的值加3
```



#### 总结：

- 由于跨平台性的设计，Java的指令都是根据栈来设计的。不同平台CPU架构不同，所以不能设计为基于寄存器的。优点是跨平台，指令集小，编译器容易实现，缺点是性能下降，实现同样的功能需要更多的指令。
- 时至今日，尽管嵌入式平台已经不是Java程序的主流运行平台了（准确来说应该是HotSpot VM的宿主环境已经不局限于嵌入式平台了），那么为什么不将架构更换为基于寄存器的架构呢？
- 因为基于栈的架构跨平台性好、指令集小，虽然相对于基于寄存器的架构来说，基于栈的架构编译得到的指令更多，执行性能也不如基于寄存器的架构好，但考虑到其跨平台性与可移植性，所以还是选用栈的架构。



### 4.7 JVM的生命周期

- 虚拟机的启动
  - Java虚拟机的启动是通过引导类加载器（bootstrap class loader）创建一个初始类（initial class）来完成的，这个类是由虚拟机的具体实现指定的。
- 虚拟机的执行
  - 一个运行中的Java虚拟机有着一个清晰的任务：执行Java程序
  - 程序开始执行时他才运行，程序结束时他就停止
  - **执行一个所谓的Java程序的时候，真真正正在执行的是一个叫做Java虚拟机的进程**
- 虚拟机的退出
  - 程序正常执行结束
  - 程序在执行过程中遇到了异常或错误而异常终止
  - 由于操作系统用现错误而导致Java虚拟机进程终止
  - 某线程调用Runtime类或System类的exit( )方法，或Runtime类的halt( )方法，并且Java安全管理器也允许这次exit( )或halt( )操作。
  - 除此之外，JNI（Java Native Interface）规范描述了用JNI Invocation API来加载或卸载 Java虚拟机时，Java虚拟机的退出情况。



## 五、JVM的发展历程

### Sun Classic VM

- 早在1996年Java1.0版本的时候，Sun公司发布了一款名为sun classic VM的Java虚拟机，它同时也是世界上第一款商用Java虚拟机，JDK1.4时完全被淘汰。
- 这款虚拟机内部只提供解释器。现在还有及时编译器，因此效率比较低，而及时编译器会把热点代码缓存起来，那么以后使用热点代码的时候，效率就比较高。
- 如果使用JIT编译器，就需要进行外挂。但是一旦使用了JIT编译器，JIT就会接管虚拟机的执行系统。解释器就不再工作。解释器和编译器不能配合工作。
- 现在hotspot内置了此虚拟机。

### Exact VM

为了解决上一个虚拟机问题，jdk1.2时，sun提供了此虚拟机。 Exact Memory Management：准确式内存管理

- 也可以叫Non-Conservative/Accurate Memory Management
- 虚拟机可以知道内存中某个位置的数据具体是什么类型。|

具备现代高性能虚拟机的维形

- 热点探测（寻找出热点代码进行缓存）
- 编译器与解释器混合工作模式

只在solaris平台短暂使用，其他平台上还是classic vm，英雄气短，终被Hotspot虚拟机替换

### HotSpot VM

HotSpot历史

- 最初由一家名为“Longview Technologies”的小公司设计
- 1997年，此公司被sun收购；2009年，Sun公司被甲骨文收购。
- JDK1.3时，HotSpot VM成为默认虚拟机

目前Hotspot占有绝对的市场地位，称霸武林。

- 不管是现在仍在广泛使用的JDK6，还是使用比例较多的JDK8中，默认的虚拟机都是HotSpot
- Sun/oracle JDK和openJDK的默认虚拟机
- 因此本课程中默认介绍的虚拟机都是HotSpot，相关机制也主要是指HotSpot的Gc机制。（比如其他两个商用虚机都没有方法区的概念）

从服务器、桌面到移动端、嵌入式都有应用。

名称中的HotSpot指的就是它的热点代码探测技术。

- 通过计数器找到最具编译价值代码，触发即时编译或栈上替换
- 通过编译器与解释器协同工作，在最优化的程序响应时间与最佳执行性能中取得平衡

### JRockit

专注于服务器端应用

- 它可以不太关注程序启动速度，因此JRockit内部不包含解析器实现，全部代码都靠即时编译器编译后执行。

大量的行业基准测试显示，JRockit JVM是世界上最快的JVM。

- 使用JRockit产品，客户已经体验到了显著的性能提高（一些超过了70%）和硬件成本的减少（达50%）。

优势：全面的Java运行时解决方案组合

- JRockit面向延迟敏感型应用的解决方案JRockit Real Time提供以毫秒或微秒级的JVM响应时间，适合财务、军事指挥、电信网络的需要
- MissionControl服务套件，它是一组以极低的开销来监控、管理和分析生产环境中的应用程序的工具。

2008年，JRockit被oracle收购。

oracle表达了整合两大优秀虚拟机的工作，大致在JDK8中完成。整合的方式是在HotSpot的基础上，移植JRockit的优秀特性。

高斯林：目前就职于谷歌，研究人工智能和水下机器人

### IBM的J9

全称：IBM Technology for Java Virtual Machine，简称IT4J，内部代号：J9

市场定位与HotSpot接近，服务器端、桌面应用、嵌入式等多用途VM广泛用于IBM的各种Java产品。

目前，有影响力的三大商用虚拟机之一，也号称是世界上最快的Java虚拟机。

2017年左右，IBM发布了开源J9VM，命名为openJ9，交给EClipse基金会管理，也称为Eclipse OpenJ9

OpenJDK -> 是JDK开源了，包括了虚拟机

### KVM和CDC / CLDC Hotspot

oracle在Java ME产品线上的两款虚拟机为：CDC/CLDC HotSpot Implementation VM KVM（Kilobyte）是CLDC-HI早期产品目前移动领域地位尴尬，智能机被Angroid和ioS二分天下。

KVM简单、轻量、高度可移植，面向更低端的设备上还维持自己的一片市场

- 智能控制器、传感器
- 老人手机、经济欠发达地区的功能手机

所有的虚拟机的原则：一次编译，到处运行。

### Azul VM

前面三大“高性能Java虚拟机”使用在通用硬件平台上这里Azu1VW和BEALiquid VM是与特定硬件平台绑定、软硬件配合的专有虚拟机I

- 高性能Java虚拟机中的战斗机。

Azul VM是Azu1Systems公司在HotSpot基础上进行大量改进，运行于Azul Systems公司的专有硬件Vega系统上的ava虚拟机。

每个Azu1VM实例都可以管理至少数十个CPU和数百GB内存的硬件资源，并提供在巨大内存范围内实现可控的GC时间的垃圾收集器、专有硬件优化的线程调度等优秀特性。

2010年，AzulSystems公司开始从硬件转向软件，发布了自己的zing JVM，可以在通用x86平台上提供接近于Vega系统的特性。

### Liquid VM

高性能Java虚拟机中的战斗机。

BEA公司开发的，直接运行在自家Hypervisor系统上Liquid VM即是现在的JRockit VE（Virtual Edition），

Liquid VM不需要操作系统的支持，或者说它自己本身实现了一个专用操作系统的必要功能，如线程调度、文件系统、网络支持等。

随着JRockit虚拟机终止开发，Liquid vM项目也停止了。





























