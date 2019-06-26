<template>
  <div>
    <el-card shadow="hover" class="card-style">
      <el-col>
        <el-col :span="6">
          <el-image :src="showImg(picUrl)" class="type-pic-style div-right">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-col>
        <el-col :span="12" :offset="3">
          <span class="font-size-1-5em">{{title}}</span>
          <br>
          <br>
          <br>
          <span>{{content}}</span>
        </el-col>
      </el-col>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "TypeCard",
  props: {
    title:String,
    content:String,
    picUrl: {
      type:String,
      require: true
    }
  },
  watch: {
  },
  data() {
    return {};
  },
  methods: {
    showImg(url) {
      if (url === null || url === "") {
        // console.log('待加载的图片地址为:' + url)
        return require("@/assets/house.jpg");
      } else {
        // console.log('待加载的图片地址为:' + url)
        return require("@/assets/"+ url +"");
      }
    },
    
  }
};
</script>

<style scoped>
.type-pic-style {
  width: 150px;
  height: 150px;
  border-radius: 5px;
  /* float: left; */
  /* margin-bottom: 20px; */
  position: relative;
  left: 0;
  margin-bottom: 15px;
  margin-left: -5px;
}
.card-style :hover {
  cursor: pointer;
}
</style>
