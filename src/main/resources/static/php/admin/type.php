<?php
include ('../DataBasic.php');
include ('../JsonResult.php');
include ('../InfoDb.php');

$jsonResult = new JsonResult();
$db = new DataBasic();
$infoDb = new InfoDb();
$type = isset($_GET['type'])?$_GET['type']:'';
if ($type==''){
    echo $jsonResult->fail1('业务类型不支持');
}elseif ($type == 0){
    echo  $jsonResult->succ1($infoDb->channelList($db));
}elseif ($type == 1){
    echo $jsonResult->succ1($infoDb->typeList($db));
}elseif ($type==2){
    $infoId = isset($_GET['infoId'])?$_GET['infoId']:'';
    $tagId = isset($_GET['tagId'])?$_GET['tagId']:'';
    if ($infoId==''||$tagId==''){
        echo $jsonResult->fail1('参数有误');
        exit();
    }
    $res = $db->execute("delete from accident_tag where info_id = '$infoId' and tag_id = '$tagId'");
    if ($res !=-1){
        echo $jsonResult->succ0();
    }else {
        echo $jsonResult->fail0();
    }
}elseif ($type == 3){
    $infoId = isset($_GET['infoId'])?$_GET['infoId']:'';
    if ($infoId==''){
        echo $jsonResult->fail1('参数有误');
        exit();
    }
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    echo $jsonResult->succ1($infoDb->saveTag($db,$reqData,$infoId));
}elseif ($type == 4){
    // 添加sourceInfo
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    $infoSource = array();
    foreach ($reqData as $k=>$v){
        $infoSource[$infoDb->uncamelize($k)] = $v;
    }
    $res = $db->save('info_source',$infoSource);
    if ($res!=-1){
        $reqData['id']=$res;
        echo $jsonResult->succ1($reqData);
    }else{
        echo $jsonResult->fail0();
    }
}elseif ($type==5){
    // 删除sourceInfo
    $sourceId = isset($_GET['sourceId'])?$_GET['sourceId']:'';
    if ($sourceId==''){
        echo $jsonResult->fail1('参数有误');
        exit();
    }
    $res = $db->execute("delete from info_source where id = '$sourceId'");
    if ($res!=-1){
        echo $jsonResult->succ0();
    }else{
        echo $jsonResult->fail0();
    }
}elseif ($type == 6){
    // 更新sourceInfo
    $json = file_get_contents('php://input');
    // 将其转换为 PHP 变量
    $reqData = json_decode($json, true);
    $infoSource = array();
    foreach ($reqData as $k=>$v){
        $infoSource[$infoDb->uncamelize($k)] = $v;
    }
    $res = $db->update('info_source',$infoSource,array('id'=>$infoSource['id']));
    if ($res!=-1){
        echo $jsonResult->succ0();
    }else{
        echo $jsonResult->fail0();
    }
}

