<template>
  <div class="hello">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="icon-size">
          <i class="el-icon-edit"></i>
        </span>
        <span class="title-size">&nbsp&nbsp{{title}}</span>
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
        <fill-regist-info v-if="activeStep == 1" @on-next-step-click="handleNextStepClick"></fill-regist-info>
        <PicUpload v-if="activeStep == 2" :id="'dfgsdfg'"></PicUpload>
      </div>
    </el-card>
  </div>
</template>

<script>
import ChooseRegistType from '@/pages/findChildren/ChooseRegistType'
import FillRegistInfo from '@/pages/findChildren/FillRegistInfo'
import PicUpload from '@/pages/findChildren/PicUpload';
export default {
  name: "FindRegist",
  components: {
    ChooseRegistType,
    FillRegistInfo,
    PicUpload,
  },
  data() {
    return {
      activeStep:0,
      findType:1,
      title: "寻亲登记",
    };
  },
  methods: {
    handleTypeClick(type){
      console.log(type);
      this.findType = type;
      this.activeStep++;
      if(type==1){
        this.title += " - 家寻宝贝"
      } else if(type == 2) {
        this.title += " - 宝贝寻家"
      }
    },
    handleNextStepClick(){
      this.activeStep++;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.icon-size {
  font-size: 3em;
}
.title-size {
  font-size: 2em;
}
</style>
