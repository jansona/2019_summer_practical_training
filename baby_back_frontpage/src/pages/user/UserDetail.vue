<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
    </div>
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="商品名称">
            <span>{{ props.row.name }}</span>
          </el-form-item>
          <el-form-item label="所属店铺">
            <span>{{ props.row.shop }}</span>
          </el-form-item>
          <el-form-item label="商品 ID">
            <span>{{ props.row.id }}</span>
          </el-form-item>
          <el-form-item label="店铺 ID">
            <span>{{ props.row.shopId }}</span>
          </el-form-item>
          <el-form-item label="商品分类">
            <span>{{ props.row.category }}</span>
          </el-form-item>
          <el-form-item label="店铺地址">
            <span>{{ props.row.address }}</span>
          </el-form-item>
          <el-form-item label="商品描述">
            <span>{{ props.row.desc }}</span>
          </el-form-item>
        </el-form>
      </template>
  </el-card>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
export default {
  name: 'UserDetail',
  data () {
    return {

    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
