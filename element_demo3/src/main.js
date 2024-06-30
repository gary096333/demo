import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
// import ElementUI from 'element-ui';//(已經有添加elementui插件，可選)
// import 'element-ui/lib/theme-chalk/index.css';//(已經有添加elementui插件，可選)
import '@/assets/global.css'//添加 global.css



Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
