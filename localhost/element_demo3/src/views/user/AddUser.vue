<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增用戶</div>
    <!--添加 :model :rules :ref(表單驗證)-->
    <!--通过 rules 屬性傳入約定的驗證規則(表單驗證)-->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

      <el-form-item label="姓名" prop="name"> <!--prop 通過 rules 屬性傳入約定的驗證規則(表單驗證)-->
        <el-input v-model="form.name" placeholder="請輸入姓名"></el-input>
      </el-form-item>

      <el-form-item label="年齡" prop="age">
        <el-input v-model="form.age" placeholder="請輸入年齡"></el-input>
      </el-form-item>


      <el-form-item label="聯繫方式" prop="phone">
        <el-input v-model="form.phone" placeholder="請輸入聯繫方式"></el-input>
      </el-form-item>

      <el-form-item label="性別"> <!--      使用選擇方式-->
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
      </el-form-item>


      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="請輸入地址"></el-input>
      </el-form-item>



    </el-form>

    <!--提交按鈕-->
    <!--text-align: center置中-->
    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>

    </div>

  </div>
</template>


<script>
import request from "@/utils/request"; //將axios對後端封裝成request對象導入

export default {
  name: 'AddUser',
  data() {

    //表單驗證
    const checkAge = (rule, value, callback) => {   //checkAge驗證規則，方法對應下面
      if (!value) {
        return callback(new Error('年齡不能為空'));
      }

      if (!/^[0-9]+$/.test(value)) {//!/^[0-9]+$/.test(value)
        callback(new Error('請輸入數字值'));
      }
      if (parseInt(value) > 120 || parseInt(value) <= 0) {
        callback(new Error('請輸入合理的年齡'));
      }
      callback()
    };

    const checkPhone = (rule, value, callback) => {   //checkPhone驗證規則，方法對應下面
      if (!/^[0][9][0-9]{8}$/.test(value)) {  //!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)
        callback(new Error('請輸入合法手機號'));
      }

      callback()
    };

    return {
      form: { sex: '男' }, //新增資料 sex默認為男

      //通过 rules 屬性傳入約定的驗證規則(表單驗證)
      //資料驗證，定義name對象中的數組，與prop 屬性設置的name字段名對應(表單驗證)
      rules: {
        name: [
          { required: true, message: '請輸入姓名', trigger: 'blur' },
        ],
        age: [
          { required: true,validator: checkAge, trigger: 'blur' } //checkAge方法(表單驗證)
        ],
        phone: [
          { required: true,validator: checkPhone, trigger: 'blur' } //checkPhone方法(表單驗證)
        ]
      }


    }
  },
  methods: {
    save() {

      //按鈕觸發後來判斷valid是否執行(表單驗證)
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {

          //後台url:user/save
          //res是名稱，為後端Result的對象
          //將數據傳到form對象，form再向後台發送
          // this.$notify彈窗顯示，另種方式
          request.post('/user/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.$refs['ruleForm'].resetFields() //新增完清空頁面 
              this.form = { sex: '男' } //新增完清空頁面，且sex保持默認為男 
            } else {
              this.$notify.error(res.msg)//將後台錯誤返出來

            }
          })
        }
      })


    }
  }
}

</script>
