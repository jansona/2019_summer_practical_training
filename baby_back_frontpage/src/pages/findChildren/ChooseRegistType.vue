<template>
  <div class="main-container">
    <div @click="chooseType(1)">
      <type-card title="家寻宝贝" picUrl="xunzi.png" content='是指家长寻找失踪、被拐时16周岁以下的儿童，可在"家寻宝贝"版块进行寻亲登记。'></type-card>
    </div>
    <br>
    <br>
    <div @click="chooseType(2)">
      <type-card
        title="宝贝寻家"
        picUrl="xunjia.png"
        content='对于自幼被拐、走失及送养的孩子（16周岁以下儿童），可在"宝贝寻家"版块里进行寻亲登记。'
      ></type-card>
    </div>
  </div>
</template>

<script>
import TypeCard from "@/pages/findChildren/components/TypeCard";
export default {
  name: "ChooseRegistType",
  components: {
    TypeCard
  },
  data() {
    return {};
  },
  methods: {
    chooseType(type) {
      this.$emit("on-choose-type-click",type);
    }
  }
};
</script>

<style scoped>
.main-container {
  margin-top: 50px;
}
.card-style :hover {
  cursor: pointer;
}
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
.el-card__body {
  padding: 10px 10px 10px 10px;
}
</style>
