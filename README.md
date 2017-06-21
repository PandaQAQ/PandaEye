# 简介
熊猫眼是一个平时用来学习的练手的项目，做这样一个应用的目的主要有两个：
- 公司项目因为历史原因还有风险控制方面的问题，新的技术不一定能够应用在现有的版本上。所以手痒了就自己弄个应用写一写，持续的更新增加自己的技能熟练度。同时也把坑踩一踩，以后用到的时候可以少走绕路。
- 像之前的 [MVPDemo][1] 这个仓库，只是进行单一功能的验证测试。写得比较杂乱，慢慢的会失去写下去的热情。所以萌生了写个稍微完整一点的应用的想法。
# FIX LIST
- 修复 Android 7.0 华为荣耀8系统自带壁纸裁剪 FileProvider 路径报错BUG
- 调整工程包结构，使之逻辑更清晰符合 Google 官方示例的结构
# TODO LIST
- m3u8 视频离线缓存
- lottie 优化加载动画

# 仓库地址
[https://github.com/PandaQAQ/PandaEye][0]


# APK 下载
[PandaEye（非最新代码打包）][2]

# 界面功能
<div width = "300" height = "300" align="center">
<img src="http://oddbiem8l.bkt.clouddn.com/ezgif.com-video-to-gif.gif" width = "200" height = "300" alt="动态图总览"/>
<img src="https://user-gold-cdn.xitu.io/2017/4/5/91fa0196ce8a1d224d88fccd6f5b7b1a.png" width = "200" height = "300" alt="侧滑菜单"/>
</div>

### 知乎日报
<div width = "300" height = "300" align="center">
<img src="https://user-gold-cdn.xitu.io/2017/4/5/d5c265c122a2e6d9dda520ae5d3492b0.png" width = "200" height = "300" alt="知乎日报列表"/>
<img src="https://user-gold-cdn.xitu.io/2017/4/5/b9a3ef950ff72637b7d8543ac7d9e194.png" width = "200" height = "300" alt="知乎日报内容"/>
</div>

### 网易新闻
<div width = "300" height = "300" align="center">
<img src="https://user-gold-cdn.xitu.io/2017/4/5/cd53d5a0ce9358046102d0ea4675b9f4.png" width = "200" height = "300" alt="新闻列表"/>
<img src="https://user-gold-cdn.xitu.io/2017/4/5/4dc9a69d03a70c7ee67772a63df4d14b.png" width = "200" height = "300" alt="新闻内容"/>
</div>

### 视频
<div width = "300" height = "300" align="center">
<img src="https://user-gold-cdn.xitu.io/2017/4/5/add239969036cb88027394fab7546cff.png" width = "200" height = "300" alt="侧滑菜单"/>
<img src="https://user-gold-cdn.xitu.io/2017/4/5/d41bab28ce5cf3ff8aac05c811c732fb.png" width = "200" height = "300" alt="侧滑菜单"/>
</div>

# 技术点
### 整体开发模式
- MVP

### 网络请求及异步处理：
- RxJava2+Retrofit2（早前使用RxJava+Retrofit，可以回滚查看）
- Okhttp
- Gson
- RxBus 实现事件总线功能

### 图片加载：
- Picasso

### 新闻数据缓存：
- DiskLurCache （PandaEye 中我对 DiskLruCache 进行了简单的封装，使用类似于 SharePrefenrence）

### 视频播放器 ：
- JieCaoPalayer

### 内存泄漏检测 ：
- leakcanary

### 其他：
- 图片毛玻璃效果（头像背景效果）
- 自定义封装的 RecyclerView： [MagicRecyclerView][7] 支持头部底部和标签
- 自定义 behavior 实现滑动时底部导航栏的隐藏显示效果
- Android N 文件读写适配
- Android M+ 运行时权限申请封装
-  分享 ShareSDK 接入（分享平台未进行注册申请，后续再完善）
-  自定义相册图片选择
-  Activity 右滑返回

# License
``` html
Copyright 2017 PandaQ.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

  [0]: https://github.com/PandaQAQ/PandaEye
  [1]: https://github.com/PandaQAQ/MvpDemo
  [2]: http://oddbiem8l.bkt.clouddn.com/app-release.apk
  [3]: https://user-gold-cdn.xitu.io/2017/4/5/d5c265c122a2e6d9dda520ae5d3492b0.png
  [4]: https://user-gold-cdn.xitu.io/2017/4/5/cd53d5a0ce9358046102d0ea4675b9f4.png
  [5]: https://user-gold-cdn.xitu.io/2017/4/5/add239969036cb88027394fab7546cff.png
  [6]: https://user-gold-cdn.xitu.io/2017/4/5/d41bab28ce5cf3ff8aac05c811c732fb.png
  [7]: https://github.com/PandaQAQ/PandaEye/tree/master/pandaqlib/src/main/java/com/pandaq/pandaqlib/magicrecyclerView
