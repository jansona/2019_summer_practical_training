let mode = 'dev'
let baseUrl = ''
if (mode === 'dev') {
  baseUrl = 'http://localhost:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else {
}

const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const lostBabyFindUrl = baseUrl + "/lost-baby/find"
const matchBabyInsertUrl = baseUrl + "/MatchBaby/insert"
const matchBabyFindUrl = baseUrl + "/MatchBaby/find"
const uploadPictureUrl = baseUrl + "/file/upload"
const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/user/insert"

const URLS = {
  lostBabyInsertUrl,
  uploadPictureUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl,
  lostBabyFindUrl,
}

module.exports = URLS
