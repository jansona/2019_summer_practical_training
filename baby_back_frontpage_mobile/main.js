import Vue from 'vue'
import App from './App'
Vue.config.productionTip = false

// 引入tabbar的组件
import tabbar_1 from './pages/tabbar/tabbar-1/tabbar-1.vue'
import tabbar_2 from './pages/tabbar/tabbar-2/tabbar-2.vue'
import tabbar_3 from './pages/tabbar/tabbar-3/tabbar-3.vue'
import tabbar_4 from './pages/tabbar/tabbar-4/tabbar-4.vue'
import user from './pages/tabbar/tabbar-5/user/user.vue'
Vue.component('tabbar_1',tabbar_1)
Vue.component('tabbar_2',tabbar_2)
Vue.component('tabbar_3',tabbar_3)
Vue.component('tabbar_4',tabbar_4)
Vue.component('tabbar_5',user)

// 注册网络请求插件
import api from '@/common/vmeitime-http/'
Vue.prototype.$api = api

// 使用Vuex
import store from './store'
Vue.prototype.$store = store;


App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
