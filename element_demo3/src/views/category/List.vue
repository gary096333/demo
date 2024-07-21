<template>
  <div>
    <!--    搜索表單-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="請輸入分類名稱" v-model="params.name"></el-input> <!--v-model=""綁定才能顯示輸入值-->

      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>





    <!--tableData是數組-->
    <!--    渲染樹狀資料時，必須要指定 row-key-->

    <el-table :data="tableData" stripe row-key="id"> <!--    default-expand-all 樹狀資料默認展開-->
      <el-table-column prop="id" label="編號" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="名稱" width="100"></el-table-column>
      <el-table-column prop="remark" label="備註" width="100"></el-table-column>
      <el-table-column prop="createtime" label="創建時間" width="100"></el-table-column>
      <el-table-column prop="updatetime" label="更新時間" width="100"></el-table-column>




      <!--          點擊編輯按鈕，根據id跳轉到新的頁面-->
      <!--          根據id查詢，獲取id中資料-->
      <el-table-column label="操作" align="center" >
        <template v-slot="scope">

          <!--          scope.row就是當前行數據-->
          <!--          v-if="!scope.row.pid" 判斷如果沒有pid(無pid是父級)的才有添加二級分類按鈕-->
          <el-button type="success" v-if="!scope.row.pid" @click="handleAdd(scope.row)">添加二級分類</el-button>
          <el-button type="primary" @click="$router.push('/editCategory?id=' + scope.row.id)">編輯</el-button>



          <!--         刪除-->
          <!--         二次確認刪除-->
          <!--          style="margin-left: px"  向左位移-->
          <el-popconfirm title="確認刪除嗎？" style="margin-left: 5px" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>


        </template>
      </el-table-column>



    </el-table>






    <!--        分頁-->
    <!--   page-size=""  每頁5筆數據-->
    <!-- :total="" 表格總數據-->
    <div style="margin-top: 20px">
      <el-pagination background :current-page="params.pageNum" :page-size="params.pageSize" layout="prev, pager, next"
        @current-change="handleCurrentChange" :total="total"> <!-- total變量-->
      </el-pagination>
    </div>


    <!--   添加二級分類， 樹狀數據-->
    <el-dialog title="添加二級分類" :visible.sync="dialogFormVisible" width="30%"> <!--    彈窗-->
      <el-form :model="form" label-width="100 px" ref="ruleForm" :rules="rules"><!-- :model  ref  :rules (校驗 1)-->

        <el-form-item label="分類名稱" prop="name"> <!-- prop="變量名稱" (校驗 3)-->
          <el-input v-model="form.name" autocomplete="off"></el-input> <!-- show-password 隱藏密碼-->
        </el-form-item>
        <el-form-item label="分類備註" prop="remark"> <!-- prop="變量名稱" (校驗 3)-->
          <el-input v-model="form.remark" autocomplete="off"></el-input> <!-- show-password 隱藏密碼-->
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">確 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>






<script>

import request from "@/utils/request";
import Cookies from "js-cookie"; //將axios對後端封裝成request對象導入

export default {
  name: 'CategoryList',

  //變量名稱
  data() {
    return {
      tableData: [
       
      ],

      total: 0,    //total初始值
      form: {},
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',


      },



      rules: {      //添加二級分類(樹狀數據)  (校驗 2)
        name: [
          { required: true, message: '請輸入分類名稱', trigger: 'blur' }
        ]
      },

      dialogFormVisible: false,  //默認不彈窗

      pid: null //樹狀數據，二級圖書分類

    }
  },

  // 連接數據庫數據
  created() {
    this.load()

  },
  methods: {


    load() {



      // 以下為向後台發送請求
      request.get('/category/page', {
        params: this.params
      }).then(res => {    //res是名稱，為後端Result的對象

        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }

      })
    },
    //初始方法
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: ''


      }
      this.load() //重新整理頁面

    },
    //點擊分頁按鈕觸發分頁
    //點擊頁數翻頁
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    //刪除方法
    //res是名稱，為後端Result的對象
    del(id) {
      request.delete("/category/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('刪除成功')
          this.load()
        }
        else {
          this.$notify.error(res.msg)
        }
      })
    },


    // 樹狀數據，添加二級分類
    save() {

      //按鈕觸發後來判斷valid是否執行(表單驗證)
      this.$refs['ruleForm'].validate((valid) => {   //(校驗 4)
        if (valid) {


          //res是名稱，為後端Result的對象
          //將數據傳到form對象，form再向後台發送
          this.form.pid = this.pid //給二級分類賦值pid()
          request.post('/category/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增二級分類成功')
              this.$refs['ruleForm'].resetFields() //新增完清空頁面
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$notify.error(res.msg)//將後台錯誤返出來
            }
          })
        }
      })


    },

    // 點擊添加二級分類，彈出視窗
    // 將當前行的id作為二級分類的pid(在pid中輸入父級的id，成為子級)
    //子級pid會等於父級id
    handleAdd(row) {
      this.pid = row.id
      this.dialogFormVisible = true
    }



  }
}
</script>

<style scoped></style>
