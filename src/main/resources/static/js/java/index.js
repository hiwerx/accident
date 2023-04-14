let accidentApp = new Vue({
  el: "#accidentId",
  data: {
    targetPage:null,// 自定义跳转页
    tagClickBeforePage:null,//保存tag点击前页号
    searchParam:{
      checkedDate:null,
      startDate:null,
      endDate:null,
      tagId:null,
      content:null
    },
    checkedDate:1,
    checkedTagFlag:false,
    checkedTag:'jk',
    bagClass:['badge-primary',
      'badge-secondary',
      'badge-success',
      'badge-danger',
      'badge-warning',
      'badge-info'
    ],
    page:{
      "current":2,
      "hitCount":false,
      "optimizeCountSql":true,
      "orders":[],
      "pageNums":[
        1,
        2,
        3
      ],
      "pages":3,
      "records":[],
      "searchCount":true,
      "size":3,
      "total":9
    },
    accidentList:[{
      "title": null,
      "updateTime": "2022-08-21T23:46:49",
      "date": "2022-08-18",
      "place": "青海大通",
      "province": "青海省",
      "city": "西宁市",
      "area": "大通县",
      "introduce": "中国天气网讯 8月18日，青海西宁大通县青林乡、青山乡瞬间强降雨后引发山洪，造成泥石流，致使河流改道漫溢",
      "injuryNum": 0,
      "deathNum": 26,
      "missingNum": 5,
      "type": "自然灾害",
      "reason": "强降雨后引发山洪、泥石流",
      "sourceUrls": [{
        "url": "https://news.cctv.com/2022/08/21/ARTIxIFmvkxMdydf3WvjN0VG220821.shtml",
        "urlTitle": "青海大通“8·18”山洪灾害已造成26人遇难 仍有5人失联",
        "channel": "央视新闻",
        "sourceDate": null
      },
        {
          "url": "https://news.cctv.com/2022/08/18/ARTI6vQtOsftO4onJfSkRPm6220818.shtml",
          "urlTitle": "青海西宁大通县山洪灾害已造成16人死亡36人失联",
          "channel": "央视新闻",
          "sourceDate": null
        }],
      "tags": [{
        "id": 3,
        "tag": "泥石流",
        "count": null
      },
        {
          "id": 2,
          "tag": "山洪",
          "count": null
        },
        {
          "id": 1,
          "tag": "暴雨",
          "count": null}
      ]
    },
      {
        "title": null,
        "updateTime": "2022-08-22T00:32:32",
        "date": "2022-08-13",
        "place": "彭州龙槽沟",
        "province": "四川省",
        "city": "成都市",
        "area": "彭州市",
        "introduce": "上游强降雨致龙槽沟突发山洪",
        "injuryNum": 0,
        "deathNum": 7,
        "missingNum": 0,
        "type": "自然灾害",
        "reason": "上游强降雨致龙槽沟突发山洪",
        "sourceUrls": [{
          "url": "https://www.163.com/dy/article/HETRA7KG0519QIKK.html",
          "urlTitle": "龙槽沟山洪当天，竟有知名旅行社组织百人戏水！有人被困3小时",
          "channel": "网易新闻",
          "sourceDate": "2022-08-16"
        },
          {
            "url": "https://www.toutiao.com/article/7133071895531880964/?wid=1661098998134",
            "urlTitle": "彭州龙槽沟山洪致7人死亡，从这三个定律看悲剧背后的真相",
            "channel": "今日头条",
            "sourceDate": "2022-08-18"
          }],
        "tags": [{
          "id": 4,
          "tag": "龙槽沟",
          "count": null
        },
          {
            "id": 2,
            "tag": "山洪",
            "count": null
          }]
      }]
  },
  created: function () {
    // this.getAllData()
    //加载cookie中请求参数
    this.loadSearchParam()
    this.mixSearch()
  },
  methods: {
    targetPageSearch:function(){
      if (this.targetPage!=null&&this.targetPage!==''){
        if (isNaN(this.targetPage)){
          alert('页码必须是数字!')
          this.targetPage = null
        }else if (this.targetPage>this.page.pages){
          alert('总共'+this.page.pages+'页，没有第'+this.targetPage+'页，请输入正确的页号！')
          this.targetPage = null
        }else {
          this.pageSearch(this.targetPage)
          this.targetPage = null
        }
      }
    },

    pageSearch:function(pageNum){
      // 如果点击页为当前页面则不搜索
      // if (pageNum==this.page.current)return
      this.searchParam.pageNum = pageNum
      this.mixSearch()
    },

    getAllData: function(){
      axios.get('/accident/info/all').then(res => {
        // this.accidentList = res.data.data
        this.page = res.data.data
        console.log(res.data.data)
      })
    },

    findDataByTagId:function (tagId,tagName) {
      // 展示点击的标签
      this.checkedTagFlag = true
      this.checkedTag = tagName
      // 搜索点击的标签
      this.searchParam.tagId = tagId
      // 修改搜索页前保存当前搜索页
      if(this.tagClickBeforePage==null) this.tagClickBeforePage = this.searchParam.pageNum
      //console.log(this.tagClickBeforePage)
      this.pageSearch(1)
      // axios.get('/accident/info/get/'+tagId).then(res => {
      //     this.accidentList = res.data.data
      //     console.log(res.data.data)
      // })
    },

    mixSearch:function(){
      let sendReqFlag = false;
      if (this.searchParam.checkedDate!='7'){
        this.searchParam.startDate=null
        this.searchParam.endDate=null
        sendReqFlag = true
      }else if (this.searchParam.startDate!=null&&this.searchParam.endDate!=null){
        sendReqFlag = true
      }
      if (sendReqFlag){
        axios.get('/accident/info/mix',{
          params: this.searchParam
        }).then(res => {
          //this.accidentList = res.data.data
          let r = res.data.data
          if (r.total == 0){
            alert('查无结果')
            // 清除搜索框
            this.searchParam.content = null
          }else if (r.total>0&&r.records.length==0){
            alert('总共'+r.pages+'页，没有第'+r.current+'页，请选择其他页')
            // 恢复搜索页的原页号
            this.searchParam.pageNum = this.page.current
          }else {
            this.page = r
          }
          // console.log(res.data.data)
        })
      }
    },
    clearTag:function(){
      // 取消标签展示
      this.checkedTagFlag=false
      // 清除标签搜索条件
      this.searchParam.tagId = null
      // 取出标签点击前的页号
      this.pageSearch(this.tagClickBeforePage)
      // 清除页号
      this.tagClickBeforePage = null
    },
    // 从cookie中获取请求参数
    loadSearchParam:function(){
      let data = getCookie('pubIndexReq')
      console.log('data:'+data)
      if (data!='')
        this.searchParam = JSON.parse(data)
    }
  }
})
//离开页面，请求数据存入cookie
function cacheReqData() {
  let reqData = accidentApp.searchParam
  let reqStrData = JSON.stringify(reqData)
  setCookieMin("pubIndexReq",reqStrData,30)
}

