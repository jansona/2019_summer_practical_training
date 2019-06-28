<template>
  <div class="finish-container">
    <div>
      <span class="font-size-5em">
        <i class="el-icon-finished"></i>
      </span> &nbsp&nbsp
      <span class="font-size-2em">恭喜完成登记！</span>
    </div>
    <el-divider></el-divider>
    <div>
      <el-button type="success" @click="gotoHomePage">返回首页</el-button>
      <el-button type="primary" @click="gotoDetail">查看提交信息</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "FinishRegist",
  data() {
    return {};
  },
  methods: {
    gotoHomePage() {
      this.$router.push({ name: "Home" });
    },
    gotoDetail() {
      this.$router.push({ name: "FaceDetail", query: { type: 1, id:  this.$store.state.imageId } });
    }
  }
};
</script>

<style scoped>
.finish-container {
  margin-top: 30px;
}
</style>
