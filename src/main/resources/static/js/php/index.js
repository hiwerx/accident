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
    }
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
      let pageSize = 5;
      this.searchParam.pageSize = pageSize
      if (this.searchParam.pageNum==null||this.searchParam.pageNum==''){
        this.searchParam.pageNum=1
      }
      let sendReqFlag = false;
      if (this.searchParam.checkedDate!='7'){
        this.searchParam.startDate=null
        this.searchParam.endDate=null
        sendReqFlag = true
      }else if (this.searchParam.startDate!=null&&this.searchParam.endDate!=null){
        sendReqFlag = true
      }
      if (sendReqFlag){
        console.log(this.searchParam)
        axios.post('/php/info.php?type=0',this.searchParam).then(res => {
          if (res.data.code!=0){
            alert('查询失败')
            return
          }
          let resData = res.data.data
          let r = pageInfo(this.searchParam.pageNum,pageSize,resData.total)
          //this.accidentList = res.data.data
          r.lastPage = r.isLastPage
          r.firstPage = r.isFirstPage
          r.total = resData.total
          r.records = resData.records
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

