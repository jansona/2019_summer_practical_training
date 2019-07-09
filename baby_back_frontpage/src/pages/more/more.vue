<template>
  <div class="hello">
    <h1 style="position: absolute;
    top: 10%;
    font-size: 3em;
    left: 20%;">宝贝回家移动端</h1>
    <h2 style="position: absolute;
    left: 25%;
    top: 17%;">为每一个回家的宝贝找寻道路</h2>
    <el-image
      style="width: 300px;
    height: 300px;
    position: absolute;
    top: 10%;
    left: 60%;"
      :src="url"
      :fit="fit"
    ></el-image>
    <el-row :gutter="50" class="box-container1">
      <el-button type="primary" round style="width:150px;" @click="dialogVisible = true">立即加入</el-button>
    </el-row>
    <el-row :gutter="50" class="box-container2">
      <el-button type="primary" round style="width:150px;" @click="dialogVisible = true">立即加入</el-button>
    </el-row>


    <el-dialog
  title="宝贝回家移动端"
  :visible.sync="dialogVisible"
  width="50%"
  :before-close="handleClose">
  <el-row>
      <el-col span="7" offset="1">
   <el-image
      style=""
      :src="url"
      :fit="fit"
    ></el-image>
    <span>安卓版</span>
    </el-col>
    <el-col span="7" offset="1">
   <el-image
      style=""
      :src="url"
      :fit="fit"
    ></el-image><span>IOS版</span></el-col>
    <el-col span="7" offset="1">
   <el-image
      style=""
      :src="url"
      :fit="fit"
    ></el-image><span>微信小程序</span></el-col></el-row>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>

<script>
import { request } from "@/api/api";
import URLS from "@/config/config";
import axios from "axios";
export default {
  name: "more",
  inject: ["reload"],
  data() {
    return {
      fit: "fill",
      url: require("@/assets/QRCode.jpg"),
    dialogVisible: false
    };
  },
  methods: {},
  mounted() {
    // console.log("mouted",this.$refs.leftPart.clientHeight)
  }
};
</script>

<style scoped>
.hello {
  /* height: 1800px; */
  /* margin-top: -100px; */
  background-image: url(../../assets/more_bg_1.png);
  background-size: 100% auto;
  padding-bottom: 100%;
  position: relative;
}
li {
  list-style-type: none;
  margin-bottom: 20px;
  margin-left: -30px;
}
ul {
  padding-inline-start: 0px;
}
.flex-container /deep/ .el-divider {
  height: 226px;
  /* margin-left: 40px; */
}
.left-part {
  /* margin-left: 60px; */
}
.forget-pass {
  float: right;
}
.box-container1 {
  left: 13%;
  position: absolute;
  top: 57%;
}
.box-container2 {
  left: 68.5%;
  position: absolute;
  top: 75%;
}
</style>
