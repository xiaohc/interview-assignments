# 短域名服务

#### 介绍
一个Java能力评估项目，供有意向企业对个人技术能力进行预审

#### 产品思路

各大微博推出短网址(short url)，例如：https://t.hk.uy/2hytQxrU ，该需求主要是因为微博有字数限制，长网址制约了用户可输入内容，所以把用户的长网址替换为短网址，当使用这些短网址服务[https://t.hk.uy](https://www.sina.lt/)时，对应服务会自动根据短网址重定向到原网址

> 应用模型如下
![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/应用模型.png "应用模型")

#### 功能介绍

短域名服务是一个长网址替换为短网址的后台服务。 提供了如下两个功能:

1.  短域名存储接口：接受长域名信息，返回短域名信息
2.  短域名读取接口：接受短域名信息，返回长域名信息。

#### 设计思路

1.  考虑同一个网址，不会有非常多的短网址转换请求，故用“IP地址 + 请求次数”来表示短网址中的特征码已足够满足示例项目需求
2.  短网址中的特征码（2hytQxrU）长度控制在8个字符内，内存中占位大概48字节大小，其中IPv4地址32位，请求次数16位可满足示例项目需求，其中请求次数16位，取值范围（0~65535），次数超出则轮转，即溢出归零
3.  长网址和短网址的对应关系，靠后台服务管理，示例项目映射关系就直接存放在内存中，不考虑落库保存了
4.  短域名存储接口，后台服务根据请求中的IP信息生成对应短网址特征码，保存特征码与长网址的映射关系，即多个短网址可能对应同一长网址，同一IP最多可以保存65536个短网址与长网址的对应关系
4.  短域名读取接口，后台服务管理通过提取请求中的短网址特征码，读取内存中的映射关系，将对应长网址返回给前端去重定向到原长网址

#### 架构设计

> 服务架构如下
![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/架构图.png "架构图")
1.  短域名服务使用单服务实现
2.  使用REST接口，采用SpringBoot框架，集成Swagger API文档
3.  性能：4C8G腾讯云服务器标准配置下，1wTPS，平均响应时间5ms，演示项目暂不考虑限流
5.  容量：1千万请求对应内存为1.23G，8G内存足够使用，演示项目暂不考虑数据清理，容量计算方式为：假设每个URL的平均长度为100个字符，其中英文字符占比90%，使用UTF-8 编码保存，则存储1条短域名对应关系占用内存大小为：(48/8)x2 + 90 + 10x3 = 132 字节。
4.  安全：演示项目暂不考虑更多安全设计

#### 实现细节

1.  源代码按照生产级的要求编写
2.  使用代码规范工具-Checkstyle，保证代码的整洁
3.  使用代码质量检测工具-SonarLint，消除代码中的坏味道
4.  使用静态代码扫描工具-SpotBugs，查找相关的漏洞，进行双重质量保证

#### 单元测试

> 单元测试编写思路
1.  高层测试（集成测试）为主，即模拟程序实际运行的单元测试，主要是进行业务功能测试
2.  针对单个类的单元测试作为补充，以确保分支和指令被单测覆盖
3.  单测检查应该尽量检查过程和结果，即检查函数调用情况、日志输出内容等（过程），及对应输出和保存内容（结果）

> 单元测试数据准备
1.  准备单测的测试数据，使用了等价类、边界值等测试方法
2.  复杂的测试数据（POJO测试输入）使用json文件，这种方式方便测试数据维护和复用，而且单测代码更整洁

> JaCoCo统计单元测试代码覆盖率 
![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/jacoco.PNG "jacoco") 
1.  单元测试中排除了Application、POJO类（DTO、config）
2.  单元测试中排除了lombok注解生成函数（主要是POJO类中使用）

> Pitest统计变异测试率 
![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/pitest.PNG "pitest")
1.  Pitest可以检测单元测试中校验（assert）的充分性
2.  Pitest最新版本对JUnit5支持还不够完善，需要屏蔽部分assert

#### 性能测试

1.  测试机：条件有限，测试机和服务器同时部署在个人电脑上进行的性能测试
    > CPU: i5-4590@3.3G 4C
    > RAM: 8G
    > OS: win7 64位
2.  Jmeter模拟 100 用户（长连接），针对 <短域名存储> 接口进行性能测试
    ![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/performance-100.PNG "performance-100") 
3.  测试时长5分钟，请求次数222w，全部处理成功，平均响应时间13ms，7401TPS
    ![Image text](https://github.com/xiaohc/interview-assignments/blob/master/java/doc/images/performance-report-100.PNG "performance-report-100") 
4.  因使用个人电脑进行的性能测试，且测试机和服务器部署在同一台机器上，性能分析可以看到CPU成为主要瓶颈，条件受限，未继续探索内存、IO、网络等瓶颈