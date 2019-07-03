import Vue from 'vue'
import Router from 'vue-router'
import { Notification } from 'element-ui'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: () => import('@/pages/main/Main'),
      redirect: 'home',
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
        },
        {
          path: 'loginOrRegist',
          name: 'LoginOrRegist',
          component: () => import('@/pages/user/LoginOrRegist')
        },
        {
          path: 'faceWall',
          name: 'FaceWall',
          component: () => import('@/pages/faceWall/FaceWall')
        },
        {
          path: 'faceDetail',
          name: 'FaceDetail',
          component: () => import('@/pages/faceWall/FaceDetail')
        },
        {
          path: 'insertArticle',
          name: 'InsertArticle',
          component: () => import('@/pages/bbs/InsertArticle')
        },
        {
          path: 'showArticle',
          name: 'showArticle',
          component: () => import('@/pages/bbs/ShowArticle')
        }
        ,
        {
          path: 'userDetail',
          name: 'UserDetail',
          component: () => import('@/pages/user/UserDetail')
        },
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/loginOrRegist' || to.path === '/home') {
    next();
  } else {
    let token = localStorage.getItem('token');
    console.log(token)
    if (token === null || token === '') {

      setTimeout(() => {
        Notification.info({
          title: "请登录后再操作",
          offset: 50,
          duration: 1500,
        })
        next('/loginOrRegist');
      }, 200);
    } else {
      next();
    }
  }
});

export default router