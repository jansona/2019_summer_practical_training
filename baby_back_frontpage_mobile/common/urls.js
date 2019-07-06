let mode = 'prod'
let baseUrl = ''
let baseUrl2 = ''
if (mode === 'dev') {
  baseUrl = 'http://127.0.0.1:18080'
  baseUrl2 = 'http://127.0.0.1:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else if (mode == 'prod'){
  baseUrl = 'http://42.159.4.66:9999/api/main'
  baseUrl2 = 'http://42.159.4.66:9999'
} else if (mode == 'test') {
  baseUrl = 'http://127.0.0.1:9999/api/main'
  baseUrl2 = 'http://127.0.0.1:9999'
} 

let imgUrl = 'http://42.159.4.66:2333'

// 填写信息部分
const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const lostBabyFindUrl = baseUrl + "/lost-baby/find"
const matchBabyInsertUrl = baseUrl + "/match-baby/insert"
const matchBabyFindUrl = baseUrl + "/match-baby/find"
const uploadPictureUrl = baseUrl + "/file/upload"

//查找lost,find
const lostBabyFindByUserUrl = baseUrl + "/lost-baby/find-by-user"
const matchBabyFindByUserUrl = baseUrl + "/match-baby/find-by-user"

// 登录注册部分
const loginUrl = baseUrl2 + "/api/auth/oauth/token"
const userInfoUrl = baseUrl2 + '/api/admin/user/infoTel'
const logoutUrl = baseUrl2+ '/api/auth/token/logout'
// const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/register"
const sendSMSUrl = baseUrl + "/sms-verify"

// 论坛部分
const articleFindUrl = baseUrl + '/article/find'
const articleDeleteUrl = baseUrl + '/article/delete'
const articleFindByUserUrl = baseUrl + '/article/find-by-user'
const articleFindByUserNameUrl = baseUrl + '/article/find-by-username'
const articleInsertUrl = baseUrl + '/article/insert'
//评论部份
const commentFindUrl = baseUrl + '/comment/find'
const commentDeleteUrl = baseUrl + '/comment/delete'
const commentFindByArticleUrl = baseUrl + '/comment/find-by-article'
const commentFindByUserUrl = baseUrl + '/comment/find-by-user'
const commentInsertUrl = baseUrl + '/comment/insert'

//查询用户信息
const userFindUrl = baseUrl + '/user/find'
const userFindByIdUrl = baseUrl + '/user/find-by-id'

const URLS = {
	userInfoUrl,
	logoutUrl,
	imgUrl,
  baseUrl,
  lostBabyInsertUrl,
  uploadPictureUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl,
  lostBabyFindUrl,
  matchBabyFindUrl,
  sendSMSUrl,
  articleFindUrl,
  articleDeleteUrl,
  articleFindByUserUrl,
	articleFindByUserNameUrl,
  articleInsertUrl,
	commentFindUrl,
	commentDeleteUrl,
	commentFindByArticleUrl,
	commentFindByUserUrl,
	commentInsertUrl,
  userFindUrl,
	userFindByIdUrl,
	lostBabyFindByUserUrl,
	matchBabyFindByUserUrl
}

module.exports = URLS