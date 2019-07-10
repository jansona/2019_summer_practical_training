<template>
	<view class="uni-tab-bar" style="height: 100%;">
		<view class="topView"></view>
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
				<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" v-model="searchInput" type="text" placeholder="搜索宝贝"
				 confirm-type="search"></input>
			</view>
			<view class="margin-tb-sm text-center">
				<button class="cu-btn round bg-blue" style="margin-right: 20upx;" @click="searchClick">搜索</button>
			</view>
		</view>
		<!-- 选项卡 -->
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				走失
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				发现
			</view>
		</scroll-view>
		<!--图片布局-->
		<swiper class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="onChangeSwiper">
			<swiper-item v-for="(content,index1) in pageData">
				<scroll-view scroll-y="true" style="height: calc(100% - 100upx);" @scrolltolower="refreshData(index1)">
					<view class="grid col-2 grid-square" style="z-index: 10;">
						<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
						 v-for="(item,index2) in content.data" :key="index2">
							<img :src="item.picUrl" mode="aspectFill" @click="goToDetail(item,index1)" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></img>
							<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">{{ item.name }}</text></view>
						</view>
					</view>
					<view class="uni-tab-bar-loading">
						{{content.loadingMsg}}
					</view>
					<view class="cu-tabbar-height"></view>
				</scroll-view>
			</swiper-item>
		</swiper>
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
		mounted: function() {
			if (this.searchInput != '') {

			} else {
				this.refreshData(0)
				this.refreshData(1)
			}

		},
		data() {
			return {
				pageData: [{
					title: "走失",
					index: 0,
					totalPage: 1,
					curPage: 0,
					loadingMsg: "正在加载...",
					data: [],
				}, {
					title: "发现",
					index: 1,
					totalPage: 1,
					curPage: 0,
					loadingMsg: "正在加载...",
					data: [],
				}],

				isFirst: true,
				isBottom: false,
				pageNo: 0,
				searchInput: '',
				findShow: false,
				lostShow: true,
				title: 'Hello',
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				enableScroll: true,
				InputBottom: 0,
				lostList: [],
				lostPicUrls: [],
				findList: [],
				findPicUrls: [],
				themeColor: '#007AFF',
				mode: '',
				deepLength: 1,
				pickerValueDefault: [0],
				pickerLabel: '姓名',
				pickerValueArray: [{
						label: '姓名',
						value: 0
					},
					{
						label: '身高',
						value: 1
					},
					{
						label: '籍贯',
						value: 2
					},
					{
						label: '失踪地点',
						value: 3
					}
				]
			};
		},
		methods: {
			async onChangeSwiper(e) {
				let index = e.target.current;
				this.tabCurrentIndex = index
			},
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
				console.log("selected:", e)
				this.tabCurrentIndex = e.currentTarget.dataset.id;
			},
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			resetData(index) {
				if (index == null) index = this.tabCurrentIndex
				this.refreshData(index, false)
			},
			refreshData(index, flag) { // flag 用来表示是添加还是刷新
				if (index == null) {
					index = this.tabCurrentIndex
				}
				if (flag == null) flag = true
				let curData = this.pageData[index]

				curData.loadingMsg = "正在加载..."
				let url = ''
				if (index == 0) {
					url = this.URLS.lostBabyFindUrl + '?size=6&page=' + curData.curPage
				} else if (index == 1) {
					url = this.URLS.matchBabyFindUrl + '?size=6&page=' + curData.curPage
				}
				curData.curPage++
				this.$api.post(url)
					.then(data => {
						curData.totalPage = data.data.data.totalPages
						let appendList = data.data.data.content
						if (flag)
							curData.data = curData.data.concat(appendList);
						else {
							curData.data = appendList;
							curData.totalPage = 1
							curData.curPage = 0
							curData.loadingMsg = "正在加载..."
						}
						curData.data.push({})
						curData.data.pop()
					}).catch(error => {
						console.log(error)
					})
				console.log(this.pageData)

				if (curData.totalPage <= curData.curPage) {
					this.isBottom = true
					console.log('已经到底了')
					curData.loadingMsg = "已经到底了"
					return;
				}
			},
			async  searchClick(e) {
				let _this = this;
				if (this.pickerLabel == '身高') {
					let urls = [this.URLS.lostBabyFindUrl + '?height=' + _this.searchInput, this.URLS.matchBabyFindUrl + '?height=' +
						_this.searchInput
					];
					for (var i = 0; i < urls.length; i++) {
						this.$api.post(urls[i]).then(data => {
							console.log(data);
							_this.pageData[i].data = data.data.data.content;
						}).catch(error => {
							console.log(error)
						});
					}
				} else if (this.pickerLabel == '姓名') {
					let urls = [this.URLS.lostBabyFindUrl + '?name=' + _this.searchInput, this.URLS.matchBabyFindUrl + '?name=' +
						_this
						.searchInput
					];
					for (var i = 0; i < urls.length; i++) {
						let getData = new Promise(() => {
							this.$api.post(urls[i]).then(data => {
								console.log(data,this.pageData[i],data.data.data.content);
								_this.pageData[i].data = data.data.data.content;
							}).catch(error => {
								console.log(error)
							});
						})
						await getData;
					}
				} else if (this.pickerLabel == '籍贯') {
					let urls = [this.URLS.lostBabyFindUrl + '?nativePlace=' + _this.searchInput, this.URLS.matchBabyFindUrl +
						'?nativePlace=' + _this.searchInput
					];
					for (var i = 0; i < urls.length; i++) {
						this.$api.post(urls[i]).then(data => {
							console.log(data);
							_this.pageData[i].data = data.data.data.content;
						}).catch(error => {
							console.log(error)
						});
					}
				} else if (this.pickerLabel == '失踪地点') {
					let urls = [this.URLS.lostBabyFindUrl + '?place=' + _this.searchInput, this.URLS.matchBabyFindUrl + '?place=' +
						_this
						.searchInput
					];
					for (var i = 0; i < urls.length; i++) {
						this.$api.post(urls[i]).then(data => {
							console.log(data);
							_this.pageData[i].data = data.data.data.content;
						}).catch(error => {
							console.log(error)
						});
					}
				}
				for(var i = 0 ; i < 2 ; i++){
					this.pageData[i].totalPage = 1
					this.pageData[i].curPage = 0
					this.pageData[i].data.push({})
					this.pageData[i].data.pop()
				}
			},
			goToDetail(item,flag) {
				if (flag == 0) {
					uni.navigateTo({
						url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item)
					})
				} else if (flag == 1) {
					uni.navigateTo({
						url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item)
					})
				}
			}
		}
	};
</script>

<style scoped>
	.uni-tab-bar-loading {
		text-align: center;
		font-size: 28upx;
		color: #999;
	}
	
	.topView {
		width: 100%;
		height: var(--status-bar-height);
		background-color: #FFFFFF;
	}

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
