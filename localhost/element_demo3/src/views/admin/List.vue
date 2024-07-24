<template>
  <div>
    <!--    搜索表單-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="請輸入用戶名" v-model="params.username"></el-input>   
      <el-input style="width: 240px; margin-left: 5px" placeholder="請輸入電話" v-model="params.phone"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="請輸入信箱" v-model="params.email"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>  
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>  
    </div>




    <el-table  :data="tableData"  stripe >     <!--tableData是數組-->
      <el-table-column prop="id" label="編號" width="80" align="center" ></el-table-column>
      <el-table-column prop="username" label="用戶名" width="80"  ></el-table-column>
      <el-table-column prop="phone" label="手機"  width="100" align="center"></el-table-column>
      <el-table-column prop="email" label="信箱"  align="center"></el-table-column>
      <el-table-column prop="createtime" label="創建時間" width="100"></el-table-column>
      <el-table-column prop="updatetime" label="更新時間" width="100"></el-table-column>


      <!--          禁用功能-->
      <!--          changeStatus 方法-->
      <el-table-column label="狀態" width="50"  >
        <template v-slot="scope">
          <el-switch v-model="scope.row.status"
                     @change="changeStatus(scope.row)"
                     active-color="#13ce66" inactive-color="#ff4949">

          </el-switch>

        </template>
      </el-table-column>






      <!--          點擊編輯按鈕，根據id跳轉到新的頁面-->
      <!--          根據id查詢，獲取id中資料-->
      <el-table-column label="操作" align="center"  width="400">
        <template v-slot="scope" >

          <!--          scope.row就是當前行數據-->
          <el-button type="primary" @click="$router.push('/editAdmin?id='+scope.row.id )"  >編輯</el-button>



          <!--         刪除-->
          <!--         二次確認刪除-->
          <!--          style="margin-left: px"  向左位移-->
          <el-popconfirm  title="確認刪除嗎？" style="margin-left: 5px" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>



          <!--         修改密碼-->
<!--          style="margin-left: px"  間距-->
<!--          (scope.row)-->
            <el-button style="margin-left: 5px" :disabled="scope.row.username != admin.username"  type="warning" @click="handleChangePass(scope.row)">修改密碼</el-button>


        </template>
      </el-table-column>



    </el-table>






    <!--        分頁-->
    <!--   page-size=""  每頁5筆數據-->
    <!-- :total="" 表格總數據-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">    <!-- total變量-->
      </el-pagination>
    </div>



<!--    修改密碼-->
    <el-dialog title="修改密碼" :visible.sync="dialogFormVisible" width="30%"> <!--    彈窗-->
      <el-form :model="form" label-width="100 px" ref="formRef" :rules="rules"><!-- :model  ref  :rules (校驗 1)-->
        <el-form-item label="新密碼" prop="newPass"> <!-- prop="變量名稱" (校驗 3)-->
          <el-input v-model="form.newPass" autocomplete="off" show-password></el-input> <!-- show-password 隱藏密碼-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePassword">确 定</el-button>
      </div>
    </el-dialog>




  </div>
</template>






<script>

import request from "@/utils/request";
import Cookies from "js-cookie"; //將axios對後端封裝成request對象導入

export default {
  name: 'AdminList',

  //變量名稱
  data() {
    return {
      tableData: [
         
      ],
      total:0,    //total初始值
      params:{
        pageNum:1,
        pageSize:10,
        username:'',
        phone:'',
        email:'',

      },
      dialogFormVisible:false,  //false彈窗默認不打開
      form:{}, 
      rules: {      // (修改密碼) (校驗 2)
        newPass: [
          {required: true, message: '請輸入新密碼', trigger: 'blur'},
          {min: 3, max: 10, message: '長度在3~10個字符', trigger: 'blur'}
        ]
      },
      //獲取瀏覽器用戶數據(獲取Cookie數據)，token憑證儲存在Cookie中
      //'admin '自訂名稱
      //如果有用戶對象，就解析成JSON，沒有傳空值(沒有判斷的話，如果沒有會報錯)
      admin:Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}  

    }
  },

  // 連接數據庫數據
  created(){
    this.load()

  },
  methods: {


    load(){
      request.get('/admin/page', {
        params:this.params
      }).then (res =>{    //res是名稱，為後端Result的對象

        if(res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }

      })
    },
    //初始方法
    reset(){
      this.params={
        pageNum:1,
        pageSize:10,
        username:'',
        phone:'',
        email:'',


      }
      this.load() //重新整理頁面

    },
    //點擊分頁按鈕觸發分頁
    //點擊頁數翻頁
    handleCurrentChange(pageNum){
      this.params.pageNum = pageNum
      this.load()
    },
    //刪除方法
    //res是名稱，為後端Result的對象
    del(id){
      request.delete("/admin/delete/" +id).then(res => {
        if(res.code ==='200'){
          this.$notify.success('刪除成功')
          this.load()
        }
        else{
          this.$notify.error(res.msg)
        }
      })
    },

    //修改密碼
    handleChangePass(row){
      this.form = JSON.parse(JSON.stringify(row)) //建立新的行對象，跟當前的行對象隔離開，互不影響
      this.dialogFormVisible = true //彈窗打開(修改密碼)


    },
    //修改密碼
    savePassword(){
      this.$refs['formRef'].validate((valid) =>{   //(校驗 4)
        if(valid){  //(校驗 4)
          request.put('/admin/password',this.form).then(res =>{
            if(res.code === '200'){
              this.$notify.success("修改成功")
              //當前修改用戶的id等於當前登入的管理員id(從Cookie獲取，admin在這行代碼上面有定義變量)
              //登入後修改正在登入帳號的密碼會自動登出
              //修改成功後，需重新登入
              if(this.form.id === this.admin.id){
                Cookies.remove('admin')
                this.$router.push('/login')

              }else{
                this.load() //重新整理頁面
                this.dialogFormVisible=false //彈窗關閉

              }
              

              }
            else{
              this.$notify.error("修改失敗")
            }
          })

        }
      })

    },

    //禁用帳戶
    changeStatus(row){ //row包含更改後的信息

      //無法禁用自己的帳戶
      //判斷目前登入的帳戶是當前帳戶，且當status為false(被禁用)
      if(this.admin.id === row.id && !row.status){
        row.status =true
        this.$notify.warning('無法禁用自己的帳戶')
        return
      }

      
      //res是名稱，為後端Result的對象
      //將數據傳到row對象，row再向後台發送
      request.put('/admin/update',row).then(res =>{
        if(res.code === '200'){
          if(row.status === true ){
          this.$notify.info('禁用取消')
          this.load() //更新成功後重新整理介面
          }
          else {
            this.$notify.info('禁用成功')
            this.load() //更新成功後重新整理介面
          }
        }
        else{
          this.$notify.error(res.msg)//將後台錯誤返出來
        }
      })


    }




  }
}
</script>

<style scoped>

</style>
