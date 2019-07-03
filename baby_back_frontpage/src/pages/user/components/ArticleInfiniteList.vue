<template>
  <div>
    <ul class="infinite-list" v-infinite-scroll="load">
      <li v-for="i in articles" class="infinite-list-item">{{ i }}</li>
    </ul>
  </div>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
export default {
  name: 'ArticleInfiniteList',
  props: {
    articles: Array
  },
  data () {
    return {
    }
  },
  methods: {
    load () {
      this.count += 2
    }
  }
}
</script>

<style scoped>

</style>
