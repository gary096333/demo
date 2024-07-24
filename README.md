
# [圖書館理系統]
## 	:white_check_mark: 項目介紹
本圖書館管理系統採用了前後端分離的設計。客戶端使用 Vue.js 和 Element UI 框架實現，服務端則使用 Spring Boot 和 MyBatis 實現，數據庫使用 MySQL。最後，項目部署到 AWS 的雲端虛擬機上。
## :white_check_mark: 功能

:globe_with_meridians:前往網站: http://54.199.152.2/

:lock:帳號密碼 
```bash
帳號：test
密碼：123
```
### 整體
- [x] 登入/登出
- [x] 首頁
- [x] 會員管理:添加、搜索/重置、編輯、刪除、充值、禁用帳戶(當積分<0)
- [x] 管理員管理:添加、搜索/重置、編輯、刪除、修改密碼、禁用帳戶
- [x] 圖書分類管理:添加、搜索/重置、編輯、刪除、添加/編輯/刪除二級分類
- [x] 圖書管理:添加(上傳封面、分類添加)、搜索/重置、編輯、刪除
- [x] 借書管理:借書添加、搜索/重置、刪除、顯示借書狀態、還書
- [x] 還書管理:搜索/重置、刪除

### 後端
- 增刪改查api接口
- 全局異常處理
- JWT-token後端權限控制
- 跨域 CORS統一配置

## :white_check_mark: 技術

### 前端
- Vue、Axios、Element UI、Vue-router
### 後端
- SpringBoot 、 MyBatis 、 Maven 、 MySQL 、 Hutool、Lombok、pagehelper
### 部屬
- AWS雲端虛擬機(EC2服務部屬)


## 	:white_check_mark: 項目預覽
|<img src = "https://github.com/gary096333/test/blob/master/%E5%9C%96%E6%9B%B8%E7%AE%A1%E7%90%86%E7%B3%BB%E7%B5%B1.png">|<img src="https://github.com/gary096333/test/blob/master/login.png">|<img src="https://github.com/gary096333/test/blob/master/userList.png" >|
|--|--|--|

## 	:white_check_mark: 開發環境

JDK： jdk-1.8

Maven:v3.8.8

node：v20.11.0

vue:v5.0.8

mysql：mysql-8.0.35

IDE：IntelliJ IDEA 2023、VSCode

## 	:white_check_mark: 運行(本地運行)


1、下載項目到本地
```bash
git clone https://github.com/gary096333/demo.git
```
>**本地運行使用localhost文件夾**

<br/>

2、修改配置文件

(1)創建數據庫 將文件夾中的 `mydatabase-202406300754.sql` 文件導入數據庫。
(2)更改'application.yml'檔中資料庫配置，於`localhost/springboot/src/main/resources/application.yml`路徑下

<br/>

3、啟動項目

- **啟動客戶端**：進入 `localhost/element_demo3` 文件夾，運行下面命令

```
npm install // 安裝依賴

npm run serve // 啟動前端項目
```
- **啟動服務端**：進入 `localhost/springboot` 文件夾，運行下面命令

```
mvn spring-boot:run // 啟動後端項目
```
>**需安裝 [maven](https://maven.apache.org/download.cgi) (本項目使用版本 v3.8.8)**










