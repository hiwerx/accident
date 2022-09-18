/**
 * 解析url地址的参数
 * @returns {string|{}} 如果不是k=v则直接返回参数，是则返回k-v对象
 */
function parsePram(){
    let obj = {};
    let href = document.location.href
    // if (!href.includes('?')) return ''
    let params = ''
    let startIndex = href.indexOf('?')+1
    if (startIndex===0) return params //兼容低版本安卓
    let endIndex = href.indexOf('#');
    if (endIndex!=-1){
        params = href.substring(startIndex,endIndex)
    }else {
        params = href.substring(startIndex)
    }

    let deParams = decodeURI(params)                         //解码
    let parArr = deParams.split('&')
    for (let i = 0; i < parArr.length; i++) {
        let par = parArr[i];
        if (par.indexOf('=')>0){
            let pr = par.split('=')
            if (pr.length === 2 && ''!==pr[0] && ''!== pr[1]){
                obj[pr[0]]=pr[1]
            }
        }
    }
    if (deParams.indexOf('=')!=-1){
        return obj;
    }else {
        return deParams;
    }

}

/**
 * <title>标签赋值
 * @param tile
 */
function setTitle(tile){
    document.getElementsByTagName('title')[0].innerText=tile
}

/**
 * 获取第一个#号后面的数据
 * @returns {string}
 */
function getLabel(){
    let href = document.location.href;
    let index = href.indexOf('#')
    if (index!=-1){
       return  href.substring(index+1)
    }
    return ''
}

// 参考 https://cloud.tencent.com/developer/article/1776252
//https://blog.csdn.net/beekim/article/details/120099638
/*****设置cookie*****/
function setCookieDay(name,value,iDay){
    let oDate = new Date();
    oDate.setDate(oDate.getDate()+iDay);
    document.cookie = name+'='+value+';expires='+oDate.toUTCString();
}
/*****设置cookie*****/
function setCookieMin(name,value,minute){
    let oDate = new Date();
    oDate.setMinutes(oDate.getMinutes()+minute)
    document.cookie = name+'='+value+';expires='+oDate.toUTCString();
}
/*****获取cookie*****/
function getCookie(name){
    let arr = document.cookie.split("; ");
    for(let i=0;i<arr.length;i++){
        let arr2 = arr[i].split("=");
        if(arr2[0]==name){
            return arr2[1]
        }
    }
    return ""
}
/*****移除cookie*****/
function removeCookie(name){
    setCookieDay(name,1,-1);
};



