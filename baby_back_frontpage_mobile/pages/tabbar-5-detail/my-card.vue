<template>
	<view>
		<!-- 选项卡 -->
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				我的帖子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				我的评论
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		mounted:function() {
			let userid=2;
			this.getUser(userid);
			console.log(this.user);
			this.getData();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				articlList:[],
				commentList:[],
				user:{},
				articleShow:true,
				commentShow:false
			}
		},
		methods: {
			tabSelect(e) {
				this.tabCurrentIndex = e.currentTarget.dataset.id;
				if (this.tabCurrentIndex == 0) {
					this.lostShow = true;
					this.findShow = false
				} else {
					this.findShow = true;
					this.lostShow = false
				}
				// this.refreshData();
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			getData() {
				let _this=this;
				if(this.lostShow){
					let url =this.URLS.lostBabyFindByUserUrl +'?user='+this.user;
					console.log(url);
					this.$api.post(url)
						.then(data => {
							_this.lostList = data.data.data.content
							for (let i = 0; i < _this.lostList.length; i++) {
								let id = _this.lostList[i].id;
								_this.lostPicUrls.push(_this.yieldPicUrl(id));
							}
						}).catch(error => {
							console.log(error)
						})
				}else{
					let url =this.URLS.matchBabyFindByUserUrl +'?user='+this.user
					this.$api.post(url)
						.then(data => {
							_this.findList = data.data.data.content;
							for (let i = 0; i < _this.findList.length; i++) {
								let id = _this.findList[i].id;
								_this.findPicUrls.push(_this.yieldPicUrl(id));
							}
						}).catch(error => {
							console.log(error)
						})
				}
			
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data
				}).catch(error => {
					console.log(error)
				})
			}
		},
	}
</script>

<style>
</style>
