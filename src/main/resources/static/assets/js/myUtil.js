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

function setTitle(tile){
    document.getElementsByTagName('title')[0].innerText=tile
}

function getLabel(){
    let href = document.location.href;
    let index = href.indexOf('#')
    if (index!=-1){
       return  href.substring(index+1)
    }
    return ''
}



