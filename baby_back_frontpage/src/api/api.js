import Qs from 'qs'
import Axios from 'axios'
import router from '../router'
import { store } from '../store/store'
import { Notification } from 'element-ui'

// ==========================================post请求 开始===========================================
export const request = (url, params, ...others) => {
  // console.log(others)
  if (others.length > 0) {
    if (!others[0]) {
      console.log('no stringify')
    }
  } else {
    params = Qs.stringify(params)
  }
  return new Promise((resolve, reject) => {
    Axios.post(url, params).then(res => {
      resolve(res.data)
    }).catch(error => {
      reject(error)
    })
  })
}
// ==========================================post请求 结束===========================================
// ==========================================文件上传post===========================================
export const importRequest = (url, params) => {
  return new Promise((resolve, reject) => {
    Axios({
      method: 'post',
      url: url,
      data: params,
      headers: {'Content-Type': 'multipart/form-data;boundary=' + new Date().getTime()}
    }).then(res => {
      resolve(res.data)
    }).catch(error => {
      reject(error)
    })
  })
}
// ==========================================文件下载get 未完成===========================================
export const exportFetch = (url, params) => {
  return new Promise((resolve, reject) => {
    Axios({
      method: 'get',
      url,
      data: params,
      responseType: 'blob',
    }).then(res => {
      if (!res) {
        reject(new Error("nothing found"))
      } else {
      }
    })
  })
}
// ==========================================get请求 开始===========================================
export const fetch = (url, params) => {
  return new Promise((resolve, reject) => {
    Axios.get(url, {
      params: params
    }).then(res => {
      // console.log(res)
      if (!res.data) {
        resolve(res)
      } else {
        resolve(res.data)
      }
    }).catch(error => {
      reject(error)
    })
  })
}
// ==========================================get请求 结束===========================================
// 响应拦截器
Axios.interceptors.response.use(
  res => {
    let data = null
    if (!res.data) {
      data = res
    } else {
      data = res.data 
    }
    if (data.code != 200 && data.code != 0 && data.code != undefined) {
      // alert(data.code)
      switch (data.code) {
        case 401:
          Notification.info({
              title: 'info',
              message: '认证失败',
          })
          break;
        default:
      }
    } else {
      return Promise.resolve(res)
    }
  },
  error => {
    if (store.state.mode === 'dev') {
      return Promise.reject(error)
    } else {
      // console.log(error)
      if (error && error.response) {
        switch (error.response.status) {
          case 400:
            // error.message = '错误请求，用户名或密码错误'
            // console.log(error.response)
            Notification.error({
              title:'error',
              message: error.response.data.message,
            })
            break
          case 401:
            error.message = '认证超时，重新登录'
            router.replace({
              path: '/login',
            })
            break
          case 504:
            error.message = '网络超时'
            Notification.info({
              title: 'info',
              message: '网络超时,请重试',
            })
            break;
          case 500:
            error.message = '服务器内部错误'
            Notification.info({
              title: 'info',
              message: '服务器内部错误',
            })
            break;
          default:
            error.message = '连接错误'
        }
      }
      return Promise.reject(error)
    }
  }
)

// 请求拦截器
Axios.interceptors.request.use(
  config => {
    if (store.state.oauth) {
      if (localStorage.getItem('access_token')) {
        config.headers['Authorization'] =
          'Bearer ' + localStorage.getItem('access_token')
      } else {
      }
    }
    return config
  },
  error => {
    console.log(error)
  }
)
