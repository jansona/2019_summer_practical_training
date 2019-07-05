<template>
  <div>
    <el-card>
      <el-tabs @tab-click="handleClick" v-model="tab">
        <el-tab-pane label="照片匹配" name="first">
          <div class="clearfix">
            <PicUpload :type="2" @return-data="getData"></PicUpload>
          </div>
          <div class="text item">
            <div class="block"></div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="描述匹配" name="second">
          <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="input" @click="nlpMatch"></el-input>
          <br />
          <br />
          <el-col offset="20" span="4">
          <el-button type="primary" size="small" style="float:right">开始匹配</el-button></el-col>
        </el-tab-pane>
      </el-tabs>
      <!-- <div slot="header" class="clearfix">
      <PicUpload :type="2" @return-data="getData">
      </PicUpload>
    </div>
    <div class="text item">
      <div class="block">
      </div>
      </div>-->
    </el-card>
  </div>
</template>

<script>
import PicUpload from "@/pages/findChildren/PicUpload";
export default {
  name: "FsatMatch",
  data() {
    return {
      tab: "first",
      input: ""
    };
  },
  components: {
    PicUpload
  },
  methods: {
    getData(data) {
      console.log("waimian:", data);
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    nlpMatch(){
      if(this.input != ''){
        console.log('do nlp task')
      }else{
         this.$notify({
            message: "描述信息不能为空",
            type: "warning",
            duration: 1500,
            offset: 50
          });
      }
      
    }
    
  }
};
</script>

<style scoped>
</style>
