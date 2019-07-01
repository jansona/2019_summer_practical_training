import Vuex from 'vuex'
//import sys from './modules/system/sys'
//import global from './modules/global/global'
import Vue from 'vue'
Vue.use(Vuex)
export const store = new Vuex.Store({
  state: {
    // mode: 'running',
    // oauth: false,
    // status: 'offline',
    // hasLoadedRouter: false,
    // userID: localStorage.getItem('userID') || -1,
    // hasLogin: localStorage.getItem('hasLogin') === 'true' || false,
    // imageId: -1,
  },
  modules: {
    //sys,
    //global
  },
  mutations: {
//     setStatus(state, status) {
//       state.status = status
//     },
//     setRouterLoad(state, flag) {
//       state.hasLoadedRouter = flag
//     },
//     setUserID(state, data) {  // 传入对象有两个值，一个id用于表明登录id，另一个用于设置是否保存到localStorage中
//       state.userID = data.id
//       if (data.id != -1) {
//         state.hasLogin = true
//       } else {
//         state.hasLogin = false
//       }
// 
//       if (data.flag) {
//         console.log("save",data,state.userID,state.hasLogin)
//         localStorage.setItem('userID', state.userID)
//         localStorage.setItem('hasLogin', state.hasLogin)
//       }
//     },
//     setImageId(state,id){
//       state.imageId = id;
//     }
  }
})

export default store
