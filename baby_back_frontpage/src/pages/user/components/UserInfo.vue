<template>
  <el-card>
    <div slot="header" class="clearfix">
      <el-button style="float: right; padding: 1px">
      <el-button type="primary" icon="el-icon-edit">编辑</el-button>
      </el-button>
    </div>
    <div>
      <el-card :body-style="{ padding: '3px'}" style="float: left; padding: 1px" class="pic-container">
      <el-image :src="showImg(picUrl)">
        <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
      </el-card>
      <el-table
      :data="tableData"
      stripe
      style="width: 70%; float: right; padding: 1px"
      :show-header="false"
      size="small">
        <el-table-column prop="name" width="180"></el-table-column>
        <el-table-column prop="content"></el-table-column>
      </el-table>
    </div>
  </el-card>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
export default {
  name: 'UserInfo',
  props: {
    picUrl: String,
    tableData: Array
  },
  data () {
    return {
      
    }
  },
  methods: {
    showImg(url) {
      if (url === null || url === "") {
        // console.log('待加载的图片地址为:' + url)
        return require("@/assets/house.jpg");
      } else {
        // console.log('待加载的图片地址为:' + url)
        return url;
      }
    },
  }
}
</script>

<style scoped>

</style>
