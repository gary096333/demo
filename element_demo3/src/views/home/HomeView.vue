<template>
  <div>
    <div class="item">用戶名: {{ form.username }} </div>
    <div class="item">手機: {{ form.phone }} </div>
    <div class="item">信箱: {{ form.email }} </div>


  </div>

</template>



<script>
import Cookies from "js-cookie"


export default {

  data() {
    return {
      form: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}


    }

  }


}

</script>

<style>
.item {
  margin: 10px 0;
}
</style>
