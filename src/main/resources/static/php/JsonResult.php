<?php


class JsonResult  
{
    public $msg;
    public $code=0;
    public $data;
    
	function __construct() {
       
   }
   public function fail0(){
	   
	    $this->msg="fail";
		$this->code=-1;
		$this->data=null;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function fail1($msg){
	   
	    $this->msg=$msg;
		$this->code=-1;
		$this->data=null;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function fail2($data,$msg){
	   
	    $this->msg=$msg;
		$this->code=-1;
		$this->data=$data;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function succ0(){
	   
	    $this->msg="success";
		$this->code=0;
		$this->data=null;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function succ1($data){
	   
	    $this->msg="success";
		$this->code=0;
		$this->data=$data;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function succ2($data,$msg){
	   
	    $this->msg=$msg;
		$this->code=0;
		$this->data=$data;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function res3($data,$code,$msg){
	   
	    $this->msg=$msg;
		$this->code=$code;
		$this->data=$data;
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
   
   public function res(){
	   return json_encode($this,JSON_UNESCAPED_UNICODE);
   }
}

