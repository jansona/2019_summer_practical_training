import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

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
          component: HelloWorld
        }
      ]
    }
  ]
})
