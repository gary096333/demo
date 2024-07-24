<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">編輯用戶</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

      <el-form-item label="會員編號">
        <el-input v-model="form.username" disabled></el-input> <!--diabled 不能修改，但能看見-->
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="請輸入姓名"></el-input>
      </el-form-item>

      <el-form-item label="年齡">
        <el-input v-model="form.age" placeholder="請輸入年齡"></el-input>
      </el-form-item>

      <el-form-item label="性別"> <!--      使用選擇方式-->
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
      </el-form-item>

      <el-form-item label="聯繫方式">
        <el-input v-model="form.phone" placeholder="請輸入聯繫方式"></el-input>
      </el-form-item>

      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="請輸入地址"></el-input>
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
export default {
  name: 'EditUser',
  data() {
    return {
      form: { sex: '男' },//新增資料 sex默認為男

      rules: {
        name: [
          { required: true, message: '請輸入姓名', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    const id = this.$route.query.id
    //後台通過查詢id(user對象)，賦給form對象
    //把查詢到id的對應資料，點擊編輯就能顯示對應的數據出來
    request.get("/user/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {


      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {

          //res是名稱，為後端Result的對象
          //將數據傳到form對象，form再向後台發送
          // this.$notify彈窗顯示
          request.put('/user/update', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('更新成功')
              this.$router.push("/userList") //更新成功後回到 /user介面
            }
            else {
              this.$notify.error(res.msg)//將後台錯誤返出來
            }
          })

        }
      })

    }
  }
}

</script>