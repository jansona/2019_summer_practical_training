<template>
  <div class="regist-container">
    <el-form
      label-position="right"
      label-width="100px"
      :model="registForm"
      :rules="rules"
      ref="registForm"
    >
      <el-form-item label="用户名" prop="name">
        <el-input v-model="registForm.name" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="tel">
        <el-input v-model="registForm.tel" placeholder="请输入手机号码"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="verifacationCode">
        <el-input
          v-model="registForm.verifacationCode"
          type="verifacationCode"
          placeholder="请输入手机验证码"
        >
          <el-button slot="append" @click="sendSMS" :disabled="disabled">{{btnTitle}}</el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="密码" prop="passWord">
        <el-input v-model="registForm.passWord" placeholder="请输入密码" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="password2">
        <el-input v-model="registForm.password2" placeholder="请输入重复密码" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          class="fill-width"
          type="success"
          @click="regist"
          :loading="registingLoading"
        >立即注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { request,fetch } from "@/api/api";
import URLS from "@/config/config";
import axios from "axios";
export default {
  name: "Regist",
  inject: ["reload"],
  data() {
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
      if (!value) {
        return callback(new Error("请输入电话号码"));
      }
      setTimeout(() => {
        if (phoneReg.test(value)) {
          callback();
        } else {
          callback(new Error("请输入正确的电话号码"));
        }
      }, 100);
    };
    // <!--二次验证密码-->
    let validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registForm.passWord) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      btnTitle: "获取验证码",
      disabled: false, //是否可点击
      countdown: 60,
      registingLoading: false,
      registForm: {
        name: "",
        tel: "",
        verifacationCode: "",
        passWord: "",
        password2: ""
      },
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        tel: [{ required: true, validator: checkPhone, trigger: "blur" }],
        verifacationCode: [
          { required: true, message: "请输入手机验证码", trigger: "blur" }
        ],
        passWord: [{ required: true, message: "请输入密码", trigger: "blur" }],
        password2: [
          { required: true, validator: validatePass2, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    regist() {
      this.$refs.registForm.validate(valid => {
        if (valid) {
          this.registingLoading = true;
          axios
            .post(URLS.registUrl+"?code="+this.registForm.verifacationCode, this.registForm)
            .then(data => {
              console.log(data);
              this.registingLoading = false;
              if (data.data.rtnCode == 200) {
                  this.$notify({
                  message: "注册成功！",
                  type: "succcess",
                  duration: 1500,
                  offset: 50
                });
                this.$store.commit("setUserID", {
                  id: data.data.data.id,
                  flag: true
                });
                this.$router.push("home");
                this.reload();
              } else  {
                this.$message({
                  message: data.data.msg,
                  type: "warning"
                });
              }
            })
            .catch(error => {
              this.registingLoading = false;
              this.$message.error("服务器错误");
              console.log(error);
            });
        }
      });
    },
    sendSMS() {
      if (this.btnTitle == "获取验证码") {
        this.$refs.registForm.validateField("tel", error => {
          if (error != "") {
            return false;
          }
          this.disabled = true;
          let countTime = setInterval(() => {
            --this.countdown;
            this.btnTitle = this.countdown + "S";
            if (this.countdown <= 0) {
              clearInterval(countTime);
              this.countdown = 60;
              this.disabled = false;
              this.btnTitle = "获取验证码";
              return;
            }
          }, 1000);

          let phoneNum = this.registForm.tel;
          fetch(URLS.sendSMSUrl, {tel:phoneNum}).then(data => {
            console.log(data);
            if (data.rtnCode != 200) {
              this.$message({
                message: data.msg,
                type: "warning"
              });

              this.disabled = false;
              this.countdown = 60;
              this.btnTitle = "获取验证码";
            } else {
              this.$notify({
                  message: "发送成功",
                  type: "succcess",
                  duration: 1500,
                  offset: 50
                });
            }
          });
        });
      }
    }
  }
};
</script>

<style scoped>
.regist-container {
  width: 50%;
  position: relative;
  left: 25%;
}
</style>
