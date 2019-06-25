import Vuex from 'vuex'
import sys from './modules/system/sys'
import global from './modules/global/global'
import Vue from 'vue'
Vue.use(Vuex)
export const store = new Vuex.Store(
  {
    state: {
      mode: 'running',
      oauth: false,
      status: 'offline',
      hasLoadedRouter: false
    },
    modules: {
      sys,
      global
    },
    mutations: {
      setStatus(state, status) {
        state.status = status
      },
      setRouterLoad(state, flag) {
        state.hasLoadedRouter = flag
      }
    }
  }
)

export default store