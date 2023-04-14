<?php


class InfoService
{
    public function initSearchDto($dto){
        // 参数校验
        if (!isset($dto['pageNum'])||is_null($dto['pageNum']))$dto['pageNum']=1;
//        /**
//         * <option value="1">全部</option>
//         * <option value="2">当月</option>
//         * <option value="3">上月</option>
//         * <option value="4">近三月</option>
//         * <option value="5">近半年</option>
//         * <option value="6">今年</option>
//         * <option value="7">自定义</option>
//         */
        $dateFlag = isset($dto['checkedDate'])?$dto['checkedDate']:'';
        if (is_null($dateFlag)||$dateFlag==''||$dateFlag=="1"){
            $dto['startDate']=null;
            $dto['endDate']=null;
        }else if ($dateFlag=="7"){
            $startDate = $dto['startDate'];
            $endDate = $dto['endDate'];
            if (strcmp($startDate,$endDate)>0) {
                $dto['endDate']=$startDate;
                $dto['startDate']=$endDate;
            }
        }else {
            $now = new DateTime('now', new DateTimeZone('Asia/Shanghai'));
            $dto['endDate']=$now->format('Y-m-d');

            $nowStartMonth = date_create_from_format('Y-m-d',date_format($now,"Y-m").'-01');
            if ($dateFlag=="2")$dto['startDate']=$nowStartMonth->format('Y-m-d');
            else if ($dateFlag=="3")$dto['startDate']=$nowStartMonth->sub(DateInterval::createFromDateString('1 month'))->format('Y-m-d');
            else if ($dateFlag=="4")$dto['startDate']=$nowStartMonth->sub(DateInterval::createFromDateString('2 month'))->format('Y-m-d');
            else if ($dateFlag=="5")$dto['startDate']=$nowStartMonth->sub(DateInterval::createFromDateString('5 month'))->format('Y-m-d');
            else if ($dateFlag=="6")$dto['startDate']=date_format($now,'Y-').'01-01';
        }
        if (!isset($dto['content'])||is_null($dto['content'])||$dto['content']==''){
            $dto['content']=null;
        }else {
//            if (!preg_match('/[\\w\\u4e00-\\u9fa5]{0,16}/',$dto['content'])){
//                echo "搜索文本仅支持汉字数字和英文字母";
//            }else{
//                $dto['content']=$dto['content'];
//            }
        }
//        echo $dto['content'];
//       // print_r($dto);
//        echo json_encode($dto);
        return $dto;
    }
}