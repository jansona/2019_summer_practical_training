<template>
  <transition :name="isContainHome?'fade':'fade-transform'" mode="out-in">
    <router-view v-if="isChildRouterAlive"/>
  </transition>
</template>
<script>
export default {
  name: "Content",
  props: {},
  data() {
    return {
      isChildRouterAlive: true,
      isContainHome: true,
    };
  },
  watch: {
    $route(to,from){
      console.log(to,from);
      if(to.name == 'Home' || from.name == 'Home') {
        this.isContainHome = true
      } else {
        this.isContainHome = false
      }
    }
  },
  methods: {
    reload() {
      // console.log("reload");
      this.isChildRouterAlive = false;
      this.$nextTick(function() {
        this.isChildRouterAlive = true;
      });
    }
  }
};
</script>
<style lang="less" scoped>
.home-conten {
  position: relative;
  width: 60%;
  left: 20%;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0s;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}

/* fade-transform */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all .5s;
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>


