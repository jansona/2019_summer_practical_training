<template>
  <div class="hello">
      
      <el-row :gutter="50" class="box-container">
    <el-button type="primary" round>立即加入</el-button>
    </el-row>
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
    
    };
  },
  methods: {
  
  },
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
.box-container {
  width: 72%;
  left: 15%;
  position: absolute;
  top: 53%;
}
</style>
