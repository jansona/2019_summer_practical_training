<template>
	<view class="uni-tab-bar">
		<view class="cu-modal" :class="modalName=='Modal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">提示</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					{{modalContent}}
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-blue text-blue" @tap="hideModal">取消</button>
						<button class="cu-btn bg-blue margin-left" @tap="deleteConfirm">确定</button>
		
					</view>
				</view>
			</view>
		</view>
		<!-- 选项卡 -->
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				亲人寻子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				游子寻家
			</view>
		</scroll-view>
		<swiper class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="onChangeSwiper">
			<swiper-item v-for="(content,index1) in pageData">
				<scroll-view scroll-y="true" style="height: calc(100% );" @scrolltolower="refreshData(index1)">
					<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;"  v-for="(item,index) in content.data" :key="index">
						<view class="padding bg-white">
							<text class="cuIcon-deletefill" style="margin-left: 570upx;color: #999999;margin-left: 550upx;" @click="deleteRelease(item)"></text>
							<view class="avatar">
								<image :src="item.picUrl" mode="aspectFit">
								</image>
							</view>
						</view>
						<form>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">姓名</view>
								<input disabled="true" :placeholder="item.name"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">出生日期</view>
								<input disabled="true" :placeholder="item.birthday"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">身高</view>
								<input disabled="true" :placeholder="item.height"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪日期</view>
								<input disabled="true" :placeholder="item.date"></input>
							</view>
							<view class="cu-form-group">
								<view class="title" style="width: 150upx;">失踪地点</view>
								<input disabled="true" :placeholder="item.place"></input>
							</view>
						</form>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
		<!-- <view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-show="lostShow" v-for="(item,index) in lostList" :key=index>
			<view class="padding bg-white">
				<text class="cuIcon-deletefill" style="margin-left: 570upx;color: #999999;margin-left: 550upx;" @click="deleteRelease(item)"></text>
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
					<view class="title" style="width: 150upx;">出生日期</view>
					<input disabled="true" :placeholder="item.birthday"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">身高</view>
					<input disabled="true" :placeholder="item.height"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪日期</view>
					<input disabled="true" :placeholder="item.date"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪地点</view>
					<input disabled="true" :placeholder="item.place"></input>
				</view>
			</form>
		</view>
		<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;" v-show="findShow" v-for="(item,index) in findList" :key=index>
			<view class="padding bg-white">
				<text class="cuIcon-deletefill" style="margin-left: 570upx;color: #999999;margin-left: 550upx;" @click="deleteRelease(item)"></text>
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
					<view class="title" style="width: 150upx;">出生日期</view>
					<input disabled="true" :placeholder="item.birthday"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">身高</view>
					<input disabled="true" :placeholder="item.height"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪日期</view>
					<input disabled="true" :placeholder="item.date"></input>
				</view>
				<view class="cu-form-group">
					<view class="title" style="width: 150upx;">失踪地点</view>
					<input disabled="true" :placeholder="item.place"></input>
				</view>
				
			</form>
		</view> -->
	</view>
</template>

<script>
	export default {
		mounted:function() {
			uni.setNavigationBarTitle({
				title: '我的发布'
			});
			this.getData();
		},
		data() {
			return {
				pageData: [{
					title: "亲人寻子",
					index: 0,
					totalPage: 1,
					curPage: 0,
					data: [],
				}, {
					title: "游子寻家",
					index: 1,
					totalPage: 1,
					curPage: 0,
					data: [],
				}],
				
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				lostList:[],
				lostPicUrls:[],
				findList:[],
				findPicUrls:[],
				userid:this.$store.state.userId,
				lostShow:true,
				findShow:false,
				modalName:null,
				modalContent:null,
				deleteid:-1
			}
		},
		methods: {
			async onChangeSwiper(e) {
				let index = e.target.current;
				this.tabCurrentIndex = index
			},
			hideModal(e) {
				this.modalName = null
			},
			tabSelect(e) {
				this.tabCurrentIndex = e.currentTarget.dataset.id;
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			getData() {
				let _this=this;
				
					let losturl =this.URLS.lostBabyFindByUserUrl+'?user='+this.userid;
					this.$api.post(losturl)
						.then(data => {
							console.log(data);
							_this.pageData[0].data = data.data.data.content;
							_this.pageData[0].data.push({})
							_this.pageData[0].data.pop()
						}).catch(error => {
							console.log(error)
						})
				
					let findurl =this.URLS.matchBabyFindByUserUrl +'?user='+this.userid
					this.$api.post(findurl)
						.then(data => {
							_this.findList = data.data.data.content;
							_this.pageData[1].data = data.data.data.content;
							_this.pageData[1].data.push({})
							_this.pageData[1].data.pop()
						}).catch(error => {
							console.log(error)
						})
				
			
			},
			deleteRelease(item){
				this.modalName='Modal';
				this.modalContent='您确定要你删除这则发布吗？'
				console.log(item)
				this.deleteid=item.id
			},
			deleteConfirm(){
				let url=null;
				if(this.tabCurrentIndex==0){
					url = this.URLS.lostBabyDeleteUrl + '?id=' + this.deleteid;
				}else{
					url = this.URLS.matchBabyDeleteUrl + '?id=' + this.deleteid;
				}
				let _this = this
				this.$api._delete(url).then(data => {
					this.modalName=null;
					this.myToast('删除成功')
					this.getData()
				}).catch(error => {
					this.modalName=null;
					this.myToast('删除失败')
					console.log(error)
				})
			}
		},
	}
</script>

<style scoped>
	uni-page-body uni-view {
		/* height: 100%; */
	}
</style>
