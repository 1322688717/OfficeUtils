# pdf转world工具

## 前言

### 开发背景

* 由于找不到工作所以准备自己创业

### 市场前景

* 关于pdf转world，excel ，ppt ，HTML市场一直都有这个需求，且需求量巨大，所以开始着手此项目

### 达到效果

* ui界面 简易实用合理
* 功能完善且稳定
* app不存在任何崩溃或者bug

### 市场需求

* 定位到学生 老师  职场工作人员等



## 环境搭建及服务器部署

#### 接口选择

* 腾讯接口
  *   https://market.cloud.tencent.com/products/18525
  * 0.01元一次

* Textln接口
  * https://www.textin.com/market/chager/image-to-pdf
  * 每次0.085元一次
* **度慧文档转PDF**
  * https://market.cloud.tencent.com/products/18764#spec=0.00%E5%85%83%2F100%E6%AC%A1
  * 0.015每次 
* **九云图文档转PDF API**
  * https://market.cloud.tencent.com/products/35566
  * 0.01元每次

* 印了么

  * https://www.pdftodoc.cn/bd/api/

  * 0.35元一次

    ##### *经调研**九云图文档转PDF API**接口性价比最高故使用*

#### 服务器选择

* 阿里云
* 1核2g  宽带1m  云硬盘40g 期限一年  356元

* 腾讯云

  * https://cloud.tencent.com/act/pro/2022double11_warmup?channel=sp&fromSource=gwzcw.6889112.6889112.6889112&utm_medium=cpc&utm_id=gwzcw.6889112.6889112.6889112&bd_vid=12018919071474196737
  * 50元2核2G4M  40GB SSD盘 300GB月流量 4M带宽

  ##### 经调研腾讯云服务器性价比最高故使用

#### 短信接口

* 阿里云
  * https://www.aliyun.com/product/sms?spm=5176.21213303.J_6704733920.7.1a1653c9hppp0n&scm=20140722.S_product%40%40%E4%BA%91%E4%BA%A7%E5%93%81%40%4080687._.ID_product%40%40%E4%BA%91%E4%BA%A7%E5%93%81%40%4080687-RL_%E7%9F%AD%E4%BF%A1-LOC_main-OR_ser-V_2-P0_0
  * 大约0.03每条
* **三网短信通知**
  * https://market.cloud.tencent.com/products/32818
  * 约0.038一次

经调研阿里云短线接口性价比较高故使用

#### ui设计

* 由于前期资金缺乏，暂时不提供ui设计

#### 原型设计

* 由于前期资金缺乏，暂时不提供原型

#### 开发环境

* jdk   
  * java11
* sdk
  * Android SDK
* 开发工具
  * Android studio

## 产品信息

* 包名：com.example.officeutils
* APPID：com.example.officeutils

## 产品设计思路

* 完全由Google推荐使用的kotlin语言编程
* 由Google推出的jecpack组件来实现
  * https://blog.csdn.net/qq_35101450/article/details/123437481?ops_request_misc=&request_id=&biz_id=102&utm_term=jectpack%E7%94%A8%E6%B3%95&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-1-123437481.142^v59^pc_rank_34_queryrelevant25,201^v3^control_1&spm=1018.2226.3001.4187
* viewbind的使用
* https://blog.csdn.net/zhangying1994/article/details/118862524?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522166619487016800182718664%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=166619487016800182718664&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-118862524-null-null.142^v59^pc_rank_34_queryrelevant25,201^v3^control_1&utm_term=android%20viewbinding&spm=1018.2226.3001.4187
  * buildFeatures {
        viewBinding true
    }
* databinding的使用
  * https://blog.csdn.net/Mr_Tony/article/details/124199278?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522166619498216781432964270%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=166619498216781432964270&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-1-124199278-null-null.142^v59^pc_rank_34_queryrelevant25,201^v3^control_1&utm_term=databinding%E4%BD%BF%E7%94%A8&spm=1018.2226.3001.4187
  *  android {
            ...
         buildFeatures {
                dataBinding = true
                //viewBinding = true
            }
     }
  * life
* 设计模式
  * 单例模式
* 项目结构
  * 采用MVVM
* 项目配置
