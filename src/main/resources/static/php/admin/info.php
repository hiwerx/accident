<?php
include ('../DataBasic.php');
include ('../JsonResult.php');
include ('../InfoService.php');
include ('../InfoDb.php');

$db = new DataBasic();
$jsonResult = new JsonResult();
$infoDb = new InfoDb();

//取参数值
$type = isset($_GET['type'])?$_GET['type']:-1;
$querySql=null;
if($type==0) {
    // 添加一个信息
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    $infoMap = array();
    $tagNames = array();
    $sourceInfos = array();
    foreach ($reqData as $k=>$v){
        if ($k=='sourceUrls'){
            foreach ($v as $source){
                $sourceMap = array();
                foreach ($source as $sourceK=>$sourceV){
                    $sourceMap[$infoDb->uncamelize($sourceK)]=$sourceV;

                }
                array_push($sourceInfos,$sourceMap);
            }
        }elseif ($k=='tags'){
            $tagNames = $v;
        }else{
            $infoMap[$infoDb->uncamelize($k)]= $v;
        }
    }

    $infoId = $db->save("accident_info",$infoMap);
    if ($infoId == -1){
        echo $jsonResult->fail1('保存失败');
        exit();
    }

    // 保存标签关联
    $infoDb->saveTag($db,$tagNames,$infoId);
    // 保存来源关联
    $infoDb->saveSourceInfo($db,$sourceInfos,$infoId);
    echo $jsonResult->succ0();

}elseif($type == 1){
    // 加载一个信息
    $infoId = isset($_GET['infoId'])?$_GET['infoId']:'';
    if ($infoId==''){
        echo $jsonResult->fail1('参数有误');
        exit();
    }
    $infoRes = $db->execute("select * from accident_info where id = '$infoId'");
    if ($infoRes == -1||count($infoRes)==0){
        echo $jsonResult->fail1('为查询到事故信息');
        exit();
    }
    $infoOne = $infoRes[0];
    $info=array();
    foreach ($infoOne as $k=>$v){
        $info[$infoDb->camelize($k)]=$v;
    }
    $infoId= $info['id'];
    $tags = $infoDb->selectTagByInfoId($db,$infoId);
    $info['tags'] = $tags;
    if (count($tags)>0){
        $tagIds = array();
        foreach ($tags as $tag){
            array_push($tagIds,$tag['id']);
        }
        $info['ts'] = join(",",$tagIds);
    }
    echo $jsonResult->succ1($info);
}elseif ($type == 2){
    // 加载infoSource
    $infoId = isset($_GET['infoId'])?$_GET['infoId']:'';
    if ($infoId==''){
        echo $jsonResult->fail1('参数有误');
        exit();
    }
    echo $jsonResult->succ1($infoDb->selectInfoSourceByInfoId($db,$infoId));

}elseif ($type == 3){
    // 添加一个信息
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    if (!isset($reqData['id'])||is_null($reqData['id'])||$reqData['id']==''){
        echo $jsonResult->fail0();
        exit();
    }
    $columns = array('id','date','place','province','city','area','introduce','injury_num','death_num','missing_num','type','reason','status','title');
    $updateDate = array();
    foreach ($columns as $column){
        $reqField = $infoDb->camelize($column);
        if (isset($reqData[$reqField])){
            $updateDate[$column] = $reqData[$reqField];
        }
    }
    $res = $db->update('accident_info',$updateDate,array('id'=>$updateDate['id']));
    if ($res==-1){
        echo $jsonResult->fail0();
    }else{
        echo $jsonResult->succ0();
    }
}else if ($type==4){
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

    $queryColumns = " ai.id, ai.title ";

    $infoSql = "select $queryColumns from accident_info ai ".$whereSql;
    $infoList = $db->execute($infoSql.$limitSql);
    $totalRes = $db->execute("select count(ai.id) ct from accident_info ai ".$whereSql);
    $total = ($totalRes!=-1&&count($totalRes)>0)?$totalRes[0]['ct']:0;

    echo $jsonResult->succ1(array('records'=>$infoList,'total'=>$total));

}elseif($type==5){
    $infoId = isset($_GET['delInfoId'])?$_GET['delInfoId']:'';
    if ($infoId===''){
        echo $jsonResult->fail0();
        exit();
    }
    // 删除info
    $res = $db->execute("delete from accident_info where id = '$infoId'");
    // 删除tag关联
    $db->execute("delete from accident_tag where info_id = '$infoId'");
    // 删除来源
    $db->execute("delete from info_source where info_id = '$infoId'");
    if ($res!=-1){
        echo $jsonResult->succ0();
    }else{
        echo $jsonResult->fail0();
    }
}else{
    echo $jsonResult->fail1("业务不支持");
}