<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增圖書</div>
    <!--添加 :model :rules :ref(表單驗證)-->
    <!--通過 rules 屬性傳入約定的驗證規則(表單驗證)-->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

      <el-form-item label="名稱" prop="name">  <!--prop 屬性設置為需校驗的字段名(表單驗證)-->
        <el-input v-model="form.name" placeholder="請輸入名稱"></el-input>
      </el-form-item>

      <el-form-item label="描述" >
        <el-input v-model="form.description" style="width: 400px" type="textarea" placeholder="請輸入描述"></el-input>
      </el-form-item>

<!--      記得設定value-format="" 不然前端日期格式不對-->
      <el-form-item label="出版日期" prop="publishDate"> 
        <el-date-picker
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="請選擇出版日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="作者" prop="author"> 
        <el-input v-model="form.author" placeholder="請輸入作者"></el-input>
      </el-form-item>

      <el-form-item label="出版社" prop="publisher">
        <el-input v-model="form.publisher" placeholder="請輸入出版社"></el-input>
      </el-form-item>

      <el-form-item label="分類" prop="category">
        <el-cascader
        :props="{value: 'name', label: 'name'}"
        v-model="form.categories"
        :options="categories">
        </el-cascader>
      </el-form-item>

      <el-form-item label="標準碼" prop="bookNo"> 
        <el-input v-model="form.bookNo" placeholder="請輸入標準碼"></el-input>
      </el-form-item>

      <el-form-item label="數量" prop="nums"> 
        <el-input v-model="form.nums" placeholder="請輸入數量"></el-input>
      </el-form-item>

<!--      換行-->
      <br>

      <el-form-item label="封面" prop="cover">
       <el-upload
            class="avatar-uploader"
            :action="'http://54.199.152.2:9091/api/book/file/upload?token=' + this.admin.token"
            :show-file-list="false"
            :on-success="handleCoverSuccess"

        >
          <img v-if="form.cover" :src="form.cover" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <!--      inputNumber 計數器-->
      <el-form-item label="借書積分" prop="score"> 
        <el-input-number v-model="form.score" :min="10" :max="30" label="所需積分"></el-input-number>
      </el-form-item>

    </el-form>



    <!--提交按鈕-->
    <div style="text-align: center; margin-top: 30px"> <!--text-align: center置中-->
      <el-button type="primary" @click="save" size="medium">提交</el-button> 
      
    </div>

  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from "js-cookie"; //將axios對後端封裝成request對象導入
export default {
  name: 'AddBook',
  data() {

    //nums(數量)資料驗證
    const checkNums = (rule, value, callback) => {

      value = parseInt(value)//value是字符串，需轉換

      if ( value>=1000) {
        callback(new Error('請输入小於1000的整數'));
      }

      if (!Number.isInteger(value) || value<0 ) {
        callback(new Error('請输入大於等於零的整數'));
      }

      callback()
    };


    return {
      form: {score:10,cover:''}, //score默認10
      categories: [],//Book category(分類)選項功能，級聯選擇


      //通過 rules 屬性傳入約定的驗證規則(表單驗證)
      //資料驗證，定義name對象中的數組，與prop 屬性設置的name字段名對應(表單驗證)
      rules: {
        name: [
          {required: true, message: '請輸入圖書名稱', trigger: 'blur'},

        ],
        score: [
          {validator: checkNums, trigger: 'blur'},

        ],
        bookNo: [
          {required: true, message: '請輸入圖書標準碼', trigger: 'blur'},

        ],
        nums: [
          {required: true, message: '請輸入數量', trigger: 'blur'},
          {validator: checkNums, trigger: 'blur'}
        ]
      },

      //獲取瀏覽器用戶數據(獲取Cookie數據)，token憑證儲存在Cookie中
      //'admin '自訂名稱
      //如果有用戶對象，就解析成JSON，沒有傳空值(沒有判斷的話，如果沒有會報錯)
      admin:Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}  
    }


  },



//Book category(分類)選項功能，級聯選擇
  //獲取後台/category/tree數據
  created() {
    request.get('/category/tree').then(res => {
      this.categories = res.data
    })
  },

  methods: {
        save() {

          //按鈕觸發後來判斷valid是否執行(表單驗證)
          //上面如沒綁定const就須要用valid判斷
          this.$refs['ruleForm'].validate((valid)=> {
            if (valid) {

              
              //res是名稱，為後端Result的對象
              //將數據傳到form對象，form再向後台發送
              request.post('/book/save', this.form).then(res => {
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


    //圖片上傳
    //後台返回下載圖片，前台
    //res.data 後台返回的數據(後台連結  return Result.success("http://localhost:9091/api/book/file/download/" + flag);)
    handleCoverSuccess(res){
          if(res.code === '200'){
            console.log(res.data)
            this.form.cover = res.data//有時讀取不到this.form對象。如要用此方法，必須在form:{cover:''} 初始化對象 或使用以下強制設置
            // this.$set(this.form, 'cover',res.data)//this.$set強制將this.form設置為對象
          }

    }


      }

}

</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #959d8c;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

