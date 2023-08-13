<?php
//导入必要的包
include ('DataBasic.php');
include ('JsonResult.php');
include ('FtpClient.php');
include ('InfoService.php');
include ('InfoDb.php');

$db = new DataBasic();
$jsonResult = new JsonResult();
//取参数值
$type = isset($_GET['type'])?$_GET['type']:-1;
$querySql=null;
if($type==0) {
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    $infoService = new InfoService();
    $dto = $infoService->initSearchDto($reqData);
    $pageSize = isset($dto['pageSize'])?$dto['pageSize']:5;
//    echo $dto['pageNum'];
    $skipNum = ($dto['pageNum']-1)*$pageSize;
    $limitSql = " limit $skipNum , $pageSize ";
    $whereSql = ' where ai.status is null ';
    if (!is_null($dto['content'])){
        $whereSql = $whereSql." and ai.introduce like '%".$dto['content']."%' ";
    }
    if (!is_null($dto['startDate'])&&!is_null($dto['endDate'])){
        $whereSql = $whereSql." and ai.date between '".$dto['startDate']."' and '".$dto['endDate']."'";
    }

    if (isset($dto['tagId'])){
        $queryInfoIdByTagIdSql = "select atg1.info_id infoId from accident_tag atg1 where atg1.tag_id = ".$dto['tagId'];
        $infoIds=$db->execute($queryInfoIdByTagIdSql);
        if ($infoIds!=-1&&count($infoIds)>0){
            $infoIdArr = array();
            foreach ($infoIds as $infoId){
                array_push($infoIdArr,$infoId['infoId']);
            }
            $whereSql = $whereSql." and ai.id in ( ".join(',',$infoIdArr)." ) ";
        }
    }
    $whereSql = $whereSql." order by ai.date desc ";

    $queryColumns = " ai.id, ai.title,ai.update_time updateTime,ai.date,ai.place,ai.introduce,ai.injury_num injuryNum,ai.death_num deathNum,ai.missing_num missingNum,at.name type,ai.reason ";

    $infoSql = "select $queryColumns from accident_info ai left join accident_type at on ai.type = at.id ".$whereSql;
    $infoList = $db->execute($infoSql.$limitSql);
    $totalRes = $db->execute("select count(ai.id) ct from accident_info ai ".$whereSql);
    $total = ($totalRes!=-1&&count($totalRes)>0)?$totalRes[0]['ct']:0;
    if ($infoList!=-1&&count($infoList)>0) {
        for ($index = 0; $index < count($infoList); $index++) {
//            $infoList[$index]['injuryNum']=$infoList[$index]['injury_num'];
//            $infoList[$index]['deathNum']=$infoList[$index]['death_num'];
//            $infoList[$index]['missingNum']=$infoList[$index]['missing_num'];

            $pieces = explode("\n", $infoList[$index]['introduce']);
            $infoList[$index]['introduces']=$pieces;
            $infoId = $infoList[$index]['id'];
//            $tagIds = $db->execute("select tag_id from accident_tag where info_id = ".$infoId);
//            $tagIdArr = array();
//            foreach ($tagIds as $tagId){
//                array_push($tagIdArr,$tagId['tag_id']);
//            }
            $infoDb = new InfoDb();
            $infoList[$index]['tags'] = $infoDb->selectTagByInfoId($db,$infoId);
            $infoList[$index]['sourceUrls'] = $infoDb->selectInfoSourceVOByInfoId($db,$infoId);
        }
    }
    echo $jsonResult->succ1(array('records'=>$infoList,'total'=>$total));

}elseif ($type==1){
    if (isset($_GET['year'])){
        $year = $_GET['year'];
        $infoDb = new InfoDb();
        echo $jsonResult->succ2($infoDb->querySort($db,$year),'查询成功');
    }else{
        echo $jsonResult->fail1('必传参数为空');
    }

}else{
    echo $jsonResult->fail1('业务不支持');
}

