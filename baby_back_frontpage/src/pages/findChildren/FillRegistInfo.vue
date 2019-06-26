<template>
  <div class="form-container">
    <el-form
      :model="missing_person"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="姓名" prop="name">
        <el-input v-model="missing_person.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="missing_person.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出生日期" required>
        <!-- <el-col :span="22"> -->
          <el-form-item prop="birthday" class="fill-width">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="missing_person.birthday"
              style="width: 100%;"
            ></el-date-picker>
          </el-form-item>
        <!-- </el-col> -->
      </el-form-item>
      <el-form-item label="身高(cm)" prop="height" required>
        <el-slider v-model="missing_person.height" :min="100" :max="250"></el-slider>
      </el-form-item>
      <el-form-item label="失踪人籍贯" prop="native_location">
        <el-input v-model="missing_person.nativePlace"></el-input>
      </el-form-item>
      <el-form-item label="失踪日期" required>
          <el-form-item prop="lost_date">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="missing_person.date"
              style="width: 100%;"
            ></el-date-picker>
          </el-form-item>
      </el-form-item>
      <el-form-item label="失踪时地址" prop="lost_location">
        <el-input v-model="missing_person.place"></el-input>
      </el-form-item>
      <el-form-item label="失踪人特征描述" prop="desc">
        <el-input type="textarea" v-model="missing_person.babyDescription"></el-input>
      </el-form-item>
      <el-form-item label="失踪经过" prop="desc">
        <el-input type="textarea" v-model="missing_person.missDescription"></el-input>
      </el-form-item>
      <el-form-item label="其余信息" prop="desc">
        <el-input type="textarea" v-model="missing_person.otherDescription"></el-input>
      </el-form-item>
      <el-form-item label="其余说明" prop="desc">
        <el-input type="textarea" v-model="missing_person.otherExplain"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { request, fetch } from "@/api/api";
import axios from 'axios';
import URLS from "@/config/config";
export default {
  name: "FillRegistInfo",
  data() {
    return {
      missing_person: {
        name: "",
        height: 0,
        birthday: "",
        place: "",
        nativePlace: "",
        date: "",
        babyDescription: "",
        missDescription: "",
        backGround: "",
        otherDescription: "",
        otherExplain: "",
        relationship: "",

        user: {
          user_id: 2
        }
      },
      rules: {
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" }
        ],
        nativePlace: [
          { required: true, message: "请输入籍贯", trigger: "change" }
        ],
        birthday: [
          {
            type: "date",
            required: false,
            message: "请选择出生日期",
            trigger: "change"
          }
        ],
        lost_date: [
          {
            type: "date",
            required: false,
            message: "请选择失踪时间",
            trigger: "change"
          }
        ],
        
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert("submit!");
          axios.post(URLS.fillFormUrl, this.missing_person)
            .then(data => {
              console.log("success", data);
              this.$emit("on-next-step-click");
            })
            .catch(error => {
                
            });

        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped>
.form-container {
  width: 50%;
  left: 25%;
  position: relative;
  margin-top: 20px;
}
</style>
