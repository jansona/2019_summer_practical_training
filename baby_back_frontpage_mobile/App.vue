<script>
	export default {
		computed: {
			connected() {
				return this.$store.state.connected
			}
		},
		watch: {
			connected(cur,old){
				console.log("连接情况：",cur)
				if(cur){
					this.connect()
				}
			}
		},
		onLaunch: function() {
			console.log('App Launch');
			plus.push.addEventListener("click",(msg) => {
				let data = msg.payload
				uni.navigateTo({
					url: '/pages/tabbar-1-detail/baby-detail?data=' + data
				})
			})
		},
		onShow: function() {
			console.log('App Show');

		},
		onHide: function() {
			console.log('App Hide');
		},
		methods: {
			connect() {
				console.log("connect", this.$store.state.userId)
				let baseUrl = this.URLS.baseUrl
				let _this = this
				baseUrl = baseUrl.split(':')[1].substring(2)
				uni.connectSocket({
					url: "ws://" + baseUrl + ":18080/websocket"
				});
				uni.onSocketOpen(function(res) {
					console.log('WebSocket连接已打开！');
					uni.sendSocketMessage({
						data: _this.$store.state.userId + ''
					})
				});
				uni.onSocketError(function(res) {
					console.log('WebSocket连接错误：', res);
				});
				uni.onSocketMessage(function(res) {
					console.log('收到服务器内容：' + res.data);
					let listString = res.data.split(":");
					if (listString[0] == "LOST") {
						let idList = listString[1].split(",");
						console.log(idList)
						for (var i = 0 ; i < idList.length ; i ++) {
							console.log("匹配到了id：",idList[i])
							_this.$api.post(_this.URLS.lostBabyFindUrl+"?id="+idList[i]).then(data => {
								console.log(data)
								plus.push.createMessage("匹配到了丢失儿童："+data.data.data.content[0].name,JSON.stringify(data.data.data.content[0]))
							})
						}
					} else if (listString[0] == "MATCH") {
						let idList = listString[1].split(",");
						for (var i = 0 ; i < idList.length ; i ++) {
							_this.$api.post(_this.URLS.matchBabyFindUrl+"?id="+idList[i]).then(data => {
								plus.push.createMessage("匹配到了寻家儿童："+data.data.data.content[0].name,JSON.stringify(data.data.data.content[0]))
							})
						}
					}
				});

				uni.onSocketClose(function(res) {
					console.log('WebSocket 已关闭！');
				});

			}
		}
	};
</script>

<style>
	@import "colorui/main.css";
	@import "colorui/icon.css";
	@import "colorui/animation.css";
	@import 'common/uni.css';

	/*每个页面公共css */
	uni-page-body {
		height: 100%;
	}
</style>
