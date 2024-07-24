import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import AddUser from "@/views/user/AddUser"
import AddAdmin from '@/views/admin/Add.vue'
import Cookies from "js-cookie";


Vue.use(VueRouter)



const routes = [


  {
    path: '/',   //主路由(Layout.vue包含頭部+側邊欄+主體)
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [  //顯示次級路由(點擊後跳轉與主路由同個頁面)

      //---首頁---
      {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/home/HomeView.vue'),

      },
      //---User---
      {
        path: '/userList',
        name: 'UserList',
        component: () => import('@/views/user/User.vue')

      },
      {
        path: '/addUser',
        name: 'AddUser',
        component: AddUser
      },
      {
        path: '/editUser',
        name: 'EditUser',
        component: () => import('@/views/user/EditUser.vue')
      },
      //---Admin---
      {
        path: '/adminList',
        name: 'AdminList',
        component: () => import('@/views/admin/List.vue')

      },
      {
        path: '/addAdmin',
        name: 'AddAdmin',
        component: AddAdmin
      },
      {
        path: '/editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin/Edit.vue')
      },
      //---Category---
      {
        path: '/categoryList',
        name: 'CategoryList',
        component: () => import('@/views/category/List.vue')

      },
      {
        path: '/addCategory',
        name: 'AddCategory',
        component: () => import('@/views/category/Add.vue')
      },
      {
        path: '/editCategory',
        name: 'EditCategory',
        component: () => import('@/views/category/Edit.vue')
      },

      //---Book---
      {
        path: '/bookList',
        name: 'BookList',
        component: () => import('@/views/book/List.vue')

      },
      {
        path: '/addBook',
        name: 'AddBook',
        component: () => import('@/views/book/Add.vue')
      },
      {
        path: '/editBook',
        name: 'EditBook',
        component: () => import('@/views/book/Edit.vue')
      },
      //---Borrow---
      {
        path: '/borrowList',
        name: 'BorrowList',
        component: () => import('@/views/borrow/List.vue')

      },
      {
        path: '/addBorrow',
        name: 'AddBorrow',
        component: () => import('@/views/borrow/Add.vue')
      },


      //---Return 還書---
      {
        path: '/returList',
        name: 'ReturList',
        component: () => import('@/views/retur/List.vue')
      },


    ]
  },

  //---login---
  {
    path: '/login',  //主路由(獨自頁面)
    name: 'Login',
    component: () => import('@/views/login/Login.vue'),

  },
  //---404 不存在路徑---
  {
    path: '*',
    name: '404',
    component: () => import('@/views/404.vue')
  }

]

const router = new VueRouter({
  base: process.env.BASE_URL,
  routes
})

//router.beforeEach 路由守衛(不用觸發請求)
//Cookies要有admin名稱的用戶數據，才能訪問其他路徑頁面，否則強制跳轉到 /login頁面

router.beforeEach((to, from, next) => {
  //如是 /login路徑，跳轉到/login頁面
  if (to.path === '/login') {
    next()
  }


  const admin = Cookies.get("admin")

  //如Cookies 沒有admin名稱的用戶數據，強制跳轉到 /login頁面
  if (!admin && to.path !== '/login') {
    return next("/login")
  }

  //如Cookies 有admin名稱的用戶數據，可訪問其他路徑頁面
  next()
})

export default router
