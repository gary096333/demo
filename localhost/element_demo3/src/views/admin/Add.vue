<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增管理員</div>
    <!--添加 :model :rules :ref(表單驗證)-->
    <!--通過 rules 屬性傳入約定的驗證規則(表單驗證)-->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

      <el-form-item label="用戶名" prop="username">  
        <el-input v-model="form.username" placeholder="請輸入用戶名"></el-input>
      </el-form-item>

      <el-form-item label="聯繫方式" prop="phone"> 
        <el-input v-model="form.phone" placeholder="請輸入聯繫方式"></el-input>
      </el-form-item>

      <el-form-item label="信箱" prop="email"> 
        <el-input v-model="form.email" placeholder="請輸入信箱"></el-input>
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
  name: 'AddAdmin',
  data() {

    //表单验证
    const checkPhone = (rule, value, callback) => {   //checkPhone方法對應下面
      if (!/^[0][9][0-9]{8}$/.test(value)) {  //!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)，正則表達式
        callback(new Error('請输入合法手機號'));
      }

      callback()
    };

    const checkEmail = (rule, value, callback) => {
      const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
      if (!emailRegex.test(value)) {
        callback(new Error('請输入有效信箱'));
      } else {
        callback();
      }
    };



    return {
      form: {},


      //通過 rules 屬性傳入約定的驗證規則(表單驗證)
      //資料驗證，定義username對象中的數組，與prop 屬性設置的username字段名對應(表單驗證)
      rules: {
        username: [
          {required: true, message: '請輸入用戶名', trigger: 'blur'},
          {min: 3, max: 10, message: '長度在 3 到 10 個字符', trigger: 'blur'}
        ],
        phone:[
          { required: true, message: '請輸入手機號碼', trigger: 'blur' },
          {validator: checkPhone, trigger: 'blur'} //checkPhone方法(表單驗證)
        ],
        email: [
          { required: true, message: '請輸入信箱 @gmail.com', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ]
      }


    }
  },
  methods: {
        save() {

          //按鈕觸發後來判斷valid是否執行(表單驗證)
          this.$refs['ruleForm'].validate((valid)=> {
            if (valid) {

              
              //res是名稱，為後端Result的對象
              //將數據傳到form對象，form再向後台發送
              request.post('/admin/save', this.form).then(res => {
                if (res.code === '200') {
                  this.$notify.success('新增成功')
                  this.$refs['ruleForm'].resetFields() //新增完清空頁面
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

