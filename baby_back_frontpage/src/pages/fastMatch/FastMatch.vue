<template>
<div>
  <el-card>
  <div slot="header" class="clearfix">
      <PicUpload :type="2" @return-data="getData">
      </PicUpload>
    </div>
    <div class="text item">
      <div class="block">
        <Pictures :datas="datas" :types="dataTypes" ref="pictures0" v-if="choosed===0"></Pictures>
      </div>
    </div> 
  </el-card>
</div>
</template>

<script>
import PicUpload from "@/pages/findChildren/PicUpload";
import Pictures from "../faceWall/components/Pictures";
export default {
  name: 'FsatMatch',
  data () {
    return {
      choosed: 0,
      datas: [],
      dataTypes: []
    }
  },
  components: {
    PicUpload,
    Pictures
  },
  methods: {
    getData(data){
      console.log("waimian:",data);
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
  }
}
</script>

<style scoped>
</style>
