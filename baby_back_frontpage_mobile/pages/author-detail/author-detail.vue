<template>
	<view class="uni-tab-bar">
		<view class="header">
			<view class="bg">
				<view class="box">
					<view class="box-hd">
						<view class="avator">
							<img :src="user.profileUrl" />
						</view>
						<view class="nickname">{{user.username}}</view>
					</view>
				</view>
			</view>
		</view>
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" style="margin-top: -30upx;"
		 @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				TA的寻子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				TA的寻亲
			</view>
			<view class="cu-item" :class="2==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="2">
				TA的帖子
			</view>
		</scroll-view>
		<swiper class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="onChangeSwiper" style="height: 850upx">
			<swiper-item>
				<scroll-view scroll-y="true" style="height: 100%;">
					<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-for="(item,index) in lostList"
					 :key="index">
						<view class="padding bg-white">
							<view class="avatar">
								<image :src="item.picUrl" mode="aspectFit" @click="goToDetail(item)">
								</image>
							</view>
						</view>
						<form @click="goToDetail(item)">
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">姓名</view>
								<input disabled="true" :placeholder="item.name"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">性别</view>
								<input disabled="true" :placeholder="item.sex"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">出生日期</view>
								<input disabled="true" :placeholder="dateFormat(item.birthday)"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">身高</view>
								<input disabled="true" :placeholder="item.height"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪日期</view>
								<input disabled="true" :placeholder="dateFormat(item.date)"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪地点</view>
								<input disabled="true" :placeholder="item.place"></input>
							</view>
						</form>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item>
				<scroll-view scroll-y="true" style="height: 100%;">
					<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-for="(item,index) in findList"
					 :key="index">
						<view class="padding bg-white">
							<view class="avatar">
								<image :src="findPicUrls[index]" mode="aspectFit" @click="goToDetail(item)">
								</image>
							</view>
						</view>
						<form @click="goToDetail(item)">
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">姓名</view>
								<input disabled="true" :placeholder="item.name"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">性别</view>
								<input disabled="true" :placeholder="item.sex"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">出生日期</view>
								<input disabled="true" :placeholder="dateFormat(item.birthday)"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">身高</view>
								<input disabled="true" :placeholder="item.height"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪日期</view>
								<input disabled="true" :placeholder="dateFormat(item.date)"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪地点</view>
								<input disabled="true" :placeholder="item.place"></input>
							</view>
						</form>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item>
				<scroll-view scroll-y="true" style="height: 100%;">
					<view v-for="(item, index) in articleList" :key="index" class="cu-card dynamic">
						<view class="cu-item shadow">
							<view class="cu-list menu-avatar">
								<view class="cu-item">
									<view class="cu-avatar round lg" style="overflow: hidden;">
										<image :src="item.user.profileUrl" mode="aspectFit"></image>
									</view>
									<view class="content flex-sub" style="margin-top:5upx;">
										<view>{{item.user.username}}</view>
										<view class="text-gray text-sm flex justify-between">
											{{dateFormat(item.date)}}
										</view>
									</view>
								</view>
							</view>
							<view class="title" @click="navToDetails(item)">
								<view class="text-cut" style="font-weight: 700;margin-left: 15upx;margin-top: 15upx;">{{item.title}}</view>
							</view>
							<view class="passage-content" style="margin-top: 5upx;" @click="navToDetails(item)">
								<!-- <view class="bg-img" v-for="(item1,index1) in item.imgList" :key="index1">
						<image :src="item1.value" style="max-height:200upx;max-width: 200upx;"></image>
					</view> -->
								<view class="desc">
									<view class="text-content">{{item.content}}</view>
								</view>
							</view>
							<view class="text-gray text-sm text-right padding">
								<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewNum}}
								<text class="cuIcon-appreciatefill margin-lr-xs" @click="articleLike(item)"></text> {{item.likeNum}}
								<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.replyNum}}
							</view>
						</view>
					</view>
					<!-- <view style="height: 250upx;"></view> -->
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	export default {
		onLoad(options) {
			//把JSON字符串转换为对象
			this.user.id = JSON.parse(options.id);
			this.getUser(this.user.id);
			this.getArticleList();
			this.getFindList();
			this.getLostList();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				lostShow: true,
				findShow: false,
				articleShow: false,
				articleList: [],
				user: {},
				lostList: [],
				lostPicUrls: [],
				findList: [],
				findPicUrls: [],
			}
		},
		methods: {
			async onChangeSwiper(e) {
				let index = e.target.current;
				this.tabCurrentIndex = index
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
					this.findShow = false;
					this.articleShow = false;
				} else if (this.tabCurrentIndex == 1) {
					this.findShow = true;
					this.lostShow = false;
					this.articleShow = false;
				} else {
					this.findShow = false;
					this.lostShow = false;
					this.articleShow = true;
				}
				// this.refreshData();
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			navToDetails(item) {
				console.log(item);
				let data = item;
				uni.navigateTo({
					url: '/pages/tabbar-2-detail/passage-detail?data=' + JSON.stringify(data)
				})
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data;
					console.log(_this.user)
				}).catch(error => {
					console.log(error)
				})
			},
			articleLike(item) {
				console.log('点赞...');
				let url = this.URLS.articleLikeUrl + '?article_id=' + item.id;
				this.$api.post(url).then(data => {
					item.likeNum += 1;
					console.log(data)
				}).catch(error => {
					console.log(error)
				})
			},
			getArticleList() {
				let _this = this;

				let articleurl = this.URLS.articleFindByUserUrl + '?user=' + this.user.id
				console.log(articleurl);
				this.$api.post(articleurl)
					.then(data => {
						console.log(data);
						_this.articleList = data.data.data.content;
						console.log(_this.articlList)
					}).catch(error => {
						console.log(error)
					})
			},
			getLostList() {
				let _this = this
				let losturl = this.URLS.lostBabyFindByUserUrl + '?user=' + this.user.id;
				this.$api.post(losturl)
					.then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						console.log(_this.lostList);
					}).catch(error => {
						console.log(error)
					})

			},
			getFindList() {
				let _this = this
				let findurl = this.URLS.matchBabyFindByUserUrl + '?user=' + this.user.id
				this.$api.post(findurl)
					.then(data => {
						_this.findList = data.data.data.content;
						for (let i = 0; i < _this.findList.length; i++) {
							//let id = _this.findList[i].id;
							_this.findPicUrls.push(_this.findList[i].picUrl)
						}
					}).catch(error => {
						console.log(error)
					})
			},
			goToDetail(item) {
				uni.navigateTo({
					url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item) + '&src=' + item.picUrl
				})
			},
			dateFormat(time) {
			 	var date = new Date(time);
			 	var year = date.getFullYear();
			 	/* 在日期格式中，月份是从0开始的，因此要加0
			 	 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
			 	 * */
			 	var month =
			 		date.getMonth() + 1 < 10 ?
			 		"0" + (date.getMonth() + 1) :
			 		date.getMonth() + 1;
			 	var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
			 	var hours =
			 		date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
			 	var minutes =
			 		date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
			 	var seconds =
			 		date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
			 	// 拼接
			 	return (
			 		year +
			 		"-" +
			 		month +
			 		"-" +
			 		day +
			 		" " +
			 		hours +
			 		":" +
			 		minutes +
			 		":" +
			 		seconds
			 	);
			 }
		},
	}
</script>

<style lang="scss">
	page {
		background-color: #f1f1f1;
		font-size: 30upx;
	}

	.header {
		background: #fff;
		height: 290upx;
		padding-bottom: 110upx;

		.bg {
			width: 100%;
			height: 200upx;
			padding-top: 100upx;
			background-color: #4191ea;
		}
	}

	.box {
		width: 500upx;
		height: 140upx;
		border-radius: 20upx;
		margin: 0 auto;
		background: #fff;
		box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2);

		.box-hd {
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;

			.avator {
				width: 160upx;
				height: 160upx;
				background: #fff;
				border: 5upx solid #fff;
				border-radius: 50%;
				margin-top: -80upx;
				overflow: hidden;

				img {
					width: 100%;
					height: 100%;
				}
			}

			.nickname {
				width: 100%;
				text-align: center;
			}
		}

		.box-bd {
			display: flex;
			flex-wrap: nowrap;
			flex-direction: row;
			justify-content: center;

			.item {
				flex: 1 1 auto;
				display: flex;
				flex-wrap: wrap;
				flex-direction: row;
				justify-content: center;
				border-right: 1px solid #f1f1f1;
				margin: 15upx 0;

				&:last-child {
					border: none;
				}

				.icon {
					width: 60upx;
					height: 60upx;

					img {
						width: 100%;
						height: 100%;
					}
				}

				.text {
					width: 100%;
					text-align: center;
					margin-top: 10upx;
				}
			}
		}
	}

	,
	.content {
		margin-top: 150upx;
	}

	,
	.passage-content {
		height: 30upx;
		margin-top: 0;
	}

	,
	.text-content {
		padding-left: 10upx;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical;
	}
</style>
