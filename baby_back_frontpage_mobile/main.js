import Vue from 'vue'
import App from './App'
import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'

Vue.use(MuseUI);
Vue.config.productionTip = false

// 引入tabbar的组件
import tabbar_1 from './pages/tabbar/tabbar-1/tabbar-1.vue'
import tabbar_2 from './pages/tabbar/tabbar-2/tabbar-2.vue'
import tabbar_3 from './pages/tabbar/tabbar-3/tabbar-3.vue'
import tabbar_4 from './pages/tabbar/tabbar-4/tabbar-4.vue'
import tabbar_5 from './pages/tabbar/tabbar-5/tabbar-5.vue'
Vue.component('tabbar_1',tabbar_1)
Vue.component('tabbar_2',tabbar_2)
Vue.component('tabbar_3',tabbar_3)
Vue.component('tabbar_4',tabbar_4)
Vue.component('tabbar_5',tabbar_5)

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
