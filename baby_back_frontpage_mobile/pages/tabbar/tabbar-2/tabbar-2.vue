<template>
	<view>
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索帖子" confirm-type="search"></input>
			</view>
			<view class="margin-tb-sm text-center">
				<button class="cu-btn round" style="margin-right: 10upx;background-color: rgba(200,140,100,0.2);">搜索</button>
			</view>
		</view>
		<view class="cu-card article">
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
		</view>
		<view class="cu-card article">
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
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				InputBottom:0,
				author:'gugu',
				date:'2019-6-27',
				tittle:'用心耕耘 深情护送宝贝回家 —志愿者小梅寻亲成功案例的启示',
				content:"朋友，当大家合家团圆、安享幸福的生活的时候，你有没有想到，这个世界上还有这样的一个群体。他们像浮萍一样漂浮地生活在这个世界上，脑海中残留的是童年心酸的灰色回忆，过的是度日如年的思亲生活。他们有自己的名字，却并不知道自己真正姓甚名谁"
			}
		},
		onLoad() {

		},
		methods: {
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			searchClick(e){
				
			}
		}
	}
</script>

<style>
	.content {
		text-align: center;
		height: 400upx;
		margin-top: 200upx;
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
