<?php
class HttpClient{
    
    public function get($url)
    {
		// 参考https://www.cnblogs.com/benpaodelulu/p/7324551.html
		//$url = 'http://localhost/commonDb.php?type=0&sql=U0VMRUNUIE5BTUUsdXJsIEZST00gd2ViX3NpdGU=';
		$ch = curl_init();//初始化
		curl_setopt($ch, CURLOPT_URL, $url);//设置选项
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		$file_contents = curl_exec($ch);//执行并获取HTML文档内容
		curl_close($ch);//释放curl句柄
		return $file_contents;//打印获得的数据

	}
	
	/**
     * 模拟post进行url请求
     * @param string $url
     * @param string $param
     */
    public function post($url = '', $param = '') {
		// 参考 https://www.cnblogs.com/jiqing9006/p/3949190.html
        if (empty($url) || empty($param)) {
            return false;
        }
        
        $postUrl = $url;
        $curlPost = $param;
        $ch = curl_init();//初始化curl
        curl_setopt($ch, CURLOPT_URL,$postUrl);//抓取指定网页
        curl_setopt($ch, CURLOPT_HEADER, 0);//设置header
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);//要求结果为字符串且输出到屏幕上
        curl_setopt($ch, CURLOPT_POST, 1);//post提交方式
        curl_setopt($ch, CURLOPT_POSTFIELDS, $curlPost);
        $data = curl_exec($ch);//运行curl
        curl_close($ch);
        
        return $data;
    }
    
}