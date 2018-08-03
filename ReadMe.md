# APP UI Automation Framework

一个基于Appium 1.8.1、TestNG，Page Object模式开发的UI自动化测试框架

![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/structure.png)


## 基本功能
* 每秒生成一次截图
* 通过yml配置待执行的测试用例
* 通过yml指定待执行测试的设备及Appium端口
* 用例执行失败自动重试，且重试次数可配置
* 用例执行失败时自动截图
* 生成测试报告(NGReport)
* 支持自定义配置项


## 设计目标
* 用一套代码执行Android/iOS测试用例


## 设计理念
* 应用Page Object模式提高UI页面操作代码的复用度
* 用Driver类封装所有用到的Appium API, 框架中其它类只通过Driver调用Appium的方法，这种作法会有以下两点好处：
*      一、屏蔽对Appium API的依赖，如果Appium的某个API官方废弃了，只需修改Driver类封装的相应方法即可
*      二、如果将Appium换成Macaca或其它框架，除了改动Driver类 其它类无需改动
* 在Driver中用findElementById等封装对iOS和Android的元素查找，提高代码的复用，尽可能的避免iOS与Android因查找元素方式不同而写相似的代码
* 该框架适用于同一个APP, Android和iOS UI结构基本一致的情况

## 一些原则
* Page类的构造函数用Verify代替
* Page类的构造函数用过findElementByID等来 检查当前页面是不否为期望的Page
* 依照SRP原则，Page类内的函数 只返回当前类实例（this)或void， 不返回其它页面的对象，确保每个Page与依赖于任何其它Page,提高Page类的复用度


## 类
* Driver : 封装所有用到的Appium方法。作用屏幕对Appium的依赖、提供更方便的函数。
* BasePage : 所有Page类的基类
* BaseTest : 所有Test类的基类
* ConfigUtil : 读取工程配置文件
* ResourceUtil : 读取资源配置文件 
* Util : 工具类，提供一些能用方法
* PageUtil : 封装进入某个页面的方法，方便复杂test case的编写
* TestListener : 监听测试结果，用例执行失败时截图


## 配置文件 
* Config.yml 运行测试时的一些配置项 如包名，重试次数等等。 详见Config.ym内的l注释
* Android 查找找查元素时用到的字符串 如： LOGIN_PAGE_PHONE_TEXT_ID: com.xes.jazhanghui.activity:id/xes_login_username
* iOS 查找元素时用到的字符串 如： LOGIN_PAGE_PHONE_TEXT_ID: name == '用户名'

## 资源文件（具体使用方法见demo）
* 为每个元素新建一个便于辨识的名字，用这个名字统一Android/iOS待查找元素, 然后将不同系统找中该名字的元素对应的值写入相应的RES.yml中
* AndroidRES.yml 写入Android元素的查找时需要用到的值
* IOSRES.yml 写入iOS元素的查找时需要用到的值

## 测试用例集 
* 框架通过读取 task目录下的yml 运行指定的测试用例


```
在任务的yml中配置四项值
1. port : Appium 端口   
2. udid : 设备ID
3. wdaPort : iOS设备运行的时的WDA port
4. class : 待运行的测试类

测试执行时输入的yml样例

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">

<suite name="TestSuite">
    <listeners>
        <listener class-name="framework.TestListener" />
    </listeners>

    <test name="Performance">
        <parameter name = "port" value = "4723"/>     
        <parameter name = "udid" value = "SJE0217B29005225"/>
        <parameter name = "wdaPort" value = "8001"/>
        <classes>
            <class name="testcase.weclass.WCPerformanceTestCPU"/>
            <class name="testcase.weclass.WCPerformanceTestBattery"/>
        </classes>
    </test>
</suite>
```

## 如何运行demo
* demo实现的功能：打开微信(若未登录微信，请先手动登录)，然后打开朋友圈，查看第一个朋友圈(带图片的)
* 启动Appium，然后运行以下命令
* 方式一 ： 将工程打成Jar包，然后运行命令 java -jar UIAutomation-1.0-fat-tests  ./task/demo.yml
* 方式2  ： IDEA中 右键单击demo.yml ,选择运行。见下图
![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/Run-By-IDEA.png)


## 参考文档
* Page Object https://blog.csdn.net/qq_37546891/article/details/79037299
* How can I configure the maven shade plugin to include test code in my jar?
https://code.i-harness.com/en/q/4e91ca