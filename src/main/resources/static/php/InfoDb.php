<?php

//include ('DataBasic.php');

class InfoDb
{
    public function selectTagByInfoId(DataBasic $db,$infoId){
        $sql = "select t.id ,t.tag from accident_tag atg left join tag t on t.id = atg.tag_id where atg.info_id = ".$infoId." order by t.id desc";
        $res = $db->execute($sql);
        if ($res==-1){
            return array();
        }else{
            return $res;
        }
    }

    public function selectInfoSourceVOByInfoId(DataBasic $db,$infoId){
        $infoSourcesRes = $db->execute("select i.url,i.url_title urlTitle, c.channel, i.source_date sourceDate from info_source i left join channel c on i.channel_id = c.id where i.info_id = '".$infoId."' order by i.source_date");

        if ($infoSourcesRes==-1){
            return array();
        }else{
            return $infoSourcesRes;
        }
    }

    public function selectInfoSourceByInfoId(DataBasic $db,$infoId){

        $infoSourcesRes = $db->execute("select i.id, i.url,i.url_title urlTitle, i.channel_id channelId, i.source_date sourceDate from info_source i where i.info_id = '".$infoId."' order by i.source_date");
        if ($infoSourcesRes==-1){
            return array();
        }else{
            return $infoSourcesRes;
        }
    }

    public function typeList($db){
        $res = $db->execute("select * from accident_type");
        if ($res==-1){
            return array();
        }else{
            return $res;
        }
    }

    public function channelList($db){
        $res = $db->execute("select * from channel");
        if ($res == -1){
            return array();
        }else{
            return $res;
        }
    }

    public function saveInfo($db,$info){
        $db = new DataBasic();
        $id=$db->save('accident_info',$info);
    }


    function camelize($uncamelized_words,$separator='_')
    {
        $uncamelized_words = $separator. str_replace($separator, " ", strtolower($uncamelized_words));
        return ltrim(str_replace(" ", "", ucwords($uncamelized_words)), $separator );
    }


    function uncamelize($camelCaps,$separator='_')
    {
        return strtolower(preg_replace('/([a-z])([A-Z])/', "$1" . $separator . "$2", $camelCaps));}

    public function saveTag(DataBasic $db, array $tagNames, $infoId)
    {
        $tags = array();
        foreach ($tagNames as $tagName){
            $tagRes = $db->execute("select id,tag,count from tag where tag = '$tagName'");
            if ($tagRes!=-1 && count($tagRes)>0){
                $db->save('accident_tag',array('tag_id'=>$tagRes[0]['id'],'info_id'=>$infoId));
                $tagRes[0]['count']+=1;
                $db->update('tag',$tagRes[0],array('id'=>$tagRes[0]['id']));
                array_push($tags,$tagRes[0]);
            }else{
                $tagId = $db->save('tag',array('tag'=>$tagName));
                $db->save('accident_tag',array('tag_id'=>$tagId,'info_id'=>$infoId));
                array_push($tags,array('id'=>$tagId,'tag'=>$tagName,'count'=>0));
            }
        }
        return $tags;
    }

    public function saveSourceInfo(DataBasic $db, array $sourceInfos, $infoId)
    {
        foreach ($sourceInfos as $sourceInfo){
            $sourceInfo['info_id'] = $infoId;
            $db->save('info_source',$sourceInfo);
        }
    }

    public function querySort(DataBasic $db,$year){
        $sql = 'SELECT id,date, title, injury_num injuryNum,death_num deathNum,missing_num missingNum, (death_num+missing_num) ttl 
            FROM accident_info where YEAR(date) = \''.$year.'\' ORDER BY (death_num+missing_num)  DESC,date desc LIMIT 0,10';
        $res = $db->execute($sql);
        if ($res == -1){
            return array();
        }else{
            return $res;
        }
    }
}