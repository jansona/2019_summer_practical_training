<template>
  <el-container class="main-container">
    <el-header class="bottom-shadow">
      <home-header @on-navbar-click="handleNavBarClick"></home-header>
    </el-header>
    <el-main>
      <home-content ref="content" class="content-container"></home-content>
    </el-main>
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
    handleNavBarClick(){
      // console.log("click!")
      this.$refs.content.reload();
    }
  },
  watch: {},
  mounted() {
    console.log("main mounted")
  }
};
</script>
<style scoped>
.bottom-shadow{
  box-shadow: 0px 10px 30px  #111111;
}
.bottom-shadow /deep/ .head-container{
  box-shadow: 0px 5px 10px  #888888;
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
.main-content-container {
  margin-top: 3px;
  /* overflow-y: hidden;
  height: 100%;
  overflow-x: hidden; */
}
.main-content-container /deep/ .el-submenu__title {
  line-height: 35px;
  height: 35px;
  font-family: "Hiragino Sans GB";
  font-size: 0.9rem;
  color: #6a6a6a;
}
.main-content-container /deep/ .el-menu-item {
  height: 35px;
  line-height: 35px;
  /* font-size: 12px; */
  font-family: "Hiragino Sans GB";
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

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
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
  width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 10px;
  border-radius: 10px;
}
.main-container /deep/ ::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}
</style>
