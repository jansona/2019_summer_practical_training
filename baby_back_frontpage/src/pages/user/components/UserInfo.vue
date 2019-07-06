<template>
  <el-card>
    <div slot="header">
      <el-button type="primary" icon="el-icon-edit" style="float: right; padding: 1px">编辑</el-button>
    </div>
    <div>
      <el-card :body-style="{ padding: '3px'}" style="float: left; padding: 1px" class="pic-container">
        <el-avatar shape="square" :size="100" :fit="contain" :src="showImg(picUrl)"></el-avatar>
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
