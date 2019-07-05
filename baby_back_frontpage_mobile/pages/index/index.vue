<template>
	<view>
        <view class="content-container">
		<tabbar_1 v-if="curPage == '1'" ref="tab1"></tabbar_1>
		<tabbar_4 v-if="curPage == '2'"></tabbar_4>
		<tabbar_3 v-if="showNewPop" @onAddClick="closeAddNav"></tabbar_3>
		<tabbar_2 v-if="curPage == '4'"></tabbar_2>
		<tabbar_5 v-if="curPage == '5'"></tabbar_5>
		</view>
		<view class="cu-bar tabbar bg-white shadow foot">
			<view class="action" @click="NavChange" data-cur="1">
				<view style="margin:0 auto;margin-top: 5upx;margin-bottom: -20upx;">
					<image style="width: 90upx;height: 90upx;" :src="'/static/img/tabbar/find' + [curPage=='1'?'active':''] + '.png'"></image>
				</view>
			</view>
			<view class="action" @click="NavChange" data-cur="2">
				<view style="margin:0 auto;margin-top: 5upx;margin-bottom: -20upx;">
					<image style="width: 90upx;height: 90upx;" :src="'/static/img/tabbar/urgence' + [curPage == '2'?'active':''] + '.png'"></image>
				</view>
			</view>
			<view class="action text-gray add-action" @click="onAddClick" data-cur="plugin">
				<button class="cu-btn cuIcon-add bg-blue shadow"></button>
				<!-- <image style="width: 100upx;height: 100upx;" :src="'/static/img/tabbar/release.png'"></image> -->
			</view>
			<!-- <view class="action" @click="onAddClick" data-cur="plugin">
				<view class='my-add-btn'>
					<image style="width: 100upx;height: 100upx;" :src="'/static/img/tabbar/release' + [curPage == '3'?'active':''] + '.png'"></image>
				</view>
			</view> -->
			<view class="action" @click="NavChange" data-cur="4">
				<view style="margin:0 auto;margin-top: 5upx;margin-bottom: -20upx;">
					<image style="width: 90upx;height: 90upx;" :src="'/static/img/tabbar/bbc' + [curPage == '4'?'active':''] + '.png'"></image>
				</view>
			</view>
			<view class="action" @click="NavChange" data-cur="5">
				<view style="margin:0 auto;margin-top: 5upx;margin-bottom: -20upx;">
					<image style="width: 90upx;height: 90upx;" :src="'/static/img/tabbar/my' + [curPage == '5'?'active':''] + '.png'"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		onReady:function () {
			this.$refs.tab1.refreshData();
		},
		data() {
			return {
				curPage: "1",
				showNewPop: false,
			}
		},
		onPullDownRefresh() {
			console.log('funcker')
			this.$refs.tab1.resetData()
			setTimeout(() => {
            uni.stopPullDownRefresh();
        }, 2000);

		},
		onReachBottom() {
			console.log('me')
			this.$refs.tab1.refreshData()
		},
		onLoad(){
			
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
			},
            closeAddNav(e){
                console.log(e)
                this.showNewPop = false;
            }
		},
		
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
		padding: 18upx 0 19upx 0;
	}
	
	.my-add-btn image {
		width: 70upx;
		height: 70upx;
		/* display: inline-block; */
	}
	.index-bar-text {
		margin-bottom: 10upx;
	}
    .content-container {
        
    }
</style>
