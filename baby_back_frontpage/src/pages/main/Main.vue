<template>
  <el-container class="main-container">
    <el-header>
      <home-header @headImgClick="handleHeadImgClick"></home-header>
    </el-header>
    <el-main>
      <home-content></home-content>
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
      console.log(indexPath);
    },
    handleHeadImgClick() {}
  },
  watch: {}
};
</script>
<style scoped>
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
/* 调整滑动条样式 */
/* .main-content-container .el-aside::-webkit-scrollbar {
  width: 8px;
}
.main-content-container .el-aside::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}
.main-content-container .el-aside::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: rgba(0, 0, 0, 0.1);
} */
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
  width: 60%;
  left: 20%;
  position: relative;
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
