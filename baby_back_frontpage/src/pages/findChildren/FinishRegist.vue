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
      <el-button type="success">返回首页</el-button>
      <el-button type="primary">查看提交信息</el-button>
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
    gotoHomePage(){

    },
    gotoDetail(){
      
    }
  }
};
</script>

<style scoped>
.finish-container {
  margin-top: 30px;
}

</style>
