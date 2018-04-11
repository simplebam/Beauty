# Beauty
五次元妹子 是一款看妹子的APP

* 仿照开源项目["五次元妹子"App](https://github.com/DanteAndroid/Beauty)
* 目前建议你们还是模仿原项目,原作者的项目问题反馈群:459232356


### 项目截图
<a href="./art/meizi.png"><img src="./art/meizi.png" width="40%"/></a><img height="0" width="8px"/><a href="./art/setting.png"><img src="./art/setting.png" width="40%"/></a>

体验地址: [app-release-v1.0.apk](https://github.com/simplebam/Beauty/releases/download/v1.0/app-release-v1.0.apk)

### 项目中用到的知识
* 命名规范-这里主要参考Blankj:[Android 开发规范（完结版） - 简书](https://www.jianshu.com/p/45c1675bec69)
* Android开发的框架:[死磕 Fragment 的生命周期 - MeloDev的博客 - CSDN博客](http://blog.csdn.net/MeloDev/article/details/53406019)
  别看名字是这样写的,但他写的项目框架是很好的,源码:[itsMelo/BuzzerBeater](https://github.com/itsMelo/BuzzerBeater)
  * 关于BaseActivity跟BaseFragment的封装:[从BaseActivity与BaseFragment的封
    装谈起 - Young_Kai]( http://blog.csdn.net/tyk0910/article/details/51355026)
* 设计模式:
  * 单例模式:[你真的会写单例吗 | Blankj's Blog]( https://blankj.com/2016/04/21/really-use-singleton/)
  * 视频版本:[模式宗师养成宝典之Java版_学习路径_慕课网](http://www.imooc.com/course/programdetail/pid/18)
  * 推荐设计模式书籍:[《大话设计模式》](http://www.java1234.com/a/javabook/javabase/2016/1106/7052.html)
* Http知识:
  * [你应该知道的计算机网络知识 - 简书](https://www.jianshu.com/p/21b5cbac0849)
  * [Android中的Http通信（一）之Http协议基本知识 - CSDN博客](http://blog.csdn.net/u014544193/article/details/49849843)
  * [Android之基于HTTP协议的通信详解 - 一叶飘舟 - 博客园](http://www.cnblogs.com/jdsjlzx/archive/2011/07/25/2116351.html)
* Gradle知识:
  * [一套源码编译多个APP，不同的签名，包名，界面，字段... - 简书 ](https://www.jianshu.com/p/4a5625a00f8d)
  * [Android 开发之版本统一规范 | Blankj's Blog](https://blankj.com/2016/09/21/android-keep-version-unity/)
  * Android Studio使用lambda表达式:[在Android Studio中使用Lambda表达式 -
    南极冰川雪 - 博客园](https://www.cnblogs.com/rainboy2010/p/6476076.html)
* Java基础:
  * 使用Okio进行读写:[大概是最完全的Okio源码解析文章 - 简书](https://www.jianshu.com/p/f033a64539a1)
* Android基础:
  * Android基础知识复习:[尚硅谷15天Android基础(复习笔记) - CSDN博客](http://blog.csdn.net/simplebam/article/details/70213167)
  * 四大组件:
     * Activity:
        * 启动模式:[Activity的四种启动模式-图文并茂 – Android开发中文站](http://www.androidchina.net/3173.html)
        * 状态保存与恢复:[Android Activity 和 Fragment 状态保存与恢复的最佳实践](https://www.jianshu.com/p/45cc7775a44b)
        * 动画切换:[酷炫的Activity切换动画，打造更好的用户体验 - 简书](https://www.jianshu.com/p/37e94f8b6f59)
        * 标签属性:[Android Activity标签属性 - 简书](https://www.jianshu.com/p/8598825222cc)
     * PreferenceActivity:
        * [Android开发之PreferenceActivity的使用 - 简书](https://www.jianshu.com/p/4a65f4a912c6)
        * [Preference 三种监听事件说明 - wangjicong_215的博客 - CSDN博客](http://blog.csdn.net/wangjicong_215/article/details/52209380)
     * Fragment
        * [实现Activity和Fragment之前通信 - CSDN博客](http://blog.csdn.net/xuxian361/article/details/75529810)
        * [死磕 Fragment 的生命周期 - MeloDev的博客 - CSDN博客](http://blog.csdn.net/MeloDev/article/details/53406019)
        * [android fragment onHiddenChanged的使用 - CSDN博客](http://blog.csdn.net/bzlj2912009596/article/details/62851537)
           ,这里是为了解释第二篇博文准备的
        * [Fragment的setUserVisibleHint方法实现懒加载，但
          setUserVisibleHint 不起作用？ - Leevey·L - 博客园](http://www.cnblogs.com/leevey/p/5678037.html)
          ,这里是为了解释第二篇博文准备的
        * [TabLayout使用详解 - 简书](https://www.jianshu.com/p/7f79b08f5afa)
          ,这里是为了解释第二篇博文准备的
        * [套在ViewPagerz中的Fragment在各种状态下的生命周期 - CSDN博客](http://blog.csdn.net/jemenchen/article/details/52645380)
        * [Android -- Fragment 基本用法、生命周期与细节注意 - 简书](https://www.jianshu.com/p/1ff18ec1fb6b)
        * [Fragment全解析系列（一）：那些年踩过的坑 - 简书](https://www.jianshu.com/p/d9143a92ad94)
          ,这系列的四篇都很经典,建议你可以看看
        * 还不知道怎么入门解析Fragment的可以看我的面经,里面涉及了(卖个广告),
          [Android面经-基础篇(持续更新...) - CSDN博客](http://blog.csdn.net/simplebam/article/details/77989675)
        * 关于保存和恢复Fragment目前最正确的状态:[The Real Best Practices to Save/Restore Activity's and Fragment's state. (StatedFragment is now deprecated)](https://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en)
  * Material Design:
    * [Android Theme.AppCompat 中，你应该熟悉的颜色属性 - 简书 ](https://www.jianshu.com/p/15c6397685a0)
      这家伙的关于MD文章也是值得一看的,简短but精辟
    * Toolbar:
        * [ToolBar使用心得(如何改变item的位置) - 泡在网上的日子](http://www.jcodecraeer.com/plus/view.php?aid=7667)
        * [Toolbar+DrawerLayout+NavigationView使用心得](http://www.jcodecraeer.com/a/anzhuokaifa/2017/0317/7694.html)
        * [Android ToolBar 使用完全解析 - 简书]( https://www.jianshu.com/p/ae0013a4f71a)
    * CoordinatorLayout(本身就是一个加强版的FrameLayout)可以监听其所有子控件
      的各种事件,然后自动帮助我们做出最为最为合理的响应 <--(寄生) AppBarLayout
      (垂直的LinearLayout加强版),它在内部做了很多滚动事件的封装
      <--(寄生) CollapsingToolBarLayout(可折叠式标题栏)
        * CoordinatorLayout:[CoordinatorLayout与滚动的处理-泡在网上的日子](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0717/3196.html)
        * DrawLayout:
          * [android官方侧滑菜单DrawerLayout详解 - 泡在网上的日子](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0925/1713.html)
          * [用Android自带的DrawerLayout和ActionBarDrawerToggle实现侧滑效果 - CSDN博客](http://blog.csdn.net/miyuexingchen/article/details/52232751)
          * [Drawer 详解 ·Material Design Part 3 - Android - 掘金](https://juejin.im/entry/5825c76d67f3560058d23657)
    * RecyclerView:
        * [RecyclerView简单使用总结 - 简书](https://www.jianshu.com/p/9b3949f7cb0f)
        * [RecyclerView使用完全指南，是时候体验新控件了（一） - 简书](https://www.jianshu.com/p/4fc6164e4709)
    * SwipeRefreshLayout:
        * [SwipeRefreshLayout详解和自定义上拉加载更多 - 简书 ](https://www.jianshu.com/p/d23b42b6360b)
        * [SwipeRefreshLayout+RecyclerView冲突的几种解决方案 - 简书](https://www.jianshu.com/p/34cbaddb668b)
    * 看不懂物料设计的话建议买郭霖先生的《第二行代码》好一点，这本书内容对于初级
      开发者来说还是蛮不错的
  * 异步消息机制:[Android异步消息处理机制完全解析，带你从源码的角度彻底理解](http://blog.csdn.net/guolin_blog/article/details/9991569)
  * Github知识:
    * stormzhang的开源书籍:[从 0 开始学习 GitHub 系列-CSDN下载 ](http://download.csdn.net/download/simplebam/9745564)
      ,你也可以关注公众号 stormzhang ，id：googdev，聊天页面回复"github"关键
      字，即可获取，完全免费,但我本人感觉他公众号完全发鸡汤,没什么卵用,所以自己
      load下来上传到csdn博客
    * [Git教程 - 廖雪峰的官方网站](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)
      ,我更愿意推荐他的,通俗易懂,我建议可以配合[Pro Git（中文版）](http://git.oschina.net/progit/)一起看效果更佳
    * [github release 功能的使用及问题解决 - Eggy2015的博客 - CSDN博客](http://blog.csdn.net/Eggy2015/article/details/52138751)

### Android开发Tips
* [Android开发一点小技巧和建议献上 - 掘金](https://juejin.im/post/5a66bea86fb9a01caf378d33)
* [Android 开发注意事项 - 简书](https://www.jianshu.com/p/0b40c02b6119)


### 项目中用到的框架
* bugtags-移动时代首选 Bug 管理系统:[Bugtags 使用说明 - CSDN博客](http://blog.csdn.net/ObjectivePLA/article/details/51037804)
* 联网框架:OkHttp
  * [Android -- OkHttp的简单使用和封装 - 阿呆哥哥 - 博客园](https://www.cnblogs.com/wjtaigwh/p/6210534.html)
  * [Android网络编程（六）OkHttp3用法全解析 | 刘望舒的博客](http://liuwangshu.cn/application/network/6-okhttp3.html)
  * [OkHttp使用进阶 译自OkHttp Github官方教程 - GavinCT - 博客园](http://www.cnblogs.com/ct2011/p/3997368.html)
  * [Android okHttp网络请求之Get/Post请求 - 总李写代码 - 博客园](http://www.cnblogs.com/whoislcj/p/5526431.html)
  * 目前市面上流行的Xutils3,OkGo,鸿洋大神封装的OKHttpUtils以及OkHttpFinal,这
    里我主要参考stay4it的文章:[OkHttp, Retrofit, Volley应该选择哪一个？](https://www.jianshu.com/p/77d418e7b5d6)
  * 关于OkHttp3无法再通过OkHttpClient.cancel(tag)形式来取消请求,我身边挺多小
    伙伴纷纷还是使用OkHttp2.x问题,我个人认为技术始终需要更新,并非因为一个简单的
    理由就让你停滞,在这里我参考了以下的文章进行OkHttp封装取消:
    * [关于取消OkHttp请求的问题 - 简书](https://www.jianshu.com/p/b74466039b84)
    * 上面这篇文章评论这句话说的特别有道理:其实cancel网络请求的时候，如果还未和
      服务器建立连接，它会回调到onFailure()方法中，但是还有一种情况就是它会在
      onResponse的时候刚好cancel网络请求，那么它会在onResponse()方法中抛出
      java.net.SocketException: Socket closed
* 解析Json数据:Gson
  * Gson教程(这个作者写的这四篇Gson文章真的很好):[你真的会用Gson吗?Gson使用指
    南（一） - 简书 ](https://www.jianshu.com/p/e740196225a4)
* AndroidUtil- Android 工具类
  * 这里主要使用Blankj大神封装的工具类框架:[Blankj/AndroidUtil](https://github.com/Blankj/AndroidUtilCode)
* ButterKnife
  * [[Android开发] ButterKnife8.5.1 使用方法教程总结 - CSDN博客](http://blog.csdn.net/niubitianping/article/details/54893571)
* Glide
  * [Glide v4 中文官方文档](https://muyangmin.github.io/glide-docs-cn/)
  * [Android图片加载框架最全解析（一），Glide的基本用法 - 郭霖的专栏](http://blog.csdn.net/guolin_blog/article/details/53759439)
    郭霖写的东西都很赞,值得推荐阅读
  * [Google推荐的图片加载库Glide介绍 - 泡在网上的日子](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2650.html)
  * [android图片加载库Glide4使用教程（项目中如何快速将Glide3替换成Glide4） - CSDN博客](https://blog.csdn.net/huangxiaoguo1/article/details/78892262)
  * [Glide最新版V4使用指南 - CSDN博客 ](https://blog.csdn.net/u013005791/article/details/74532091)
* glide-transformations
  * 网上出现了很多Glide的图片变换开源库，其中做的最出色的应该要数glide-transformations这个库了
  * [Android图片加载框架最全解析（五），Glide强大的图片变换功能 - 郭霖](http://blog.csdn.net/guolin_blog/article/details/71524668)
  * [Glide、Picasso、Fresco进阶 - 图像转换 - 简书 ](https://www.jianshu.com/p/976c86fa72bc)
* 还是不会用Glide的话,那么推荐使用[panpf/sketch: Sketch 是 Android 上一个
   强大且全面的图片加载器,支持 GIF，手势缩放以及分块显示超大图片](https://github.com/panpf/sketch)
   无需关心TAG，因为根本就不使用TAG来关联，也自带多种图片处理效果，圆形的、圆角
   的、高斯模糊的等等
* RxJava
   * 目前最好的RxJava入门文章,没有之一:[给初学者的RxJava2.0教程(一) - 简书 ](https://www.jianshu.com/p/464fa025229e)
     以及对应的项目教程源码:[ssseasonnn/RxJava2Demo](https://github.com/ssseasonnn/RxJava2Demo)
   * 其他RxJava文章推荐:[RxJava2 学习资料推荐](http://mp.weixin.qq.com/s/UAEgdC2EtqSpEqvog0aoZQ)
   * [RxJava之过滤操作符 - 行云间 - CSDN博客](http://blog.csdn.net/io_field/article/details/51378909)
   * [Retrofit 1.x和Retrofit 2.x的不同 | Xiaofeng's Blog | Beyond Compare ](http://xiaofeng.site/2016/12/07/Retrofit-1-x%E5%92%8CRetrofit-2-x%E7%9A%84%E4%B8%8D%E5%90%8C/undefined/)
* RxPermissions
   * [RxPermissions获取运行时权限 - 简书](https://www.jianshu.com/p/314e9e27592f)
* Retrofit
   * [你真的会用Retrofit2吗?Retrofit2完全教程 - 简书](https://www.jianshu.com/p/308f3c54abdd)
* RxRxLifeCycle
   * [解决RxJava内存泄漏（前篇）：RxLifecycle详解及原理分析 - CSDN博客 ](https://blog.csdn.net/mq2553299/article/details/78927617)
   * [不继承RxAppCompatActivity的情况下使用RxLifeCycle - CSDN博客 ](https://blog.csdn.net/kevinscsdn/article/details/78980010)
* AlipayZeroSdk-支付宝转账工具类
   * 最轻量的支付宝转账工具类:[AlipayZeroSdk: Lightest Alipay Transfer
     Helper](https://github.com/fython/AlipayZeroSdk)
   * 关于付款第三方SDK,我们开发中原则能够不使用第三方的尽量少涉及第三方,附教程:
     [超详细Android接入支付宝支付实现，有图有真相 - 简书 ](https://www.jianshu.com/p/2aa2e8748476)
* BaseRecyclerViewAdapterHelper-这里使用CymChad的
   * [BRVAH官方使用指南（持续更新） - 简书](https://www.jianshu.com/p/b343fcff51b0)
* iconics-core/google-material-typeface
   * [Android-Iconics - 这是一个字体图标的库 - Android开发社区 | CTOLib码库](http://ruby.ctolib.com/Android-Iconics.html)
* jsoup
   * [Android中jsoup的混淆规则 - CSDN博客 ](https://blog.csdn.net/yanzhenjie1003/article/details/78384725)


### 性能优化
* [Android内存优化(使用SparseArray和ArrayMap代替HashMap) - CSDN博客]( http://blog.csdn.net/simplebam/article/details/73467149)
* [小细节，大用途，35 个 Java 代码性能优化总结！]( http://mp.weixin.qq.com/s/0J1T7zq6zkokQIDyjNxscg)


### 开发中遇到的问题
* [解决 Android 26 无法查看系统源码的问题](https://www.jianshu.com/p/37751162c720)
* 在原项目中 BaseFragment 里面有个方法 "applySchedulers" 里面返回的参数是
  Observable.Transformer,这个是 RxJava1 的写法,我使用了RxJava2,写法是
  ObservableTransformer,随便把 RxJava 线程调度封装成为 RxUtil 工具类
* 原项目使用Timer作为log打印,这里我使用PLog+CrashHanlder结合使用,timer也是一个
  优秀的库,timber-管理Log:[Android日志记录杂谈-Logger,Timber,logback-android ](https://www.jianshu.com/p/39834be3cb6c)
  ,这里一般不建议使用logback-android一起来管理Log,打包报错会出现"opengl-api
  defines classes that conflict with classes now provided by Android"
* 在原项目中没有很好处理 RxJava 等引起的内存泄漏,这里我本来引入 Rxlifecycle
  管理,该开源库的作者不再推荐该库,链接:[为什么不使用 RxLifecycle? - 简书 ](https://www.jianshu.com/p/6627e97eba8d)
  ,在文章中他都推荐使用 AutoDispose,但我对 AutoDispose 库还不是很好熟悉等原因,
  所以继续使用 RxLifecycle + dispose 一起管理
   * AutoDispose:[Android架构中添加AutoDispose解决RxJava内存泄漏 - CSDN博客 ](https://blog.csdn.net/mq2553299/article/details/79418068)
* 在进入应用时候SplashActivity报错"You need to use a Theme.AppCompat theme
   (or descendant) with this activity."这里的意思已经很明白,得用继承自
   AppCompat的主题,下面两篇
   * [You need to use a Theme.AppCompat theme (or descendant) with this
     activity解决方法 - CSDN博客 ](http://blog.csdn.net/zxm317122667/article/details/50984644)
   * [Android问题集锦之二十八：You need to use a Theme.AppCompat theme
     (or descendant) with this activity. - CSDN博客](http://blog.csdn.net/lincyang/article/details/42673151)
   * 以上两篇都可以解决,但我这里感觉好还是没有进行机型适配,所以参考了这篇文章:[Android沉浸式(透明)状态栏适配 - 简书 ](https://www.jianshu.com/p/a44c119d6ef7)



