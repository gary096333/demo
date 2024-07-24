<template>
  <div>
    <!--    搜索表單-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="請輸入圖書名稱" v-model="params.name"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="請輸入圖書標準碼" v-model="params.bookNo"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
      
    </div>





    <!--tableData是數組-->
    <!--    渲染樹狀資料時，必須要指定 row-key-->

    <el-table :data="tableData" stripe row-key="id"> <!--    default-expand-all 樹狀資料默認展開-->
      <el-table-column prop="id" label="編號" align="center" width="50px"></el-table-column>
      <el-table-column prop="name" label="圖書名稱"></el-table-column>
      <el-table-column prop="description" label="描述"  align="center" width="150px">
        <template #default="scope">
          <el-tooltip class="item" effect="light" :content="scope.row.description || '無描述'" placement="top">
            <div v-if="scope.row.description && scope.row.description.length < 10">{{ scope.row.description }}</div>
            <div v-else-if="scope.row.description">{{ scope.row.description.slice(0, 10) }}...</div>
            <div v-else> </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="publishDate" label="出版日期" width="90px"></el-table-column>
      <el-table-column prop="author" label="作者"></el-table-column>
      <el-table-column prop="publisher" label="出版社"></el-table-column>
      <el-table-column prop="category" label="分類" width="60px"></el-table-column>
      <el-table-column prop="bookNo" label="標準碼"></el-table-column>
      <el-table-column prop="score" label="借書積分"  align="center"></el-table-column>
      <el-table-column prop="nums" label="數量"  align="center"></el-table-column>
      <el-table-column prop="cover" label="封面"  align="center">
        <template v-slot="scope">
          <!--          :preview-src-list 圖片預覽-->
          <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="創建時間" width="90px"></el-table-column>
      <el-table-column prop="updatetime" label="更新時間" width="90px"></el-table-column>




      <!--          點擊編輯按鈕，根據id跳轉到新的頁面-->
      <!--          根據id查詢，獲取id中資料-->
      <el-table-column label="操作" align="center" width="90">
        <template v-slot="scope">

          <!--          scope.row就是當前行數據-->
          <el-button type="primary" @click="$router.push('/editBook?id=' + scope.row.id)">編輯</el-button>



          <!--         刪除-->
          <!--         二次確認刪除-->
          <!--          style="margin-left: px"  向左位移-->
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


export default {
  name: 'BookList',

  //變量名稱
  data() {
    return {
      tableData: [
       
      ],
      total: 0,    //total初始值

      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookNo: ''

      }



    }
  },

  // 連接數據庫數據
  created() {
    this.load()

  },
  methods: {


    load() {
      


      // 以下為向後台發送請求
      request.get('/book/page', {
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
        name: '',
        bookNo: ''


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
      request.delete("/book/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('刪除成功')
          this.load()
        }
        else {
          this.$notify.error(res.msg)
        }
      })
    },





  }
}
</script>

<style scoped></style>
