<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">編輯分類</div>
    
    <el-form :inline="true" :model="form"  ref="ruleForm" label-width="100px">

      <el-form-item label="名稱" prop="name"> 
        <el-input v-model="form.name" placeholder="請輸入名稱"></el-input>
      </el-form-item>

      <el-form-item label="備註" prop="remark">
        <el-input v-model="form.remark" placeholder="請輸入備註"></el-input>
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
  name: 'EditCategory',
  data() {
    return {
      form: {}, //新增資料

      rules: {
        username: [
          { required: true, message: '請輸入分類名稱', trigger: 'blur' },

        ],

      }
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/category/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {

      //res是名稱，為後端Result的對象
      //將數據傳到form對象，form再向後台發送
      request.put('/category/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('更新成功')
          this.$router.push("/categoryList")
        }
        else {
          this.$notify.error(res.msg)//將後台錯誤返出來
        }
      })


    }
  }
}

</script>