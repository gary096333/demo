<template>
  <div>
    <!--    搜索表單-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="請輸入名稱" v-model="params.name"></el-input> <!--v-model=""綁定才能顯示輸入值-->
      <el-input style="width: 240px; margin-left: 5px" placeholder="請輸入電話" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>



    <!--tableData是數組-->
    <!--stripe是斑馬紋，格子背景黑白交叉-->
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="編號" width="80" align="center"></el-table-column>
      <el-table-column prop="username" label="會員編號" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="80"></el-table-column>
      <el-table-column prop="age" label="年齡" width="100"></el-table-column>
      <el-table-column prop="address" label="地址" width="100"></el-table-column>
      <el-table-column prop="phone" label="手機" width="100"></el-table-column>
      <el-table-column prop="sex" label="性別" width="100" align="center"></el-table-column>
      <el-table-column prop="account" label="帳戶積分" width="100" align="center"></el-table-column>

      <!--          禁用功能-->
      <!--          changeStatus 方法 @change="changeStatus(scope.row)"-->
      <!--diabled 不能修改，但能看見-->
      <el-table-column label="狀態" width="50">
        <template v-slot="scope">
          <el-switch  v-model="scope.row.status"  disabled  active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>

        </template>
      </el-table-column>

      <el-table-column prop="createtime" label="創建時間" width="100"></el-table-column>
      <el-table-column prop="updatetime" label="更新時間" width="100"></el-table-column>

      <!--          點擊編輯按鈕，根據id跳轉到新的頁面-->
      <!--          根據id查詢，獲取id中資料-->
      <!--          scope.row就是當前行數據，點擊後會獲取當前對應的值-->
      <el-table-column label="操作" align="center" width="300">
        <template v-slot="scope">

          <!--          充值積分-->
          <el-button type="warning" @click="handleAccountAdd(scope.row)">充值</el-button>

          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)"
            style="margin-left: 2px">編輯</el-button>




          <!--         刪除-->
          <!--         二次確認刪除-->
          <!--          style="margin-left: px"  向左位移-->
          <el-popconfirm title="確認刪除嗎？" style="margin-left: 1px" @confirm="del(scope.row.id)">
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


    <!--   充值積分-->
    <el-dialog title="充值" :visible.sync="dialogFormVisible" width="30%"> <!--    彈窗-->
      <el-form :model="form" label-width="100 px" ref="ruleForm" :rules="rules"><!-- :model  ref  :rules (校驗 1)-->

        <el-form-item label="當前帳戶積分" prop="account"> <!-- prop="變量名稱" (校驗 3)-->
          <el-input disabled v-model="form.account" autocomplete="off"></el-input> 
        </el-form-item>
        <el-form-item label="積分" prop="score"> <!-- prop="變量名稱" (校驗 3)-->
          <el-input v-model="form.score" autocomplete="off"></el-input> 
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAccount">確 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>






<script>

import request from "@/utils/request"; //將axios對後端封裝成request對象導入

export default {
  name: 'User',
  data() {

    const checkNums = (rule, value, callback) => {//校驗規則

      value = parseInt(value)//value是字符串，需轉換


      if (value < 10 || value > 200) {
        callback(new Error('請输入大於10或小於等於200的整數'));
      }

      callback()
    };

    return {
      tableData: [

      ],
      total: 0,    //total初始值
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      },

      dialogFormVisible: false, //充值 //彈出視窗，默認false
      form: {}, //充值

      rules: {
        score: [
          { required: true, message: '請輸入積分', trigger: 'blur' },
          { validator: checkNums, trigger: 'blur' }

        ]
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
      request.get('/user/page', {
        params: this.params
      }).then(res => {    //res是名稱，為後端Result的對象

        if (res.code === '200') {
          
          this.tableData = res.data.list
          this.total = res.data.total

           // 更新status值
          this.tableData.forEach(item => {
            if (item.account >= 0) {
              item.status = true;
            } else {
              item.status = false;
            }
          })
        }

      })
    },
    //初始方法
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''

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
      request.delete("/user/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('刪除成功')
          this.load()
        }
        else {
          this.$notify.error(res.msg)
        }
      })

    },

    //充值積分
    //row 把當前用戶對象傳來
    handleAccountAdd(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true

    },

    //充值積分
    addAccount() {
      //按鈕觸發後來判斷valid是否執行(表单驗證)
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/account', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('充值成功')
              this.dialogFormVisible = false
              this.load()
            }
            else {
              this.$notify.error(res.msg)
            }

          })
        }
      })

    },

    // //禁用帳戶
    // changeStatus(row) { //row包含更改後的信息


    //   //res是名稱，為後端Result的對象
    //   //將數據傳到row對象，row再向後台發送
    //   request.put('/user/update', row).then(res => {
    //     if (res.code === '200') {

    //       if (row.status === true) {
    //         this.$notify.info('禁用取消')
    //         this.load() //更新成功後重新整理介面
    //       }
    //       else {
    //         this.$notify.info('禁用成功')
    //         this.load() //更新成功後重新整理介面
    //       }
    //     }
    //     else {
    //       this.$notify.error(res.msg)//將後台錯誤返出來
    //     }
    //   })


    // }



  }

}
</script>

<style scoped></style>
