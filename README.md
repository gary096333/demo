
# [圖書館理系統]
## 項目介紹
本圖書館管理系統採用了前後端分離的設計。客戶端使用 Vue.js 和 Element UI 框架實現，服務端則使用 Spring Boot 和 MyBatis 實現，數據庫使用 MySQL。最後，項目部署到 AWS 的雲端虛擬機上。
## 功能

:globe_with_meridians:前往網站: http://54.199.152.2/

:lock:帳號密碼 
```bash
帳號：test
密碼：123
```
- [x] 登入/登出
- [x] 首頁
- [x] 會員管理:添加、搜索/重置、編輯、刪除、充值、禁用帳戶(當積分<0)
- [x] 管理員管理:添加、搜索/重置、編輯、刪除、修改密碼、禁用帳戶
- [x] 圖書分類管理:添加、搜索/重置、編輯、刪除、添加/編輯/刪除二級分類
- [x] 圖書管理:添加(上傳封面、分類添加)、搜索/重置、編輯、刪除
- [x] 借書管理:借書添加、搜索/重置、刪除、顯示借書狀態、還書
- [x] 還書管理:搜索/重置、刪除

## 項目預覽
|<img src = "https://github.com/gary096333/test/blob/master/%E5%9C%96%E6%9B%B8%E7%AE%A1%E7%90%86%E7%B3%BB%E7%B5%B1.png">|<img src="https://github.com/gary096333/test/blob/master/login.png">|<img src="https://github.com/gary096333/test/blob/master/userList.png" >|
|--|--|--|

## 安裝

> 請務必依據你的專案來調整內容。
以下將會引導你如何安裝此專案到你的電腦上。

Node.js 版本建議為：`16.15.0` 以上
Node.js 版本建議為：`16.15.0` 以上...

### 取得專案

@@ -40,7 +56,7 @@ git clone git@github.com:hsiangfeng/README-Example-Template.git
cd README-Example-Template
```

### 安裝相依套件
### 安裝套件

```bash
npm install
@@ -69,6 +85,7 @@ http://localhost:8080/
```env
APIPATH= # API 位置
COUSTOMPATH= # 自訂變數
...
```

## 資料夾說明
@@ -106,6 +123,7 @@ COUSTOMPATH= # 自訂變數
- 編譯程式碼
- 執行 ESLint 掃描
- 執行測試
...

當專案 merge 到 main 時會自動執行以下動作：

@@ -115,3 +133,15 @@ COUSTOMPATH= # 自訂變數
- 執行 ESLint 掃描
- 執行測試
- 部署到 Github Pages
...

## 聯絡作者

> ps. 這邊絕對不是業配，而是要適當提供一些方式讓觀看者知道你的聯絡方式，讓他們可以更方便的找到你。
你可以透過以下方式與我聯絡

- [部落格](https://israynotarray.com/)
- [Facebook](https://www.facebook.com/israynotarray)
- [Instagram](https://www.instagram.com/isray_notarray/)
...