<template>
  <div>
    <el-row>
      <el-col :span="6" v-for="(o, index) in 4" :key="o">
        <div class="card-container">
          <el-card :body-style="{ padding: '3px' }" shadow="hover">
            <img
              src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
              class="image"
            >
            <div style="padding: 14px;">
              <span>好吃的汉堡</span>
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
  data() {
    return {
      currentDate: new Date(),
      data: [],
    };
  },
  methods: {}
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
  margin: 0 3px 0 3px;
}
.card-container:hover {
    cursor: pointer;
}
.card-container .is-hover-shadow:hover {
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.3);
}
</style>
