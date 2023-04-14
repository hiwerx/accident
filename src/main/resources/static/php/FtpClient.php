<?php
class FtpClient{
	
	

	public function ftp(){
		// set up basic connection
		$ftp = ftp_connect("192.168.190.104",2121);
		// login with username and password
		$login_result = ftp_login($ftp,'user','user');
		// 检查连接是否正常
		if ((!$ftp) || (!$login_result)) {
			return null;
		}else{
			return $ftp;
		}
	}
	
	/**
	*$remoteFilePath 远程文件路径。
	*$fp 			打开的文件
	*/
	public function fput($remoteFilePath,$fp){
		$res=-1;
		$ftp = $this->ftp();
		if($ftp==null)return $res;
		// try to upload $file
		
		if (ftp_fput($ftp, $remoteFilePath, $fp, FTP_BINARY)) {
			$res=0;
			//echo $jsonResult->succ1(array('fileName'=>$fileName));
		} 
		// close the connection and the file handler
		ftp_close($ftp);
		fclose($fp);
		return $res;
	}
	
	public function del($remoteFilePath){
		$res=-1;
		$ftp = $this->ftp();
		if($ftp==null)return $res;
		if(ftp_delete($ftp, $remoteFilePath)){
			$res=0;
		}
		ftp_close($ftp);
		return $res;
	}

}