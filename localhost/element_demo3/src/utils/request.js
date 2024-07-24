import axios from 'axios'
import Cookies from "js-cookie";
import router from "@/router";





//相當於new axios， request是axios的對象
const request = axios.create({

    baseURL: 'http://54.199.152.2:9091/api', //後端前綴有加上/api(class WebConfig)，應路由保持一致
    timeout: 10000
})

//request攔截器
//可以自請求發送前，對請求做一些處理
//比如統一加token，對請求參數統一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';



    //從Cookie中獲取Admin數據裡的token
    //Cookie中存放的是Json格式字符串，需解開
    const adminJson = Cookies.get('admin')
    if (adminJson) {
        // 設置請求頭
        //把token放置在請求頭
        config.headers['token'] = JSON.parse(adminJson).token

    }


    return config
}, error => {
    return Promise.reject(error)
});


//response攔截器
//可以在接口響應後統一處理結果
request.interceptors.response.use(
    response => {
        let res = response.data;  //res包含code、date、msg屬性
        //兼容服務端返回的字符串數據
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }

        if (res.code === '401') { //出現401，表示沒有權限訪問數據
            router.push('/login')
        }
        return res;
    },

    error => {
        console.log('err' + error)//for debug
        return Promise.reject(error)
    }
)

export default request