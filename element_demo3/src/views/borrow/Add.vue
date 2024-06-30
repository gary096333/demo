<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增借書紀錄</div>
    <!--添加 :model :rules :ref(表單驗證)-->
    <!--通過 rules 屬性傳入約定的驗證規則(表單驗證)-->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">


      <el-form-item label="圖書標準碼" prop="bookNo"> <!--prop 屬性設置為需校驗的字段名(表單驗證)-->
        <!--      Select選擇器-->
        <!--        filterable可用搜索尋找-->
        <!--        clearable 選擇完旁邊出現叉叉-->
        <!--        [數據聯動]@change當選擇bookNo(圖書標準碼)，bookName(圖書名稱)與score(積分)會對應bookNo(圖書標準碼)所對應的圖書，將圖書名稱綁定到當前表單中-->
        <el-select v-model="form.bookNo" clearable filterable placeholder="請選擇" @change="selBook">
          <el-option v-for="item in books" :key="item.id" :label="item.bookNo" :value="item.bookNo">
          </el-option>
        </el-select>
      </el-form-item>
      <!--      disabled 不可輸入-->
      <el-form-item label="圖書名稱" prop="bookName"> <!--prop 屬性設置為需校驗的字段名(表單驗證)-->
        <el-input v-model="form.bookName" disabled></el-input>
      </el-form-item>

      <el-form-item label="圖書數量" prop="nums">
        <el-input v-model="form.nums" disabled></el-input>
      </el-form-item>

      <!--      disabled 不可輸入-->
      <el-form-item label="所需積分" prop="score">
        <el-input v-model="form.score" disabled></el-input>
      </el-form-item>



      <el-form-item label="會員碼" prop="userNo">
        <!--      Select選擇器 filterable-->
        <!--        [數據聯動] @change-->
        <!--        :value="item.id" 綁定selUser方法中的v.id(item為遍歷後的自訂變量、users為接收後端請求數據所自訂的變量)-->
        <el-select v-model="form.userNo" clearable filterable placeholder="請選擇" @change="selUser">
          <el-option v-for="item in users" :key="item.id" :label="item.username + '(' + item.name + ')'"
            :value="item.username">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="用戶名稱" prop="userName">
        <el-input disabled v-model="form.userName"></el-input>
      </el-form-item>

      <el-form-item label="用戶聯繫方式" prop="userPhone">
        <el-input disabled v-model="form.userPhone"></el-input>
      </el-form-item>

      <el-form-item label="用戶帳戶積分" prop="account">
        <el-input disabled v-model="form.account"></el-input>
      </el-form-item>

      <el-form-item label="借出的天數" prop="days">

        <el-input-number v-model="form.days" :min="1" :max="30" label="借出的天數"></el-input-number>
      </el-form-item>

    </el-form>



    <!--提交按鈕-->
    <div style="text-align: center; margin-top: 30px"> <!--text-align: center置中-->
      <el-button type="primary" @click="save" size="medium">提交</el-button>

    </div>

  </div>
</template>


<script>
import request from "@/utils/request"; //將axios對後端封裝成request對象導入
export default {
  name: 'BorrowBook',
  data() {

    return {
      form: { days: 1 },
      books: [], //Select選擇器
      users: [],



      //通過 rules 屬性傳入約定的驗證規則(表單驗證)
      //資料驗證，定義username對象中的數組，與prop 屬性設置的username字段名對應(表單驗證)
      rules: {
        bookNo: [
          { required: true, message: '請輸入圖書標準碼', trigger: 'blur' },

        ],
        userNo: [
          { required: true, message: '請輸入會員碼', trigger: 'blur' },

        ]


      }


    }
  },

  //Select選擇器
  //獲取後台 /book/list 所有數據
  created() {
    request.get('/book/list').then(res => {
      this.books = res.data
    })

    //Select選擇器
    //獲取後台 /user/list 所有數據
    //filter(v => v.status) user的status為true才允許借書(沒被禁用的用戶)
    request.get('/user/list').then(res => {
      this.users = res.data.filter(v => v.status)
    })
  },

  methods: {
    save() {

      //按鈕觸發後來判斷valid是否執行(表單驗證)
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {


          //res是名稱，為後端Result的對象
          //將數據傳到form對象，form再向後台發送
          request.post('/borrow/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.$refs['ruleForm'].resetFields()

            } else {
              this.$notify.error(res.msg)//將後台錯誤返出來
            }
          })
        }
      })


    },

    //Select選擇器
    //[數據聯動]當選擇bookNo(圖書標準碼)，bookName(圖書名稱)與score(積分)會對應bookNo(圖書標準碼)所對應的圖書，將圖書名稱綁定到當前表單中
    //輸入的form(borrow)中的bookNo是否等於v.bookNo(user中的bookNo)的值
    //this.$set(this.form,'bookName',res.data.name) 強制設置對象屬性  this.$set(對象,'對象的屬性名稱',對象屬性值)
    selBook() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      request.get('/book/' + book.id).then(res => {//調用後台更新數據
        // this.form.bookName = res.data.name//第一次選擇會沒數據
        this.$set(this.form, 'bookName', res.data.name)//改成強制設置
        this.form.score = res.data.score  //積分綁定到當前表單中
        this.form.nums = res.data.nums

      })

    },
    //Select選擇器
    //[數據聯動]
    //輸入的form(borrow)中的userNo是否等於v.id(user中的id)的值
    selUser() {
      const user = this.users.find(v => v.username === this.form.userNo)
      request.get('/user/' + user.id).then(res => {
        this.$set(this.form, 'userName', res.data.name)
        this.form.userPhone = res.data.phone
        this.form.account = res.data.account

      })

    }





  }

}

</script>
