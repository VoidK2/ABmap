### 文件结构
> `app`
> >`libs`
> > >`account` 登录注册使用的库
> > >
> > >`other` 百度和高德相关库
> > >
> >`src`
> > >`main/java` java代码
> > > > `com.amap` 高德地图重绘和自定义接口代码
> > > >
> > > > `com.baidu` 百度地图重绘和自定义接口代码
> > > >
> > > > `com.majie` app主要代码
> > > > >`abmap`
> > > > > >`activity` 主要activity代码
> > > > > >
> > > > > > `adapter` 使用adapter装载数据，把res的资源装载到activuty
> > > > > >
> > > > > > `base` activity跳转、生命周期管理
> > > > > >
> > > > > > `fragment` fragment主要代码
> > > > > >
> > > > > > `interacter` 交互跳转逻辑代码
> > > > > >
> > > > > > `listener` 罗盘模式，处理本机传感器代码
> > > > > >
> > > > > > `model` 持久化，po类
> > > > > >
> > > > > > `tools` webActivity的工具类
> > > > > >
> > > > > > `utils` 常用工具类
> > > > > >
> > > > > > `view` listView的滚动加载相关生命周期
> > > > > >
> > > > `BApp.java` 加载百度和高德
> > >
> > >` main/res` 图标和布局资源
> > >
> > > `main/androidManifest.xml` app声明
> > >
> > `build.gradle` app项目构建设置
> >
> `Bmap.iml` idea的设置
>
> `build.gradle` 项目构建设置
>
> `debug.keystore` 存储百度和高德的项目打包密钥（SHA1密钥）

### 注意事项
- 在自己电脑上运行需要修改`ABmap\app\src\main\java\com\majie\abmap\utils\WebVerify.java`中的PcPath路径，改成自己的webService地址