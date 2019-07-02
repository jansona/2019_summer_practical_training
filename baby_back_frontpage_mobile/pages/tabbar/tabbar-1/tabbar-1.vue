<template>
	<view>
		<!-- 顶部选项卡 -->
		<scroll-view scroll-x class="bg-white nav text-center" :scroll-left="scrollLeft">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				走失
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				发现
			</view>
		</scroll-view>
		
		<!--搜索框-->
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索宝贝" confirm-type="search"></input>
			</view>
			<view class="margin-tb-sm text-center">
				<button class="cu-btn round bg-blue" style="margin-right: 20upx;">搜索</button>
			</view>
		</view>
		
		<!--图片布局-->
		<view class="grid col-2 grid-square">
			<!-- <view class="cu-card" v-for="(item,index) in findList" :key="index">
				<view class="bg-img" :style="[{ backgroundImage:'url(' + findList[index] + ')' }]" style="border-radius: 20upx;overflow: hidden;"></view>
			</view> -->
			<!-- <view class="bg-img" v-for="(item,index) in findList" :key="index" :style="[{ backgroundImage:'url(' + findList[index] + ')' }]" style="width: 330upx;height: 330upx;margin-top: 15upx;margin-left: 20upx;margin-right: 20upx;border-radius: 10upx;overflow: hidden;"></view> -->
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;" v-for="(item,index) in findList" :key="index">
				<image :src="findList[index]" mode="aspectFill" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></image>
					<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">2019-5-20</text></view>
			</view>
		</view>
	
		<!-- <view class="cu-card" v-for="(item,index) in lostList" :key="index">
			<view class="cu-item shadow">
					<view style="text-align: center;">
						<view class="cu-tag bg-red light sm round">小花</view>
						<view class="cu-tag bg-green light sm round">2019-6-28失踪</view>
					</view>
					<image :src="lostList[index]" mode="aspectFit" style="size: 500upx;border-radius: 20upx;overflow: hidden;margin-left: 27upx;"></image>
			</view>
		</view> -->
		<view class="cu-tabbar-height"></view>
	</view>
</template>

<script>
export default {
	
	data() {
		return {
			title: 'Hello',
			tabCurrentIndex: 0, //当前选项卡索引
			scrollLeft: 0, //顶部选项卡左滑距离
			enableScroll: true,
			InputBottom:0,
			lostList:['../../../static/img/release.png','../../../static/img/release.png','../../../static/img/release.png','../../../static/img/release.png'],
			findList:['../../../static/img/pkq.png','../../../static/img/pkq.png','../../../static/img/pkq.png','../../../static/img/pkq.png','../../../static/img/pkq.png']
		};
	},
	onLoad() {},
	methods: {
		tabSelect(e) {
			this.tabCurrentIndex = e.currentTarget.dataset.id;
			this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
		},
		InputFocus(e) {
			this.InputBottom = e.detail.height
		},
		InputBlur(e) {
			this.InputBottom = 0
		},
		searchClick(e){
			
		}
	}
};
</script>
	
<style>
.page {
	height: auto;
	min-height: 100%;
}
.content {
	text-align: center;
	height: 400upx;
	margin-top: 200upx;
}
.my-tag{
	margin-left: 100upx;
	vertical-align: middle;
	position: relative;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	box-sizing: border-box;
	padding: 0upx 16upx;
	height: 35upx;
	font-family: Helvetica Neue, Helvetica, sans-serif;
	white-space: nowrap;
	opacity: 0.3;
	max-width: 150upx;
	max-height: 35upx;
	border-radius: 20upx;
	overflow: hidden;
	background-color:#0D9FFF;
}

</style>
