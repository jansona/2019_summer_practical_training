import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		hasLogin: uni.getStorageSync('token') != undefined && uni.getStorageSync('token') != null && uni.getStorageSync('token') != '',
		loginProvider: "",
		openid: null,
		userId: uni.getStorageSync('userId') || -1,
		token: uni.getStorageSync('token'),
		userInfo: {},
		connected: false,
	},
	mutations: {
		login(state, provider) {
			state.hasLogin = true;
			state.loginProvider = provider;
		},
		logout(state) {
			state.hasLogin = false
			state.token = ''
			state.userId = -1
			state.connected = false
			uni.removeStorageSync('userId')
			uni.removeStorageSync('token')
		},
		setOpenid(state, openid) {
			state.openid = openid
		},
		setUserId(state,id){  // 传入类型为{id:xxx,flag:xxxx}
			state.userId = id
			state.hasLogin = true
			uni.setStorageSync('userId',id)
		},
		setToken(state,token) {
			state.token = token
			state.hasLogin = true
			uni.setStorageSync('token',token)
		},
		setUserInfo(state,data){
			state.userInfo = JSON.parse(JSON.stringify(data))
			state.userInfo.username = data.username
		},
		setConnected(state,data){
			state.connected = data
		}
	},
	actions: {
		// lazy loading openid
		getUserOpenId: async function ({
			commit,
			state
		}) {
			return await new Promise((resolve, reject) => {
				if (state.openid) {
					resolve(state.openid)
				} else {
					uni.login({
						success: (data) => {
							commit('login')
							setTimeout(function () { //模拟异步请求服务器获取 openid
								const openid = '123456789'
								console.log('uni.request mock openid[' + openid + ']');
								commit('setOpenid', openid)
								resolve(openid)
							}, 1000)
						},
						fail: (err) => {
							console.log('uni.login 接口调用失败，将无法正常使用开放接口等服务', err)
							reject(err)
						}
					})
				}
			})
		}
	}
})

export default store
