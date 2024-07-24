
<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">編輯管理員</div>
    
    <el-form :inline="true" :model="form"   label-width="100px">

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
export default{
  name:'EditAdmin',
  data(){
    return{
      form: {sex:'男'} //新增資料 sex默認為男
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/admin/"+id).then(res =>{   
      this.form = res.data
    })
  },
  methods:{
    save(){
      
      //res是名稱，為後端Result的對象
      //將數據傳到form對象，form再向後台發送
      request.put('/admin/update',this.form).then(res =>{
        if(res.code === '200'){
          this.$notify.success('更新成功')
          this.$router.push("/adminList") //更新成功後回到 /admin介面
        }
        else{
          this.$notify.error(res.msg)//將後台錯誤返出來
        }
      })


    }
  }
}

</script>