<?php
class DataBasic implements \Serializable {

    var $servername = "localhost";
    public $port = 3306;
    public $username = "root";
    public $password = 'root';
    public $dbname = "accident";

    public function getConnection() {
        $conn = new mysqli($this->servername . ":" . $this->port, $this->username, $this->password, $this->dbname);
        if ($conn->connect_error) {
            return null;
        }
        $conn->query("set names utf8"); //防止乱码
        $conn->query("SET time_zone = '+8:00'"); //设置时区
        return $conn;
    }

    public function execute($sql) {
//        echo $sql;
        $conn = $this->getConnection();
        if ($conn == null)
            return -1;
        $result = $conn->query($sql);
        $conn->close();
        if ($result === TRUE) {
            return 0;
        } else if ($result->num_rows >= 0) {
            return $result->fetch_all(MYSQLI_ASSOC);
        } else {
            return -1;
        }
    }

    public function save($table, $data) {
        //echo  $this->getInsertSql($table,$data);
        $conn = $this->getConnection();
        if ($conn == null) return -1;
        $res = $conn->query($this->getInsertSql($table,$data));
        $id = -1;
        if ($res === TRUE){
            $id = mysqli_insert_id($conn);
        }
        $conn->close();
        return $id;
    }

    /**
     * @param $table 表名
     * @param $whereData 条件 k=v...
     * @return int|mixed
     */
    public function del($table, $whereData) {
        $whereSql = $this->getWhereAndSql($whereData);
        $sql = "delete from $table $whereSql";
        return $this->execute($sql);
    }

    public function update($table, $setData, $whereData) {
        $setSql = $this->getSetSql($setData);
        $whereSql = $this->getWhereAndSql($whereData);
        $sql = "update $table $setSql $whereSql";
        return $this->execute($sql);
    }

    public function query($table, $queryColumn, $whereData) {
        $whereSql = $this->getWhereAndSql($whereData);
        $queryColumns = $this->getQueryColumn($queryColumn);
        $sql = "select $queryColumns from $table $whereSql";
        return $this->execute($sql);
    }

    public function getInsertSql($table, $arr) {
        $index = 0;
        $keyArr = array();
        $valArr = array();
        foreach ($arr as $k => $v) {
            if (is_null($v)||$v==='')
                continue;
            $keyArr[$index] = $k;
            $valArr[$index] = $v;
            $index++;
        }
        $key = $this->getColumnSql($keyArr);
        $value = $this->getValuesSql($valArr);
        return 'insert into ' . $table . $key . '  values ' . $value;
    }

    /**
     * @param $arr ['column1','column2','column3']
     * @return string 返回 (column1,column2,column3)
     */
    public function getColumnSql($arr) {
        return ' ( ' . join(",", $arr) . ' ) ';
    }

    /**
     * @param $arr 诸如['value1','value2',value3]
     * @return string 返回 ('value2,'value2','value3')
     */
    public function getValuesSql($arr){
        return " ( '".join("','",$arr)."' ) ";
    }

    public function getWhereAndSql($arr) {
        return ' where ' . $this->getKVWithSeparator($arr,'and');
    }

    public function getOrSql($arr) {
        return $this->getKVWithSeparator("or");
    }

    public function getSetSql($arr) {
        return ' set ' . $this->getKVWithSeparator($arr,',');
    }

    /**
     * @param $arr
     * @param $separator 分隔符
     * @return string
     */
    public function getKVWithSeparator($arr, $separator){
        $paramArr = array();
        foreach ($arr as $k => $v) {
            if (is_null($v)||$v==='')
                continue;
            array_push($paramArr," $k = '$v' ");
        }
        return join(" $separator ", $paramArr);
    }


    /**
     * sql 的几种变种
     * insert | select  (column1,column2,column3.....)
     * values | in ( 'value1','value2','value3'......)
     * where and (column1='value1' and column2 = 'value2'.....)
     * set (column1='value1' , column2 = 'value2'.....)
     */


    /**
     * @return string
     */
    public function getServername() {
        return $this->servername;
    }

    /**
     * @return number
     */
    public function getPort() {
        return $this->port;
    }

    /**
     * @return string
     */
    public function getUsername() {
        return $this->username;
    }

    /**
     * @return string
     */
    public function getPassword() {
        return $this->password;
    }

    public function serialize() {
        
    }

    public function unserialize($serialized) {
        
    }

}

