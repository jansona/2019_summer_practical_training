import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: () => import('@/pages/main/Main'),
      children: [
        {
          path: 'home',
          name: 'Home',
          component: () => import('@/pages/home/Home')
        },
        {
          path: 'findRegist',
          name: 'FindRegist',
          component: () => import('@/pages/findChildren/FindRegist'),
        },
        {
          path: 'bbsHome',
          name: 'BbsHome',
          component: () => import('@/pages/bbs/BbsHome'),
        },
        {
          path: 'fastMatch',
          name: 'FastMatch',
          component: () => import('@/pages/fastMatch/FastMatch')
        }
      ]
    }
  ]
})
