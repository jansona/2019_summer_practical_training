<template>
  <div class="main-container">
    <el-row :gutter="25">
      <el-col :span="6" v-for="(o, index) in datas" :key="index">
        <div class="card-container" :ref="'image'+index" @click="gotoDetail(types[index],o.id)">
          <el-card :body-style="{ padding: '3px' }" shadow="hover">
            <div class="image-container" :style=" {backgroundImage:'url('+picUrls[index]+')'}"></div>
            <div class="text-inline">
              <span>{{o.name}}</span>
              <span class="grey-text">{{o.place}}</span>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
export default {
  name: "Pictures",
  props: {
    datas: Array,
    types: Array
  },
  data() {
    return {
      currentDate: new Date(),
      data: [],
      rows: [],
      picUrls: []
    };
  },
  methods: {
    click() {},
    reloadData() {
      for (var i = 0, len = this.datas.length; i < len; i++) {
        let id = this.datas[i].id;
        let urlType = this.types[i] == 0 ? "lost" : "match";
        this.$set(
          this.picUrls,
          i,
          this.datas[i].picUrl
        );
      }
    },
    gotoDetail(type,id) {
      this.$router.push({ name: "FaceDetail", query: { type: 1, id: id } });
    }
  },
  mounted() {
    this.reloadData();
  }
};
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.card-container {
  margin-bottom: 20px;
}
.card-container:hover {
  cursor: pointer;
}
.card-container .is-hover-shadow:hover {
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.3);
}

.main-container {
  margin: 0 10px 0 10px;
}
.image-container {
  width: 100%;
  height: 0;
  padding-bottom: 120%;
  /* overflow: hidden; */
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
}
.text-inline {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 100%;
  padding: 7px;
  margin-left: -7px;
}
.grey-text {
  color: #999;
}
</style>
