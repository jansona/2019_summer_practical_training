<template>
  <div class="infinite-list-wrapper">
    <ul
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled">
      <li v-for="i in articles" class="list-item" v-bind:key='i'>{{ i }}</li>
    </ul>
    <p v-if="loading">加载中...</p>
    <p v-if="noMore">没有更多了</p>
  </div>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";

export default {
  name: "ArticleInfiniteList",
  props:{
    // articles: Array
    user: Number
  },
  data () {
    return {
      count: 10,
      loading: false,
      articles: [],
      index: 0
    }
  },
  computed: {
    noMore () {
      return this.count >= 100
    },
    disabled () {
      return this.loading || this.noMore
    }
  },
  methods: {
    load () {
      this.loading = true
      request(URLS.articleInfiniteUrl, {user: this.user, index: this.index})
      .then(
        data => {
          if(data.rtnCode === 200){
            console.log(data);
            this.articles.push(data.data.data);
            this.loading = false;
          }
        }
      )
      .catch(
        error => {
          console.log(error);
        }
      )
      // setTimeout(() => {
      //   this.count += 2
      //   this.loading = false
      // }, 2000)
    }
  }
}
</script>

<style scoped>

</style>
