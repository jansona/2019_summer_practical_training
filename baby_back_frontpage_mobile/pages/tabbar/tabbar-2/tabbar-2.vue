<template>
	<view>
		<!--模态框提示-->
		<view class="cu-modal" :class="modalName=='Modal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">发布提示</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					{{modalContent}}
				</view>
			</view>
		</view>
		<view class="mpvue-picker">
			<mpvue-picker
				:themeColor="themeColor"
				ref="mpvuePicker"
				:mode="mode"
				:deepLength="deepLength"
				:pickerValueDefault="pickerValueDefault"
				@onConfirm="onConfirm"
				@onCancel="onCancel"
				:pickerValueArray="pickerValueArray"
			></mpvue-picker>
		</view>
		<view class="cu-bar search bg-white">
			<view style="margin-left: 20upx;" @click="showSinglePicker">{{pickerLabel}}</view>
			<uni-icon type="arrowdown" color="#333333" size="22" @click="showSinglePicker"></uni-icon>
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" v-model="searchInput" type="text" placeholder="搜索帖子" confirm-type="search"></input>
			</view>
			<view class="margin-tb-sm text-center">
				<button class="cu-btn round bg-blue" style="margin-right: 20upx;" @click="searchClick">搜索</button>
			</view>
		</view>
		<view v-for="(item, index) in articleList" :key="index" class="cu-card dynamic" @click="navToDetails(item)">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item" @click="navToDetails(item)">
						<view class="cu-avatar round lg"></view>
						<view class="content flex-sub">
							<view>{{item.user.name}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{item.date}}
							</view>
						</view>
					</view>
				</view>
				<view class="title">
					<view class="text-cut" style="font-weight: 700;margin-left: 15upx;margin-top: 15upx;">{{item.title}}</view>
				</view>
				<view class="passage-content" style="margin-top: 5upx;">
					<!-- <view class="bg-img" v-for="(item1,index1) in item.imgList" :key="index1">
						<image :src="item1.value" style="max-height:200upx;max-width: 200upx;"></image>
					</view> -->
					<view class="desc"><view class="text-content">{{item.content}}</view></view>
				</view>
				<view class="text-gray text-sm text-right padding">
					<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewNum}}
					<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.likeNum}}
					<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.replyNum}}
				</view>
			</view>
        <!-- <view class="cu-tabbar-height"></view> -->
		</view>
		<!-- <view class="cu-card dynamic">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg);"></view>
						<view class="content flex-sub">
							<view>{{author}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{date}}
							</view>
						</view>
					</view>
				</view>
				<view class="title">
					<view class="text-cut" style="font-weight: 700;padding-left: 10upx;">{{tittle}}</view>
				</view>
				<view class="passage-content" style="margin-top: 5upx;">
					<image src="../../../static/logo.png" style="max-height:200upx;max-width: 200upx;"></image>
					<image src="../../../static/logo.png" style="max-height:200upx;max-width: 200upx;"></image>
					<view class="desc"><view class="text-content">{{content}}</view></view>
				</view>
			</view>
		</view> -->
		<!-- <view class="cu-card article">
			<view class="cu-item shadow">
				<view class="title"><view class="text-cut">{{tittle}}</view></view>
				<view class="text-content">
					<view class="desc">
						<view>
							<view class="cu-tag bg-red light sm round">{{author}}</view>
							<view class="cu-tag bg-green light sm round">{{date}}</view>
						</view>
						<view class="text-content">{{content}}</view>
					</view>
				</view>
			</view>
		</view> -->
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
		mounted:function () {
			if(this.articleList.length<1)
			{
				this.refreshData();
			}
		},
		data() {
			return {
				isBottom:false,
				searchInput:'',
				pageNo :0,//当前页面数
				totalPageNum:2,
				modalName:null,
				modalContent:null,
				InputBottom:0,
				//author:'贺谷牛牛',
				//date:'2019-6-27',
				//tittle:'用心耕耘 深情护送宝贝回家 —志愿者小梅寻亲成功案例的启示',
				//content:"朋友，当大家合家团圆、安享幸福的生活的时候，你有没有想到，这个世界上还有这样的一个群体。他们像浮萍一样漂浮地生活在这个世界上，脑海中残留的是童年心酸的灰色回忆，过的是度日如年的思亲生活。他们有自己的名字，却并不知道自己真正姓甚名谁",
				//imgList:[],
				articleList:[],
				themeColor: '#007AFF',
				mode: '',
				deepLength: 1,
				pickerValueDefault: [0],
				pickerLabel:'内容',
				pickerValueArray: [
					{
						label: '内容',
						value: 0
					},
					{
						label: '用户',
						value: 1
					}
				]
			}
		},
		methods: {
			hideModal(e) {
				this.modalName = null
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
				this.pickerLabel=e.label;
				//this.setStyle(0, e.label);
			},
			resetData(){
				console.log('RESET...')
				this.pageNo=0;
				this.articleList=[];
				this.refreshData();
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			refreshData() {
				console.log('REFRESH...')
				if(this.totalPageNum<=this.pageNo){
					this.isBottom=true
					console.log('已经到底了')
					let _this = this
					setTimeout(function(){ _this.isBottom=false; }, 1000);
					return;
					
				}
				let url =this.URLS.articleFindUrl +'?size=5&page='+this.pageNo;
				this.pageNo +=1;
				this.$api.post(url)
					.then(data => {
						this.totalPageNum = data.data.data.totalPages;
						let appendList = data.data.data.content
						this.articleList = this.articleList.concat(appendList);
					}).catch(error => {
						console.log(error)
					})
			},
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			searchClick(e){
				let _this=this;
				if(this.pickerLabel=='用户'){
					let url=this.URLS.articleFindByUserNameUrl+'?user_name='+_this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.articleList=data.data.data.content;
					}).catch(error => {
						console.log(error)
					})
				}else if(this.pickerLabel=='内容'){
					let url=this.URLS.articleFindUrl+'?key_word='+_this.searchInput;
					this.$api.post(url).then(data => {
						console.log(data);
						_this.articleList=data.data.data.content;
					}).catch(error => {
						console.log(error)
					})
				}
			},
			ViewImage(e) {
				uni.previewImage({
					//imgList:
					current: e.currentTarget.dataset.url
				});
			},
			navToDetails(item){
				console.log(item);
				let data = item;
				uni.navigateTo({
					url: '/pages/tabbar-2-detail/passage-detail?data='+JSON.stringify(data)
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
			}
		}
	}
</script>

<style>
	.content {
		text-align: center;
		margin-top: 150upx;
	},
	.passage-content {
		text-align: center;
		height: 110upx;
		margin-top: 0;
	},
	.text-content{
		text-align: center;
		padding-left: 3upx;
		padding-right: 3upx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-line-clamp:3;
		-webkit-box-orient:vertical;
	}
	
</style>
