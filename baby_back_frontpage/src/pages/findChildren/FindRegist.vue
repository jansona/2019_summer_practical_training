<template>
  <div class="hello">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="font-size-3em">
          <i class="el-icon-edit"></i>
        </span>
        <span class="font-size-2em">&nbsp&nbsp{{title}}</span>
      </div>
      <div>
        <el-steps :active="activeStep" simple finish-status="success">
          <el-step title="选择登记类型" icon="el-icon-collection-tag"></el-step>
          <el-step title="填写登记信息" icon="el-icon-tickets"></el-step>
          <el-step title="上传照片" icon="el-icon-picture"></el-step>
          <el-step title="完成登记" icon="el-icon-success"></el-step>
        </el-steps>
      </div>
      <div>
        <choose-regist-type @on-choose-type-click="handleTypeClick" v-if="activeStep == 0"></choose-regist-type>
        <fill-regist-info v-if="activeStep == 1" @on-next-step-click="handleNextStepClick" :fillType="findType"></fill-regist-info>
        <PicUpload
          v-if="activeStep == 2"
          :id="'dfgsdfg'"
          @on-prior-step-click="handlePriorStepClick"
          @on-next-step-click="handleNextStepClick"
        ></PicUpload>
        <FinishRegist v-if="activeStep == 3"></FinishRegist>
      </div>
    </el-card>
  </div>
</template>

<script>
import ChooseRegistType from "@/pages/findChildren/ChooseRegistType";
import FillRegistInfo from "@/pages/findChildren/FillRegistInfo";
import PicUpload from "@/pages/findChildren/PicUpload";
import FinishRegist from '@/pages/findChildren/FinishRegist';
export default {
  name: "FindRegist",
  components: {
    ChooseRegistType,
    FillRegistInfo,
    PicUpload,
    FinishRegist,
  },
  data() {
    return {
      activeStep: 0,
      findType: 1,
      title: "寻亲登记"
    };
  },
  methods: {
    handleTypeClick(type) {
      console.log(type);
      this.findType = type;
      this.activeStep++;
      if (type == 1) {
        this.title += " - 家寻宝贝";
      } else if (type == 2) {
        this.title += " - 宝贝寻家";
      }
    },
    handleNextStepClick() {
      this.activeStep++;
    },
    handlePriorStepClick() {
      this.activeStep--;
    }
  },
  mounted() {
    // console.log("mounted");
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
