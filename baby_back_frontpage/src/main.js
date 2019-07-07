// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 国际化
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)
Vue.config.lang = 'zh-cn'
const i18n_zh = new VueI18n({
  locale: 'zh-cn',
  zhLocale
})
const i18n_en = new VueI18n({
  locale: 'en',
  enLocale
})

// 引入elementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)

// 引入无限滚动
import infiniteScroll from 'vue-infinite-scroll'
Vue.use(infiniteScroll)

// 引入自己的工具css
import "@/assets/public.css";
// 引入导入的阿里巴巴图标库
import '@/assets/icons/iconfont.css'

// 引入vuex和axios
import { store } from '@/store/store'
import axios from 'axios'
Vue.config.productionTip = false

import VueNativeSock from 'vue-native-websocket'
Vue.use(VueNativeSock, 'ws://localhost:18080/websocket', {
  reconnection: true, // (Boolean) whether to reconnect automatically (false)
  reconnectionAttempts: 5, // (Number) number of reconnection attempts before giving up (Infinity),
  reconnectionDelay: 3000, // (Number) how long to initially wait before attempting a new (1000)
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n_zh,
  components: { App },
  axios,
  template: '<App/>'
})
