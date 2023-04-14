let accidentApp = new Vue({
  el: "#accidentId",
  data: {
    targetPage:null, //自定义跳转页
    delInfoId:null,//被选中的待删除的id
    searchParam:{
      checkedDate:null,
      startDate:null,
      endDate:null,
      content:null
    },
    accidentList:[{
      "title": '大通泥石流事件',
      "id": "1",
    },
      {
        "title": '龙槽沟山洪事件',
        "id": "2",
      }
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
      "records":[
        {
          "id":4,
          "title":"浙江龙游衢江河道意外溺水事件",
        },
        {
          "id":5,
          "title":"3·21东航客机事故",
        },
        {
          "id":6,
          "title":"4·29长沙楼房坍塌事故"
        }
      ],
      "searchCount":true,
      "size":3,
      "total":9
    }
  },
  created: function () {
    this.loadSearchParam()
    // this.pageSearch(1)
    this.mixSearch()
  },
  methods: {
    targetPageSearch:function(){
      if (this.targetPage!=null&&this.targetPage!==''){
        if (isNaN(this.targetPage)){
          alert('页码必须是数字!')
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
      // if (pageNum==this.page.current)return 影响文本搜索
      this.searchParam.pageNum = pageNum
      this.mixSearch()
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
        axios.post('/php/admin/info.php?type=4',
          this.searchParam
        ).then(res => {
          // this.accidentList = res.data.data
          console.log(res.data.data)
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
          this.page = r

          // 起始页数重置为1
          // this.searchParam.pageNum = 1
        })
      }
    },
    delInfo:function(){
      console.log(this.delInfoId)
      if (this.delInfoId==null) return
      axios.post('/php/admin/info.php?type=5&delInfoId='+this.delInfoId)
          .then(response => {
            console.log(response);
            if (response.data.code==0){
              this.delInfoId=null
              // 隐藏模态框
              $('#delInfoModal').modal('hide')
              this.mixSearch()
              alert('删除成功')
            }
          })
    },
    // 从cookie中获取请求参数
    loadSearchParam:function(){
      let data = getCookie('adminIndexReq')
      // console.log('data:'+data)
      if (data!='')
        this.searchParam = JSON.parse(data)
    }
  }
})
//离开页面，请求数据存入cookie
function cacheReqData() {
  let reqData = accidentApp.searchParam
  let reqStrData = JSON.stringify(reqData)
  setCookieMin("adminIndexReq",reqStrData,30)
}