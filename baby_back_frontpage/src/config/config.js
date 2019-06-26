let mode = 'dev'
let baseUrl = ''
if (mode === 'dev') {
  baseUrl = 'http://localhost:8895'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:8895'
} else {
}

const fillFormUrl = baseUrl + "user/addForm"
const URLS = {
  fillFormUrl,
}

module.exports = URLS
