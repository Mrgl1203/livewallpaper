livewallpaper
==============
一个动态壁纸demo
---------------
动态壁纸大致步骤：<br>
（1）建一个service继承WallpaperService，创建Engine的子类，在service的onCreateEngine中return<br>
（2）然后在AndrodManifest.XML文件的<service>标签内定义动态壁纸的服务程序LiveWallpaper.java和动态壁纸的资源来源“/res/XML/liveWallpaper.XML”<br>

`● 效果如图：`<br>



