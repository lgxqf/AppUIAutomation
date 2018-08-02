# APP UI Automation Framework

![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/structure.png)

该框架适用情况：同一个APP, Android和iOS UI结构基本一致

基于Page Object模式开发的UI Automation Framework.
统一iOS和Android的元素查找和操作，提高代码的复用，尽可能的避免iOS与Android因查找元素方式不同导致的相似代码

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


## Task 
* 框架通过读取 task目录下的yml 运行指定的测试任务


```
在任务的yml中配置四项值
1. port : Appium 端口   
2. udid : 设备ID
3. wdaPort : iOS设备运行的时的WDA port
4. class : 待运行的测试类

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">

<suite name="TestSuite">
    <listeners>
        <listener class-name="framework.TestListener" />
    </listeners>

    <test name="Performance">
        <parameter name = "port" value = "4723"/>     
        <parameter name = "udid" value = "SJE0217B29005225"/>
        <parameter name = "wdaport" value = "8001"/>
        <classes>
            <class name="testcase.weclass.WCPerformanceTestCPU"/>
            <class name="testcase.weclass.WCPerformanceTestBattery"/>
        </classes>
    </test>
</suite>
```


# 参考文档
* Page Object
* How can I configure the maven shade plugin to include test code in my jar?
https://code.i-harness.com/en/q/4e91ca