# App UI Automation Framework

一个基于Appium 1.8.1、TestNG，Page Object模式开发的UI自动化测试框架

![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/structure.png)
![](/uploads/photo/2018/fc861afc-93c7-4fab-aa59-b5050886e600.png!large)

## 基本功能
* 每秒生成一次截图
* 生成Android/iOS log文件
* 用例执行失败自动重试，且重试次数可配置
* 用例执行失败时自动截图
* 生成测试报告(NGReport)
* 通过xml配置待执行的测试用例
* 通过yml指定待执行测试的设备及Appium端口
* 支持自定义配置项

![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/Config.png)
![](/uploads/photo/2018/07156b5a-f994-49fa-8dfe-be86a6b5f7d8.png!large)

## 设计目标
* 用一套代码执行Android/iOS测试用例
* Test case层的代码高度利用，只需要考虑业务逻辑，无需关心系统平台及如何查找元素
```aidl
以下代码在iOS和Android上均可运行

    //打开我的朋友圈
    public void showMyMoment(){
        //打开微信主页面，点击"我"
        WeiXinMainPage.verify()
                .clickMeButton();

        //校验"我"页面，打开"朋友圈"
        WeiXinMePage.verify()
                .clickMoment();

        //校验"朋友圈页面"，下划一段距离，然后打开带图片的朋友圈
        WeiXinMomentPage.verify()
                .scroll()
                .clickMyMoment();

        Driver.sleep(10);
    }
```

## 设计理念
* 应用Page Object模式提高UI页面操作代码的复用度
* 用Driver类封装所有用到的Appium API, 框架中其它类只通过Driver调用Appium的方法，这种作法会有以下两点好处：
*      一、屏蔽对Appium API的依赖，如果Appium的某个API官方废弃了，只需修改Driver类封装的相应方法即可
*      二、如果将Appium换成Macaca或其它框架，除了改动Driver类 其它类无需改动
* 在Driver中用findElementById等封装对iOS和Android的元素查找，提高代码的复用，尽可能的避免iOS与Android因查找元素方式不同而写相似的代码
* 该框架适用于同一个APP, Android和iOS UI结构基本一致的情况

## 一些原则
* Page类的构造函数用Verify代替
* Page类的构造函数用findElementByID等函数能过检查页面关键元素来判断当前页面是不否为期望的Page
* 依照SRP原则，Page类内的函数 只返回当前类实例（this)或void， 不返回其它页面的对象，确保每个Page的单一职责且不依赖于任何其它Page

```aidl
//朋友圈的Page类
public class WeiXinMomentPage extends BasePage {

    //能过静态方法返回页面实例
    public static WeiXinMomentPage verify(){
        if( !Util.isAndroid() ) {
            //默认情况下写的Page类是Android的UI
            //若Android与iOS UI上有差异，需继承Android的Page类再写个iOS Page
            return new WeiXinMomentPageiOS();
        }

        return new WeiXinMomentPage();
    }

    //不允许调用构造函数
    protected WeiXinMomentPage(){
        Driver.findElementByText(getRes("MOMENT_PAGE_ME_TEXT"));
    }
    
    //所有成员函数只返回this或void,确保每个Page类的独立性，不依赖于任何其它Page类
    public WeiXinMomentPage scroll(){
        Driver.scrollUp();

        return this;
    }
    
    public WeiXinMomentPage clickMyMoment(){
        MobileElement elem = Driver.findElemByIdWithoutException (getRes("MY_POST_PAGE_MOMENT_PIC_ID"));

        if(elem == null){
            elem = Driver.findElementById(getRes("MY_POST_PAGE_MOMENT_ARTICLE_ID"));
        }

        elem.click();

        return this;
    }
}

```
## 类
* Driver : 封装所有用到的Appium方法。作用屏蔽对Appium的依赖、提供更方便的函数。
* BasePage : 所有Page类的基类
* BaseTest : 所有Test类的基类
* ConfigUtil : 读取config.yml配置文件
* ResourceUtil : 读取*.RES.yml资源配置文件
* Util : 工具类，提供一些通用方法
* PageUtil : 封装进入某个页面的方法，方便复杂test case的编写
* TestListener : 监听测试结果，用例执行失败时截图


## 配置文件 
* Config.yml 运行测试时的一些配置项 如包名，重试次数等等。 详见Config.ym内的注释


## 资源文件（具体使用方法见demo）
* 为每个元素新建一个便于辨识的名字，用这个名字统一Android/iOS待查找元素, 然后将不同系统找中该名字的元素对应的值写入相应的RES.yml中
* AndroidRES.yml 写入Android元素查找时需要用到的值
* IOSRES.yml 写入iOS元素查找时需要用到的值

```aidl
AndroidRES.yml

MAIN_PAGE_WEIXIN_TEXT: '微信'
MAIN_PAGE_CONTACT_TEXT: '通讯录'
MAIN_PAGE_DISCOVER_TEXT: '发现'
MAIN_PAGE_ME_TEXT: '我'

ME_PAGE_MY_POST_TEXT: '相册'
MOMENT_PAGE_ME_TEXT: '我的相册'

MY_POST_PAGE_MOMENT_PIC_ID: 'com.tencent.mm:id/dep'
MY_POST_PAGE_MOMENT_ARTICLE_ID: 'com.tencent.mm:id/yk'
```

```aidl
IOSRES.yml

MAIN_PAGE_WEIXIN_TEXT: '微信'
MAIN_PAGE_CONTACT_TEXT: '通讯录'
MAIN_PAGE_DISCOVER_TEXT: '发现'
MAIN_PAGE_ME_TEXT: '我'

ME_PAGE_MY_POST_TEXT: '相册'
MOMENT_PAGE_ME_TEXT: '我'

MY_POST_PAGE_MOMENT_PIC_ID: 'visible == true AND type == "XCUIElementTypeStaticText" AND name CONTAINS "月"'
```

## 测试用例集 
* 框架通过读取 task目录下的xml 运行指定的测试用例


```
在任务的xml中有四个值需要配置
1. port : Appium 端口   
2. udid : 设备ID
3. wdaPort : iOS设备运行的时的WDA port
4. class : 待运行的测试类

测试执行时输入的xml样例

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
* 方式一 ： 将工程打成Jar包，然后运行命令 java -jar UIAutomation-1.0-fat-tests  ./task/demo.xml
* 方式2  ： IDEA中 右键单击demo.xml ,选择运行。见下图
![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/Run-By-IDEA.png)
![](/uploads/photo/2018/35b5c6b0-4917-4805-b004-1c14aa7a2a38.png!large)


## 参考文档
* Page Object https://blog.csdn.net/qq_37546891/article/details/79037299
* How can I configure the maven shade plugin to include test code in my jar? https://code.i-harness.com/en/q/4e91ca