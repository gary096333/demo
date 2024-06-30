<template>
  <div id="app">
    <!-- 頭部 header-->
    <!-- margin-bottom  下框-->
    <div style="height: 60px; line-height: 60px; background-color: white; margin-bottom: 2px; display: flex">

      <div style="width: 300px">
        <img src="@/assets/logo.png" alt="" style="width: 40px; position: relative; top:10px; left: 20px">
        <span style="margin-left: 25px; font-size: 24px">圖書館</span>
      </div>

<!--      右上角登出位置(顯示登入後之帳號名稱)-->
      <!--       {{user.username}}會顯示登入後之帳號(username)名稱-->
      <div style="flex: 1; text-align: right; padding-right: 20px">
        <el-dropdown size="medium">
          <!--          style="cursor: pointer" 鼠標變小手形狀-->
          <span class="el-dropdown-link" style="cursor: pointer">
            <i class="el-icon-user-solid" ></i>
           {{admin.username}}
            <i class="el-icon-arrow-down el-icon--right"></i>
         </span>

          <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
            <el-dropdown-item>
              <div style="width: 50px; text-align: center;" @click="logout" >登出</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </div>

    <!--    側邊欄+主體-->
    <div style="display: flex">

      <!--  側邊欄導航-->
      <div
          style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; margin-left: 2px; background-color: white">

        <!--NavMenu 导航菜单 從Element ui 複製修改-->
        <!--(default-opened*s) ="['/sub']" OR ="['user,admin']" 設定當前副目錄保持開啟-->
        <el-menu :default-active="$route.path " router class="el-menu-demo" style="margin-bottom: 10px">

          <el-menu-item index="/home">
            <!--    element ui    圖標-->
            <i class="el-icon-house"></i>
            <span>首頁</span>
          </el-menu-item>

          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-question"></i>
              <span>會員管理</span>
            </template>
            <el-menu-item index="/addUser">會員添加</el-menu-item>
            <el-menu-item index="/userList">會員列表</el-menu-item>  <!-- 藉由前端路由 index.js 點擊會員列表就能看到 User.vue所呈現的東西-->
          </el-submenu>

          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理員管理</span>
            </template>
            <el-menu-item index="/addAdmin">管理員添加</el-menu-item>
            <el-menu-item index="/adminList">管理員列表</el-menu-item><!-- 藉由前端路由 index.js 點擊管理員列表就能看到 List.vue所呈現的東西-->
          </el-submenu>

          <el-submenu index="category">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>圖書分類管理</span>
            </template>
            <el-menu-item index="/addCategory">圖書分類添加</el-menu-item>
            <el-menu-item index="/categoryList">圖書分類列表</el-menu-item>  <!-- 藉由前端路由 index.js 點擊會員列表就能看到 List.vue所呈現的東西-->
          </el-submenu>

          <el-submenu index="book">
            <template slot="title">
              <i class="el-icon-notebook-1"></i>
              <span>圖書管理</span>
            </template>
            <el-menu-item index="/addBook">圖書添加</el-menu-item>
            <el-menu-item index="/bookList">圖書列表</el-menu-item>  <!-- 藉由前端路由 index.js 點擊會員列表就能看到 List.vue所呈現的東西-->
          </el-submenu>

          <el-submenu index="borrow">
            <template slot="title">
              <i class="el-icon-shopping-bag-2"></i>
              <span>借書管理</span>
            </template>
            <el-menu-item index="/addBorrow">借書添加</el-menu-item>
            <el-menu-item index="/borrowList">借書列表</el-menu-item>  <!-- 藉由前端路由 index.js 點擊會員列表就能看到 List.vue所呈現的東西-->
          </el-submenu>

          <el-submenu index="retur">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>還書管理</span>
            </template>
            <el-menu-item index="/returList">還書列表</el-menu-item>  <!-- 藉由前端路由 index.js 點擊會員列表就能看到 List.vue所呈現的東西-->
          </el-submenu>


        </el-menu>
      </div>
      <!--主體數據，App.vue展示一級路由的頁面-->
<!--      width: 0;[左邊列表固定] 可以限制容器的寬度(中間的表單)，不被子元素(中間的表格)無限撐開-->
      <div style="flex: 1; width: 0; background-color: white; padding: 10px">
        <router-view/>   <!--  router-view 在主路由中顯示次級路由頁面-->
      </div>
    </div>
  </div>

</template>


<script >

import Cookies from "js-cookie";

export default {
  name:"Layout.vue",

  data(){
    return{
      //獲取瀏覽器用戶數據(獲取Cookie數據)
      //如果有用戶對象，就解析成JSON，沒有傳空值(沒有判斷的話，如果沒有會報錯)
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }

  },

  methods:{

    logout(){
      Cookies.remove('admin') //清除瀏覽器用戶數據(刪除Cookie數據)
      this.$router.push('/login') // 登出後返回 /login 頁面

    }
  }


}
</script>