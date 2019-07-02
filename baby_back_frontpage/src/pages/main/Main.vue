<template>
  <el-container class="main-container">
    <!-- <el-header class="bottom-shadow"> -->
      <home-header @on-navbar-click="handleNavBarClick"></home-header>
    <!-- </el-header> -->
    <!-- <div class="full-container"> -->
      <el-main>
        <div class="full-container"></div>
        <home-content ref="content" class="content-container"></home-content>
      </el-main>
    <!-- </div> -->
  </el-container>
</template>
<script>
import HomeHeader from "@/pages/main/Header";
import HomeContent from "@/pages/main/Content";
export default {
  name: "Home",
  components: {
    HomeHeader,
    HomeContent
  },
  data() {
    return {
      currentWidth: 300
    };
  },
  methods: {
    handleSelect(indexPath) {
      // console.log(indexPath);
    },
    handleHeadImgClick() {},
    handleNavBarClick() {
      // console.log("click!")
      this.$refs.content.reload();
    }
  },
  watch: {},
  mounted() {
    // console.log("main mounted")
  }
};
</script>
<style scoped>
.bottom-shadow {
  box-shadow: 0px 10px 30px #111111;
}
.bottom-shadow /deep/ .head-container {
  box-shadow: 0px 5px 10px #888888;
}
.main-el-sider {
  /* width: 15%; */
  overflow: visible;
  /* background-color: black; */
  background-color: #f1f3f4 !important;
  height: 100%;
  transition: width 0.2s;
}
body {
  background: #333;
}
.el-header,
.el-footer {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 10px;
  padding: 0 0 0 0;
  margin: 0 0 0 0;
}
.el-main {
  background-color: #f1f3f4;
  color: #333;
  text-align: center;
  /* overflow: hidden; */
}
.content-container {
  position: relative;
  width: 60%;
  left: 20%;
  margin-top: 20px;
}
body {
  width: 100%;
  /* height: 100%; */
}
.main-container {
  width: 100%;
  height: 100%;
  position: absolute;
  overflow: hidden;
  margin: 0 0 0 0;
  padding: 0 0 0 0;
  background-color: #f1f1f1;
  top: 0;
  left: 0;
}

.main-container /deep/ ::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 7px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 7px;
  border-radius: 10px;
}
.main-container /deep/ ::-webkit-scrollbar-thumb {
  border-radius: 5px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}

@-webkit-keyframes animate-cloud {
  0% {
    background-position: 600px 100%;
  }
  to {
    background-position: 0 100%;
  }
}

.full-container {
  /* display: -webkit-box;
  display: -ms-flexbox; */
  /* display: flex; */
  /* -webkit-box-align: center;
  -ms-flex-align: center; */
  /* align-items: center; */
  position: absolute;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
  -webkit-animation: animate-cloud 20s linear infinite;
  animation: animate-cloud 20s linear infinite;
}
.el-main {
  padding: 0;
}

.el-menu--horizontal>.el-menu-item.is-active {
  /* border-bottom: 3px solid #409EFF; */
}
.el-menu .el-menu--horizontal {
  border-bottom-width: 0px!important ;
}

.el-header {
  background-color: transparent;
  background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
}
</style>
