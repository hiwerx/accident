let addInfoApp = new Vue({
  el: "#addInfoId",
  data: {
    typeList: [{id: 1, name: 't1'}, {id: 2, name: 't2'}, {id: 3, name: 't3'}],
    channelList: [{id: 1, channel: 'c1'}, {id: 2, channel: 'c2'}, {id: 3, channel: 'c3'}],
    addInfoReq: {
      "area": null,
      "city": null,
      "date": null,
      "deathNum": null,
      "injuryNum": null,
      "introduce": null,
      "missingNum": null,
      "place": null,
      "province": null,
      "reason": null,
      "sourceUrls": [],
      "tags": [],
      "title": null,
      "type": null
    },
    addInfoResp: {}
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
  },
  methods: {
    // 添加信息来源
    /**
     * 参考
     * https://blog.csdn.net/qq_24806045/article/details/85279034
     * https://www.jianshu.com/p/8f6495db917e
     */
    addInfoSourceTag: function () {
      let obj = {url: null, urlTitle: null, channelId: null, sourceDate: null}
      this.addInfoReq.sourceUrls.push(obj)
    },
    delInfoSourceTag: function (i) {
      this.addInfoReq.sourceUrls.splice(i, 1)
    },
    addInfoData: function () {
      let tagEls = $("#tagId span.badge.badge-primary")
      if (tagEls != null && tagEls.length > 0) {
        // 清空标签
        this.addInfoReq.tags = []
        for (let i = 0; i < tagEls.length; i++) {
          this.addInfoReq.tags.push(tagEls[i].innerText)
        }
      }
      // console.log(this.addInfoReq)
      axios.request({
        url: '/php/admin/info.php?type=0',
        method: 'post',
        data: this.addInfoReq
      }).then(res => {
        let dat = res.data
        console.log(dat.data)
        // 给响应数据赋值
        if (dat.code == 0) {
          // 页面跳转至首页
          location.href = "/admin/index.html"
        }
      })
    }
  }
})