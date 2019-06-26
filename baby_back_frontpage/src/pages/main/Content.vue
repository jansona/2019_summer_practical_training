<template>
  <transition>
    <router-view v-if="isChildRouterAlive"/>
  </transition>
</template>
<script>
export default {
  name: "Content",
  props: {},
  data() {
    return {
      isChildRouterAlive: true
    };
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
</style>


