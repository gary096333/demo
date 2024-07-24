<template>
  <!--  margin: 100px auto 上下100px 左右置中-->
  <!--  overflow: hidden 頁面太大變有卷軸，把超過部分隱藏，卷軸頁面消失-->
  <!--  position: relative 記得加上相對位置，因cover有設置根據相對位置的絕對位置 position: absolute -->
  <div style="height: 100vh; overflow: hidden; position:relative">

    <div style="width: 500px; height: 400px; background-color: white; border-radius: 10px;
     margin: 100px auto; padding: 50px">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: darkolivegreen">登 入</div>


      <el-form :model="admin" :rules="rules" ref="loginForm">

        <el-form-item prop="username">
          <!--        v-model="admin.username" 綁定才能輸入值-->
          <!--        show-password 隱藏密碼-->
          <el-input placeholder="請輸入帳號" prefix-icon="el-icon-user" size="medium" v-model="admin.username"> </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="請輸入密碼" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.password">
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%" size="medium" type="primary" @click="login">登入</el-button>
        </el-form-item>

      </el-form>

    </div>
  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: 'LOGIN',
  data() {
    return {
      admin: {},      //方法名稱(自訂)

      rules: {
        username: [ //資料驗證
          { required: true, message: '請輸入用戶名', trigger: 'blur' },
          { min: 3, max: 10, message: '長度在3~10個字符', trigger: 'blur' }
        ],
        password: [ //資料驗證
          { required: true, message: '請輸入密碼', trigger: 'blur' },
          { min: 3, max: 10, message: '長度在3~10個字符', trigger: 'blur' }
        ]
      },



    }

  },
  methods: {
    login() {

      this.$refs['loginForm'].validate((valid) => {     //資料驗證
        if (valid) {    //如通過驗證才提交以下內容

          request.post('/admin/login', this.admin).then(res => {
            if (res.code === '200') {//請求成功



              this.$notify.success("登入成功")

              // 登入成功後將數據(包含token)存放Cookie
              // 將用戶數據存入瀏覽器(判斷當用戶帳密正確才存用戶信息)
              // Cookies存的是字符串，res.data是Json對象，需轉化成字符串
              // 'admin '是cookies名稱

              if (res.data != null) {
                Cookies.set('admin', JSON.stringify(res.data))//Cookies存的是字符串，res.data是Json對象，需轉化成字符串存到cookies
              }

              // 再跳轉 '/'代表/home頁面
              this.$router.push('/')
            }
            else {
              this.$notify.error(res.msg)
            }
          })

        }
      })


    }



  }



}
</script>
