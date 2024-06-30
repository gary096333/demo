<template>
  <div>
    <!--    搜索表單-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="請輸入圖書名稱" v-model="params.bookName"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="請輸入圖書標準碼" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="請輸入用戶名稱" v-model="params.userName"></el-input>


      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>





    <!--tableData是數組-->
    <!--    渲染樹狀資料時，必須要指定 row-key-->

    <el-table :data="tableData" stripe row-key="id">
      <el-table-column prop="id" label="編號" align="center" width="50px"></el-table-column>
      <el-table-column prop="bookName" label="圖書名稱"></el-table-column>
      <el-table-column prop="bookNo" label="圖書標準碼"></el-table-column>
      <el-table-column prop="userNo" label="會員碼"></el-table-column>
      <el-table-column prop="userName" label="用戶名稱"></el-table-column>
      <el-table-column prop="userPhone" label="用戶聯繫方式"></el-table-column>
      <el-table-column prop="score" label="所用積分"></el-table-column>
      <el-table-column prop="status" label="借出狀態"></el-table-column>
      <el-table-column prop="days" label="借出天數"></el-table-column>
      <el-table-column prop="createtime" label="借出日期" width="90px"></el-table-column>
      <el-table-column prop="returnDate" label="歸還日期"></el-table-column>
      <el-table-column prop="realDate" label="實際歸還日期"></el-table-column>

      <!--          點擊編輯按鈕，根據id跳轉到新的頁面-->
      <!--          根據id查詢，獲取id中資料-->
      <el-table-column label="操作" align="center" width="90">
        <template v-slot="scope">





          <!--         刪除-->
          <el-popconfirm title="確認刪除嗎？" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference" style="margin-top: 1px">删除</el-button>
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



  </div>
</template>






<script>

import request from "@/utils/request";
import Cookies from "js-cookie"; //將axios對後端封裝成request對象導入

export default {
  name: 'ReturList',

  //變量名稱
  data() {
    return {
      tableData: [

      ],
      total: 0,    //total初始值

      params: {
        pageNum: 1,
        pageSize: 10,
        userName: '',
        bookNo: '',
        bookName: ''

      },

    }
  },

  // 連接數據庫數據
  created() {
    this.load()

  },
  methods: {


    load() {



      // 以下為向後台發送請求
      request.get('/borrow/pageRetur', {
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
        bookName: '',
        bookNo: '',
        userName: ''


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
      request.delete("/borrow/deleteRetur/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('刪除成功')
          this.load()
        }
        else {
          this.$notify.error(res.msg)
        }
      })
    }






  }
}
</script>

<style scoped></style>
