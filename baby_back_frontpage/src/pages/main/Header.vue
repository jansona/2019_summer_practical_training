<template>
  <div class="head-container">
    <el-col :span="14" :offset="5" class="">
    <el-col :span="3">
      <img
      style="width: 14.24rem;height:4rem;max-width:100%;float:left"
      :src="require('@/assets/logo.png')"
    >
    </el-col>
    <el-col :span="1" :offset="4">
      <!-- <img
      @click="handleHeadImgClick"
      class="head-img"
      style="width:64px;height:64px;max-width:100%;float:left"
      :src="require('@/assets/home_page/nav_control_large.png')"
    > -->
    </el-col>
    <el-col :span="4">
      <el-input style="top:12px" placeholder="请输入内容" v-model="searchValue">
      <el-button slot="append" icon="el-icon-search"></el-button>
    </el-input>
    </el-col>
    <el-col :span="4" :offset="8">
      <!-- <el-image class="head-right-icon" :src='require("@/assets/icons/small/settings.png")'></el-image>
      <el-image class="head-right-icon" :src='require("@/assets/icons/small/user.png")'></el-image>
      <el-image class="head-right-icon" :src='require("@/assets/icons/small/message.png")'></el-image> -->
    </el-input>
    </el-col>
    </el-col>
  </div>
</template>
<script>
export default {
  name: "Header",
  data() {
    return {
      input21: "",
      searchValue: ""
    };
  },
  methods: {
    handleHeadImgClick() {
      this.$emit("headImgClick");
    }
  }
};
</script>
<style lang="less" scoped>
.red {
  background-color: red;
}

@media screen and (min-width: 100px) {
  .head-container {
    overflow: hidden;
    .head-img {
      width: 10px;
      height: 10px;
    }
  }
}
.head-container {
  color: rgb(172, 23, 23);
  height: 100%;
  /* margin: 10px */
}
.head-img {
  position: relative;
  top: 0;
  left: 0;
}
.head-right-icon {
  width: 30px;
  height: 30px;
  margin-top: 15px;
  margin-right: 10px;
}
</style>
