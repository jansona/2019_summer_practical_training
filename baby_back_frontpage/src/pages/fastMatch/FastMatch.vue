<template>
  <div>
    <el-card>
      <el-tabs @tab-click="handleClick" v-model="tab">
        <el-tab-pane label="照片匹配" name="first">
          <div class="clearfix">
            <PicUpload :type="2" @return-data="getData"></PicUpload>
          </div>
          <el-divider></el-divider>
          <div class="text item">
            <div class="block">
              <Pictures :datas="datas" :types="dataTypes" ref="pictures0" v-if="choosed===0"></Pictures>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="描述匹配" name="second">
          <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="input" @click="nlpMatch"></el-input>
          <br />
          <br />
          <el-col :offset="20" :span="4">
            <el-button type="primary" size="small" style="float:right" @click="nlpAnalyze">开始匹配</el-button>
          </el-col>
          <br>
          <el-divider></el-divider>
          <div class="text item">
            <div class="block">
              <Pictures :datas="datas" :types="dataTypes" ref="pictures0" v-if="choosed===0"></Pictures>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import URLS from '@/config/config';
import { request,fetch } from "@/api/api";
import PicUpload from "@/pages/findChildren/PicUpload";
import Pictures from "../faceWall/components/Pictures";
export default {
  name: "FsatMatch",
  data() {
    return {
      choosed: 0,
      datas: [],
      dataTypes: [],
      tab: "first",
      input: ""
    };
  },
  components: {
    PicUpload,
    Pictures
  },
  methods: {
    getData(data) {
      console.log("waimian:", data);
      if (data.rtnCode == 200) {
        if (this.choosed == -1) this.choosed = 0;
        this.datas = JSON.parse(JSON.stringify(data.data));
        this.dataTypes = [];
        for (let i = 0, len = this.datas.length; i < len; i++) {
          this.dataTypes.push(this.choosed);
        }
        this.$refs.pictures0.reloadData();
        let saved = this.choosed;
        this.choosed = -1;
        this.$nextTick(function() {
          this.choosed = saved;
        });
      }
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    nlpMatch() {
      if (this.input != "") {
        console.log("do nlp task");
      } else {
        this.$notify({
          message: "描述信息不能为空",
          type: "warning",
          duration: 1500,
          offset: 50
        });
      }
    },
    nlpAnalyze(){
      request(URLS.uploadTxtAndRecogUrl, {'txt': this.input})
      .then(
        data => {
          console.log(data);
          if (data.rtnCode == 200) {
            if (this.choosed == -1) this.choosed = 0;
            this.datas = JSON.parse(JSON.stringify(data.data));
            this.dataTypes = [];
            for (let i = 0, len = this.datas.length; i < len; i++) {
              this.dataTypes.push(this.choosed);
            }
            this.$refs.pictures0.reloadData();
            let saved = this.choosed;
            this.choosed = -1;
            this.$nextTick(function() {
              this.choosed = saved;
            });
          }
        }
      )
    }
  }
};
</script>

<style scoped>
</style>
