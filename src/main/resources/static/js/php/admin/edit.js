let param = parsePram()
let infoId = param.id
let editInfoApp = new Vue({
  el: "#editInfoId",
  data: {
    typeList: [{id: 1, name: 't1'}, {id: 2, name: 't2'}, {id: 3, name: 't3'}],
    channelList: [{id: 1, channel: 'c1'}, {id: 2, channel: 'c2'}, {id: 3, channel: 'c3'}],
    info: {
      "area": null,
      "city": null,
      "date": '2022-11-11',
      "deathNum": 1,
      "injuryNum": 1,
      "introduce": '简述',
      "missingNum": 1,
      "place": '未知地',
      "province": null,
      "reason": '未知',
      "ts": '',
      "tags": [],
      "title": '无标题',
      "type": '2'
    },
    infoSource: [{
      "channelId":1,
      "createTime":'2022-10-11',
      "id":1,
      "infoId":1,
      "sourceDate":'2022-10-11',
      "url":'http://www.ee.com',
      "urlTitle":'暂无标题'
    },
      {
        "channelId":2,
        "createTime":'2022-10-11',
        "id":2,
        "infoId":1,
        "sourceDate":'2022-10-11',
        "url":'http://www.ee.com',
        "urlTitle":'暂无标题'
      }],
    // 保存新添加的source信息
    newInfoSource:{
      "channelId":null,
      "infoId":infoId,
      "sourceDate":null,
      "url":null,
      "urlTitle":null
    },
    // 保存要删除的source在数组中的位置
    delInfoSourceIndex:null
  },
  created: function () {
    axios.get('/php/admin/type.php?type=1').then(res => {
      this.typeList = res.data.data
      // console.log(res.data.data)
    })
    axios.get('/php/admin/type.php?type=0').then(res => {
      this.channelList = res.data.data
      // console.log(res.data.data)
    })
    this.getBaseInfo()
    this.getInfoSource()
  },
  methods: {
    delTag:function(index){
      axios.request({
        url: '/php/admin/type.php?type=2&infoId='+infoId+'&tagId='+this.info.tags[index].id,
        method: 'post'
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 删除成功移除tag列表i位置元素
          this.info.tags.splice(index, 1)
        }
      })
    },
    saveTag:function(){
      let tagEls = $("#tagId span.badge.badge-primary")
      let tagReqParam = []
      if (tagEls != null && tagEls.length > 0) {
        for (let i = 0; i < tagEls.length; i++) {
          tagReqParam.push(tagEls[i].innerText)

        }
      }else {
        console.log("未获取到数据")
        return
      }
      axios.request({
        url: '/php/admin/type.php?type=3&infoId='+infoId,
        method: 'post',
        data: tagReqParam
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        if (dat.code == 0) {
          // 清空输入框
          $("span").remove(".badge.badge-primary");
          // 把保存的数据加到标签中
          if (dat.data!=null&&dat.data.length>0){
            for (let i = 0; i < dat.data.length; i++) {
              this.info.tags.push(dat.data[i])
            }
          }
        }
      })
    },
    getBaseInfo:function(){
      axios.get('/php/admin/info.php?type=1&infoId='+infoId).then(res => {
        this.info = res.data.data
      })
    },
    getInfoSource:function(){
      axios.get('/php/admin/info.php?type=2&infoId='+infoId).then(res => {
        this.infoSource = res.data.data
      })
    },
    // 添加信息来源
    addInfoSource: function () {
      axios.request({
        url: '/php/admin/type.php?type=4&infoId='+infoId,
        method: 'post',
        data: this.newInfoSource
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 隐藏模态框
          $('#addInfoSourceModal').modal('hide')
          // 加入数据来源
          this.infoSource.push(dat.data)

        }
      })
    },
    delInfoSource: function () {
      axios.request({
        url: '/php/admin/type.php?type=5&sourceId='+this.infoSource[this.delInfoSourceIndex].id,
        method: 'post'
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 删除成功移除i位置元素
          this.infoSource.splice(this.delInfoSourceIndex, 1)
          // 隐藏模态框
          $('#delInfoSourceModal').modal('hide')
        }else {
          alert('删除失败！')
        }
      })
    },
    editInfoSource: function (i) {
      let reqData =  this.infoSource[i]
      axios.request({
        url: '/php/admin/type.php?type=6',
        method: 'post',
        data: reqData
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 将i位置的数据更新
          //this.infoSource[i] = dat.data
          alert("更新成功！")
        }
      })
    },
    editInfo: function () {
      let reqData = this.info;
      if (reqData.missingNum==''||reqData.missingNum==null)reqData.missingNum=0
      if (reqData.injuryNum==''||reqData.injuryNum==null)reqData.injuryNum=0
      if (reqData.deathNum==''||reqData.deathNum==null)reqData.deathNum=0
      // console.log(this.info)
      axios.request({
        url: '/php/admin/info.php?type=3',
        method: 'post',
        data: reqData
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 页面跳转至首页
          //  location.href = "/admin/index.html"
          alert('更新成功')
        }
      })
    }
  }
})