import Vuex from 'vuex'
//import sys from './modules/system/sys'
//import global from './modules/global/global'
import Vue from 'vue'
Vue.use(Vuex)
export const store = new Vuex.Store({
  state: {
    mode: 'running',
    oauth: true,
    status: 'offline',
    hasLoadedRouter: false,
    userID: localStorage.getItem('userID') || -1,
    hasLogin: localStorage.getItem('hasLogin') === 'true' || false,
    imageId: -1,
    token: localStorage.getItem('token') || '',
    userInfo: {},
    messageList : [],
  },
  modules: {
    //sys,
    //global
  },
  mutations: {
    setMessageList(state,content){
      state.messageList.concat(content)

    },
    setStatus(state, status) {
      state.status = status
    },
    setRouterLoad(state, flag) {
      state.hasLoadedRouter = flag
    },
    setUserID(state, data) { // 传入对象有两个值，一个id用于表明登录id，另一个用于设置是否保存到localStorage中
      state.userID = data.id
      if (data.id != -1) {
        state.hasLogin = true
      } else {
        state.hasLogin = false
      }

      if (data.flag) {
        console.log("save", data, state.userID, state.hasLogin)
        localStorage.setItem('userID', state.userID)
        localStorage.setItem('hasLogin', state.hasLogin)
      }
    },
    setImageId(state, id) {
      state.imageId = id;
    },
    setToken(state, data) {
      state.token = data.token;
      if (data.flag) {
        localStorage.setItem("token", data.token);
      }
    },
    delToken(state) {
      state.token = '';
      localStorage.removeItem("token");
      state.hasLogin = false
      state.userID = -1
      state.userInfo = {}
    },
    setUserInfo(state, data){
      state.userInfo = data
    }
  }
})

export default store
