<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane class="tab-container">
        <div slot="label" >
          <span class="font-size-2em"><i class="el-icon-user"></i></span>&nbsp&nbsp <span class="font-size-2em">登录</span>
        </div>
        <Login></Login>
      </el-tab-pane>
      <el-tab-pane>
        <div slot="label">
          <span class="font-size-2em"><i class="el-icon-document"></i></span>&nbsp&nbsp <span class="font-size-2em">注册</span>
        </div>
        <Regist></Regist>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Login from '@/pages/user/Login'
import Regist from '@/pages/user/Regist'
export default {
  name: "LoginOrRegist",
  components: {
    Login,
    Regist,
  },
  data() {
    return {
      activeName: "second"
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>

<style scoped>
.tab-container {
  height: 100%;
  
}
.content-container /deep/ .el-tabs__item {
  margin: 10px 0px 10px 0px;
}
.content-container .el-tabs {
  border-radius: 5px;
}
</style>
