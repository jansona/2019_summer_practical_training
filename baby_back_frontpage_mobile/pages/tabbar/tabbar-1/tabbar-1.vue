<template>
	<view>
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
		<view class="grid col-2 grid-square" v-show="findShow">
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
			 v-for="(item,index) in findList" :key="index">
				<image :src="findPicUrls[index]" mode="aspectFill" @click="goToDetail(item,index,0)" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></image>
				<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">{{ item.name }}</text></view>
			</view>
		</view>
		<view class="grid col-2 grid-square" v-show="lostShow">
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
			 v-for="(item,index) in lostList" :key="index">
				<image :src="lostPicUrls[index]" mode="aspectFill" @click="goToDetail(item,index,1)" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></image>
				<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">{{ item.name }}</text></view>
			</view>
		</view>
		<view class="uni-center" v-if="isBottom">
			<text style="color:#AAAAAA;">已经到底了</text>
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
		mounted: function() {
			if (this.searchInput != '') {

			} else {
				this.refreshData();
			}

		},
		data() {
			return {
				isFirst: true,
				isBottom: false,
				pageNo: 0,
				searchInput: '',
				totalPageNum: 2,
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
				// this.pageNo=0
				// this.lostPicUrls=[]
				// this.lostList=[]
				// this.findPicUrls=[]
				// this.findList=[]
				if (this.tabCurrentIndex == 0) {
					this.lostShow = true;
					this.findShow = false
				} else {
					this.findShow = true;
					this.lostShow = false
					if (this.isFirst && this.findList.length == 0) {
						this.pageNo = 0
						this.refreshData();
					}
					this.isFirst = false
				}
				// this.refreshData();
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			resetData() {
				this.pageNo = 0
				this.lostPicUrls = []
				this.lostList = []
				this.findPicUrls = []
				this.findList = []
				this.refreshData();
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			refreshData() {

				if (this.totalPageNum <= this.pageNo) {
					this.isBottom = true
					console.log('已经到底了')
					let _this = this
					setTimeout(function() {
						_this.isBottom = false;
					}, 1000);
					return;

				}
				if (this.lostShow) {
					let url = this.URLS.lostBabyFindUrl + '?size=7&page=' + this.pageNo
					this.pageNo += 1
					this.$api.post(url)
						.then(data => {
							this.totalPageNum = data.data.data.totalPages

							let appendList = data.data.data.content
							console.log(appendList)
							for (let i = 0; i < appendList.length; i++) {
								let id = appendList[i].id;
								console.log(appendList[i].picUrl);
								this.lostPicUrls.push(appendList[i].picUrl);
							}
							this.lostList = this.lostList.concat(appendList);
						}).catch(error => {
							console.log(error)
						})
				} else {
					let url = this.URLS.matchBabyFindUrl + '?size=7&page=' + this.pageNo
					this.pageNo += 1
					this.$api.post(url)
						.then(data => {
							this.totalPageNum = data.data.data.totalPages

							let appendList = data.data.data.content
							for (let i = 0; i < appendList.length; i++) {
								let id = appendList[i].id;
								this.findPicUrls.push(appendList[i].picUrl);
							}
							this.findList = this.findList.concat(appendList);
						}).catch(error => {
							console.log(error)
						})
				}

			},
			searchClick(e) {
				let _this = this;
				if (this.pickerLabel == '身高') {
					let url = this.URLS.lostBabyFindUrl + '?height=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						_this.lostPicUrls = [];
						for (let i = 0; i < _this.lostList.length; i++) {
							let id = _this.lostList[i].id;
							this.lostPicUrls.push(_this.lostList[i].picUrl);
						}

					}).catch(error => {
						console.log(error)
					});
					url = his.URLS.matchBabyFindUrl + '?height=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.findList = data.data.data.content;
						_this.findPicUrls = [];
						for (let i = 0; i < _this.findList.length; i++) {
							let id = _this.findList[i].id;
							this.findPicUrls.push(_this.findList[i].picUrl);
						}
					}).catch(error => {
						console.log(error)
					})
				} else if (this.pickerLabel == '姓名') {

					let url = this.URLS.lostBabyFindUrl + '?name=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						_this.lostPicUrls = [];
						for (let i = 0; i < _this.lostList.length; i++) {
							let id = _this.lostList[i].id;
							this.lostPicUrls.push(_this.lostList[i].picUrl);
						}

					}).catch(error => {
						console.log(error)
					});
					url = this.URLS.matchBabyFindUrl + '?name=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.findList = data.data.data.content;
						_this.findPicUrls = [];
						for (let i = 0; i < _this.findList.length; i++) {
							let id = _this.findList[i].id;
							this.findPicUrls.push(_this.findList[i].picUrl);
						}
					}).catch(error => {
						console.log(error)
					})
				} else if (this.pickerLabel == '籍贯') {
					let url = this.URLS.lostBabyFindUrl + '?nativePlace=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						_this.lostPicUrls = [];
						for (let i = 0; i < _this.lostList.length; i++) {
							let id = _this.lostList[i].id;
							this.lostPicUrls.push(_this.lostList[i].picUrl);
						}

					}).catch(error => {
						console.log(error)
					});
					url = his.URLS.matchBabyFindUrl + '?nativePlace=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.findList = data.data.data.content;
						_this.findPicUrls = [];
						for (let i = 0; i < _this.findList.length; i++) {
							let id = _this.findList[i].id;
							this.findPicUrls.push(_this.findList[i].picUrl);
						}
					}).catch(error => {
						console.log(error)
					})

				} else if (this.pickerLabel == '失踪地点') {
					let url = this.URLS.lostBabyFindUrl + '?place=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						_this.lostPicUrls = [];
						for (let i = 0; i < _this.lostList.length; i++) {
							let id = _this.lostList[i].id;
							this.lostPicUrls.push(_this.lostList[i].picUrl);
						}

					}).catch(error => {
						console.log(error)
					});
					url = this.URLS.matchBabyFindUrl + '?place=' + _this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.findList = data.data.data.content;
						_this.findPicUrls = [];
						for (let i = 0; i < _this.findList.length; i++) {
							// let id = _this.findList[i].id;
							this.findPicUrls.push(_this.findList[i].picUrl);
						}
					}).catch(error => {
						console.log(error)
					})
				}
			},
			goToDetail(item, index, flag) {
				if (flag == 0) {
					uni.navigateTo({
						url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item) + '&src=' + item.picUrl
					})
				} else if (flag == 1) {
					uni.navigateTo({
						url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item) + '&src=' + item.picUrl
					})
				}
			}
		}
	};
</script>

<style scoped>
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
