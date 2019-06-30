<template>
  <div>
    <el-tabs type="border-card" :stretch="true" class="main-container">
      <el-tab-pane class="tab-container">
        <div slot="label" class="regist-tab">
          <span class="font-size-2em">
            <i class="el-icon-user"></i>
          </span>&nbsp&nbsp
          <span class="font-size-2em">登录</span>
        </div>
        <Login class="content-margin"></Login>
      </el-tab-pane>
      <el-tab-pane>
        <div slot="label" class="regist-tab">
          <span class="font-size-2em">
            <i class="el-icon-document"></i>
          </span>&nbsp&nbsp
          <span class="font-size-2em">注册</span>
        </div>
        <Regist class="content-margin"></Regist>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Login from "@/pages/user/Login";
import Regist from "@/pages/user/Regist";
export default {
  name: "LoginOrRegist",
  components: {
    Login,
    Regist
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

<style lang="scss" scoped>
.tab-container {
  height: 100%;
}
.content-container /deep/ .el-tabs__item {
  /* margin: 10px 0px 10px 0px; */
}
.content-container /deep/ .is-active {
  height: 100%;
}
.content-container .el-tabs {
  border-radius: 5px;
}
.regist-tab {
  width: 100%;
  padding-top: 8px;
  /* padding-bottom: 5px; */
}
.content-margin {
  margin-top: 50px;
}
.main-container {
  width: 1000px;
  margin: 50px auto;
}

@-webkit-keyframes animate-cloud {
  0% {
    background-position: 600px 100%;
  }
  to {
    background-position: 0 100%;
  }
}

$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  position: relative;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
  -webkit-animation: animate-cloud 20s linear infinite;
  animation: animate-cloud 20s linear infinite;

  .login-weaper {
    margin: 0 auto;
    width: 1000px;
    -webkit-box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
    box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
  }

  .bounceInDown {
    -webkit-animation-name: bounceInDown;
    animation-name: bounceInDown;
  }

  .animated {
    -webkit-animation-duration: 1s;
    animation-duration: 1s;
    -webkit-animation-fill-mode: both;
    animation-fill-mode: both;
  }

  .login-time {
    position: absolute;
    left: 25px;
    top: 25px;
    width: 100%;
    color: #fff;
    font-weight: 400;
    opacity: 0.9;
    font-size: 20px;
    overflow: hidden;
  }

  .login-left {
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    background-color: #409eff;
    color: #fff;
    float: left;
    width: 50%;
    position: relative;
    min-height: 500px;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
  }

  .login-right {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border-left: none;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
    color: #fff;
    background-color: #fff;
    width: 50%;
    float: left;
    min-height: 500px;
  }

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    margin: 0 auto;
    overflow: hidden;
    padding: 0 56px;
  }

  .svg-container {
    position: absolute;
    top: -5px;
    font-size: 14px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
    padding: 6px 5px 6px 15px;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .el-input {
    position: initial !important;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #606266;
      margin: 50px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .el-icon-user::before {
    content: "\e6e3";
  }

  .show-pwd {
    position: absolute;
    right: 33px;
    top: 0px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
