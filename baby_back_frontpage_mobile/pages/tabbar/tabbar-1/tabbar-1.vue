<template>
	<view>
		<view class="mpvue-picker">
			<mpvue-picker :themeColor="themeColor" ref="mpvuePicker" :mode="mode" :deepLength="deepLength" :pickerValueDefault="pickerValueDefault"
			 @onConfirm="onConfirm" @onCancel="onCancel" :pickerValueArray="pickerValueArray"></mpvue-picker>
		</view>
		<!--搜索框-->
		<view class="cu-bar search bg-white">
			<view style="margin-left: 20upx;" @click="showSinglePicker">{{pickerLabel}}</view>
			<uni-icon type="arrowdown" color="#333333" size="22" @click="showSinglePicker"></uni-icon>
			<view class="search-form round" style="margin-left: 8upx;">
				<text class="cuIcon-search"></text>
				<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索宝贝" confirm-type="search"></input>
			</view>
			<view class="margin-tb-sm text-center">
				<button class="cu-btn round bg-blue" style="margin-right: 20upx;" @click="searchClick()">搜索</button>
			</view>
		</view>
		<!-- 选项卡 -->
		<scroll-view scroll-x class="bg-white nav text-center" :scroll-left="scrollLeft">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				走失
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				发现
			</view>
		</scroll-view>
		<!--图片布局-->
		<view class="grid col-2 grid-square" v-show="findShow">
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
			 v-for="(item,index) in findList" :key="index">
				<img :src="findList[index]" mode="aspectFill" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></img>
				<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">2019-5-20</text></view>
			</view>
		</view>
		<view class="grid col-2 grid-square" v-show="lostShow">
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
			 v-for="(item,index) in lostList" :key="index">
				<img :src="lostPicUrls[index]" mode="aspectFill" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></img>
				<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">{{ item.name }}</text></view>
			</view>
		</view>
		<view class="cu-tabbar-height"></view>
	</view>
</template>

<script>
	import mpvuePicker from '@/components/mpvue-picker/mpvuePicker.vue';
	import uniIcon from '@/components/uni-icon/uni-icon.vue'
	export default {
		components: {
			mpvuePicker,
			uniIcon
		},
		data() {
			return {
				findShow: false,
				lostShow: true,
				title: 'Hello',
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				enableScroll: true,
				InputBottom: 0,
				lostList: ['../../../static/img/release.png', '../../../static/img/release.png', '../../../static/img/release.png',
					'../../../static/img/release.png'
				],
				lostPicUrls: [],
				findList: [this.URLS.imgUrl + '/static/img/pkq.png', this.URLS.imgUrl + '/static/img/pkq.png', this.URLS.imgUrl +
					'/static/img/pkq.png', this.URLS.imgUrl + '/static/img/pkq.png', this.URLS.imgUrl + '/static/img/pkq.png', this.URLS
					.imgUrl + '/static/img/pkq.png', this.URLS.imgUrl + '/static/img/pkq.png'
				],
				themeColor: '#007AFF',
				mode: '',
				deepLength: 1,
				pickerValueDefault: [0],
				pickerLabel: '全部',
				pickerValueArray: [{
						label: '全部',
						value: 0
					},
					{
						label: '姓名',
						value: 1
					},
					{
						label: '特征',
						value: 2
					},
					{
						label: '失踪地点',
						value: 3
					}
				]
			};
		},
		onLoad:function(){
			alert("hah");
		},
		methods: {
			onCancel(e) {
				console.log(e);
			},
			// 单列
			showSinglePicker() {
				this.mode = 'selector';
				this.deepLength = 1;
				this.pickerValueDefault = [0];
				this.$refs.mpvuePicker.show();
			},
			onConfirm(e) {
				console.log(e.label);
				this.pickerLabel = e.label;
				//this.setStyle(0, e.label);
			},
			tabSelect(e) {
				this.tabCurrentIndex = e.currentTarget.dataset.id;
				if (this.tabCurrentIndex == 0) {
					this.lostShow = true;
					this.findShow = false
				} else {
					this.findShow = true;
					this.lostShow = false
				}
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			refreshData() {
				this.$api.post(this.URLS.lostBabyFindUrl, {
						page: 0
					})
					.then(data => {
						this.lostList = data.data.data.content;
						for (let i = 0; i < this.lostList.length; i++) {
							let id = this.lostList[i].id;
							console.log(this.lostList[i]);
							this.lostPicUrls.push(this.yieldPicUrl(id));
						}
					}).catch(error => {
						console.log(error)
					})

			},
			searchClick(e) {
				this.refreshData()
			},
			yieldPicUrl(id) {
				return this.URLS.baseUrl + "/resource/photo/lost/" + id + ".jpg"
			}
		},
		onPullDownRefresh() {
			console.log("Fuck Yourself");
		},
		onReachBottom() {
			console.log("Fuck Yourself");
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

	.my-tag {
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
		background-color: #0D9FFF;
	}
</style>
