<template>
	<view>
		<view class="header">
			<view class="bg">
				<view class="box">
					<view class="box-hd">
						<view class="avator">
							<img src="../../static/logo.png"/>
						</view>
						<view class="nickname">{{user.username}}</view>
					</view>
				</view>
			</view>
		</view>
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" style="margin-top: -30upx;" @scroll="scroll">
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
		<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-show="lostShow" v-for="(item,index) in lostList" :key=index>
			<view class="padding bg-white">
				<view class="avatar">
					<image :src="lostPicUrls[index]" mode="aspectFit">
					</image>
				</view>
			</view>
			<form>
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
					<input disabled="true" :placeholder="item.birthday"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">籍贯</view>
					<input disabled="true" :placeholder="item.nativePlace"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">身高</view>
					<input disabled="true" :placeholder="item.height"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">特征</view>
					<input disabled="true" :placeholder="item.babyDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪日期</view>
					<input disabled="true" :placeholder="item.date"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪地点</view>
					<input disabled="true" :placeholder="item.place"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪过程</view>
					<input disabled="true" :placeholder="item.missDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他描述</view>
					<input disabled="true" :placeholder="item.otherDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他说明</view>
					<input disabled="true" :placeholder="item.otherExplain"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">联系人</view>
					<input disabled="true" :placeholder="item.contactName"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">手机号</view>
					<input disabled="true" :placeholder="item.contactPhone"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">邮箱</view>
					<input disabled="true" :placeholder="item.contactEmail"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他方式</view>
					<input disabled="true" :placeholder="item.otherContactMethod"></input>
				</view>
			</form>
		</view>
		<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-show="findShow" v-for="(item,index) in findList" :key=index>
			<view class="padding bg-white">
				<view class="avatar">
					<image :src="findPicUrls[index]" mode="aspectFit">
					</image>
				</view>
			</view>
			<form>
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
					<input disabled="true" :placeholder="item.birthday"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">籍贯</view>
					<input disabled="true" :placeholder="item.nativePlace"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">身高</view>
					<input disabled="true" :placeholder="item.height"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">特征</view>
					<input disabled="true" :placeholder="item.babyDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪日期</view>
					<input disabled="true" :placeholder="item.date"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪地点</view>
					<input disabled="true" :placeholder="item.place"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪过程</view>
					<input disabled="true" :placeholder="item.missDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他描述</view>
					<input disabled="true" :placeholder="item.otherDescription"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他说明</view>
					<input disabled="true" :placeholder="item.otherExplain"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">联系人</view>
					<input disabled="true" :placeholder="item.contactName"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">手机号</view>
					<input disabled="true" :placeholder="item.contactPhone"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">邮箱</view>
					<input disabled="true" :placeholder="item.contactEmail"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">其他方式</view>
					<input disabled="true" :placeholder="item.otherContactMethod"></input>
				</view>
			</form>
		</view>
		<view v-for="(item, index) in articleList" :key="index" class="cu-card dynamic" v-show="articleShow">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"></view>
						<view class="content flex-sub" style="margin-top:5upx;">
							<view>{{item.user.username}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{item.date}}
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
					<view class="desc"><view class="text-content">{{item.content}}</view></view>
				</view>
				<view class="text-gray text-sm text-right padding">
					<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewNum}}
					<text class="cuIcon-appreciatefill margin-lr-xs" @click="articleLike(item)"></text> {{item.likeNum}}
					<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.replyNum}}
				</view>
			</view>
		</view>
	</view>
	
</template>

<script>
	export default{
		onLoad(options) {
			//把JSON字符串转换为对象
			this.user.id = JSON.parse(options.id);
			this.getUser(this.user.id);
			this.getArticleList();
			this.getFindList();
			this.getLostList();
		},
		data(){
			return{
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				lostShow: true,
				findShow: false,
				articleShow: false,
				articleList:[],
				user:{},
				lostList:[],
				lostPicUrls:[],
				findList:[],
				findPicUrls:[],
			}
		},
		methods: {
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
					this.articleShow=false;
				} else if(this.tabCurrentIndex==1){
					this.findShow = true;
					this.lostShow = false;
					this.articleShow=false;
				} else{
					this.findShow = false;
					this.lostShow = false;
					this.articleShow= true;
				}
				// this.refreshData();
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			navToDetails(item){
				console.log(item);
				let data = item;
				uni.navigateTo({
					url: '/pages/tabbar-2-detail/passage-detail?data='+JSON.stringify(data)
				})
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + this.user.id;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data;
					console.log(_this.user)
				}).catch(error => {
					console.log(error)
				})
			},
			articleLike(item){
				console.log('点赞...');
				let url=this.URLS.articleLikeUrl+'?article_id='+item.id;
				this.$api.post(url).then(data => {
					item.likeNum+=1;
					console.log(data)
				}).catch(error => {
					console.log(error)
				})
			},
			getArticleList(){
				let _this = this;
				
				let articleurl = this.URLS.articleFindByUserUrl + '?user='+this.user.id
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
			getLostList(){
				let losturl =this.URLS.lostBabyFindByUserUrl+'?user='+this.user.id;
				this.$api.post(losturl)
					.then(data => {
						console.log(data);
						_this.lostList = data.data.data.content;
						console.log(_this.lostList);
						for (let i = 0; i < _this.lostList.length; i++) {
							let id = _this.lostList[i].id;
							_this.lostPicUrls.push(this.URLS.baseUrl + "/resource/photo/lost/" + id + ".jpg");
						}
					}).catch(error => {
						console.log(error)
					})
								
			},
			getFindList(){
				let findurl =this.URLS.matchBabyFindByUserUrl +'?user='+this.user.id
				this.$api.post(findurl)
					.then(data => {
						_this.findList = data.data.data.content;
						for (let i = 0; i < _this.findList.length; i++) {
							let id = _this.findList[i].id;
							_this.findPicUrls.push(this.URLS.baseUrl + "/resource/photo/match/" + id + ".jpg")
						}
					}).catch(error => {
						console.log(error)
					})
			}
		},
	}
</script>

<style lang="scss">
	page{
		background-color:#f1f1f1;
		font-size: 30upx;
	}
	.header{
		background: #fff;
		height: 290upx;
		padding-bottom: 110upx;
		.bg{
			width: 100%;
			height:200upx;
			padding-top:100upx;
			background-color:#4191ea;
		}
	}
	.box{
		width: 500upx;
		height: 140upx;
		border-radius: 20upx;
		margin: 0 auto;
		background: #fff;
		box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2); 
		.box-hd{
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			.avator{
				width: 160upx;
				height: 160upx;
				background: #fff;
				border: 5upx solid #fff;
				border-radius: 50%;
				margin-top: -80upx;
				overflow: hidden;
				img{
					width: 100%;
					height: 100%;
				}
			}
			.nickname{
				width: 100%;
				text-align: center;
			}
		}
		.box-bd{
			display: flex;
			flex-wrap: nowrap;
			flex-direction: row;
			justify-content: center;
			.item{
				flex: 1 1 auto;
				display: flex;
				flex-wrap: wrap;
				flex-direction: row;
				justify-content: center;
				border-right: 1px solid #f1f1f1;
				margin: 15upx 0;
				&:last-child{
					border: none;
				}
				.icon{
					width: 60upx;
					height: 60upx;
					img{
						width: 100%;
						height: 100%;
					}
				}
				.text{
					width: 100%;
					text-align: center;
					margin-top: 10upx;
				}
			}
		}
	},
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
