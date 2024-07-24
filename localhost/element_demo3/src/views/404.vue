
<template>
  <div style="text-align: center">
    <div style="margin-top: 100px; font-size: 200px;" >Oops!</div>
    <div style="margin:30px; font-size: 30px; ">404-Page Not Found </div>
    <div>
<!--      ('/')代表跳轉到 /home頁面-->
      <el-button type="text" style="margin-top: 20px; font-size: 20px" @click="$router.push('/')">返回主頁</el-button>
    </div>
  </div>
</template>