<template>
	<view>
		<tabbar_1 v-if="curPage == '1'"></tabbar_1>
		<tabbar_4 v-if="curPage == '2'"></tabbar_4>
		<tabbar_3 v-if="showNewPop"></tabbar_3>
		<tabbar_2 v-if="curPage == '4'"></tabbar_2>
		<tabbar_5 v-if="curPage == '5'"></tabbar_5>
		
		<view class="cu-bar tabbar bg-grey-index shadow foot">
			<view class="action" @click="NavChange" data-cur="1">
				<view class='cuIcon-cu-image'>
					<image :src="'/static/img/tabbar/home' + [curPage=='1'?'active':''] + '.png'"></image>
				</view>
				<view class="index-bar-text" :class="curPage=='1'?'selected-color':'text-gray'">首页</view>
			</view>
			<view class="action" @click="NavChange" data-cur="2">
				<view class='cuIcon-cu-image'>
					<image :src="'/static/img/tabbar/guanzhu' + [curPage == '2'?'active':''] + '.png'"></image>
				</view>
				<view class="index-bar-text" :class="curPage=='2'?'selected-color':'text-gray'">紧急</view>
			</view>
			<view class="action" @click="onAddClick" data-cur="plugin">
				<view class='my-add-btn'>
					<image :src="'/static/img/tabbar/add' + [curPage == '3'?'active':''] + '.png'"></image>
				</view>
				<!-- <view :class="curPage=='plugin'?'text-green':'text-gray'">发布</view> -->
			</view>
			<view class="action" @click="NavChange" data-cur="4">
				<view class='cuIcon-cu-image'>
					<image :src="'/static/img/tabbar/news' + [curPage == '4'?'active':''] + '.png'"></image>
				</view>
				<view class="index-bar-text" :class="curPage=='4'?'selected-color':'text-gray'">论坛</view>
			</view>
			<view class="action" @click="NavChange" data-cur="5">
				<view class='cuIcon-cu-image'>
					<image :src="'/static/img/tabbar/me' + [curPage == '5'?'active':''] + '.png'"></image>
				</view>
				<view class="index-bar-text" :class="curPage=='5'?'selected-color':'text-gray'">我</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				curPage: "1",
				showNewPop: false,
			}
		},
		methods: {
			NavChange: function(e) {
				this.curPage = e.currentTarget.dataset.cur
				this.showNewPop = false
			},
			onAddClick: function(message) {
				console.log(message)
				// this.curPage = "asd"
				if(!this.showNewPop){
					this.showNewPop = true
				} else {
					this.showNewPop = false
				}
			}
		}
	}
</script>

<style>
	.bg-grey-index {
		background-color: #333;
	}
	.selected-color {
		color: #f33e54;
	}
	.my-add-btn {
		margin: 0 auto;
		padding: 19upx 0 19upx 0;
	}
	
	.my-add-btn image {
		width: 70upx;
		height: 70upx;
		/* display: inline-block; */
	}
	.index-bar-text {
		margin-bottom: 10upx;
	}
</style>
