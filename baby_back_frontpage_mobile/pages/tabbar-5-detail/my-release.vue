<template>
	<view>
		<!-- 选项卡 -->
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				亲人寻子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				游子寻家
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		mounted:function () {
			this.refreshData();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				pageNo: 0,
				lostList:[],
				lostPicUrls:{},
				findList:[],
				findPicUrls:{}
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
			resetData(){
				this.pageNo=0
				this.lostPicUrls=[]
				this.lostList=[]
				this.findPicUrls=[]
				this.findList=[]
				this.refreshData();
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			refreshData() {
				
				if(this.totalPageNum<=this.pageNo){
					this.isBottom=true
					console.log('已经到底了')
					let _this = this
					setTimeout(function(){ _this.isBottom=false; }, 1000);
					return;
					
				}
				if(this.lostShow){
					let url =this.URLS.lostBabyFindUrl +'?size=7&page='+this.pageNo
					this.pageNo +=1
					this.$api.post(url)
						.then(data => {
							this.totalPageNum = data.data.data.totalPages
							
							let appendList = data.data.data.content
							for (let i = 0; i < appendList.length; i++) {
								let id = appendList[i].id;
								this.lostPicUrls.push(this.yieldPicUrl(id));
							}
							this.lostList = this.lostList.concat(appendList);
						}).catch(error => {
							console.log(error)
						})
				}else{
					let url =this.URLS.matchBabyFindUrl +'?size=7&page='+this.pageNo
					this.pageNo +=1
					this.$api.post(url)
						.then(data => {
							this.totalPageNum = data.data.data.totalPages
							
							let appendList = data.data.data.content
							for (let i = 0; i < appendList.length; i++) {
								let id = appendList[i].id;
								this.findPicUrls.push(this.yieldPicUrl(id));
							}
							this.findList = this.findList.concat(appendList);
						}).catch(error => {
							console.log(error)
						})
				}
			
			},
		},
	}
</script>

<style>
</style>
