# HyCloud API文档  
## 1 项目管理  
#### 1.1 创建项目  
在设备连接HyCloud之前，需要预先创建一个项目，设备需要保存HyCloud返回的项目id，以此作为身份识别的凭证。
##### api地址  
`POST` `/hycloud/v1/project`  
##### POST参数  

参数名 | 是否必须 | 说明
---|---|----
name | 是 | 项目名
msg | 否 | 项目信息 
data | 是 | 项目所有数据的信息，格式为json数组
##### data内参数:  
参数名 | 是否必须 | 说明
---|---|----
name | 是 | 数据名称，只能由英文、数字、下划线组成
note | 否 | 数据的注释
type | 是 | 数据的类型(int/float)

##### 请求示例  
```
{
	"name" : "project_1",
	"msg" : "demo project",
	"data" : [{
		"name" : "humi",
		"note" : "湿度",
		"type": "int"
	}, {
		"name" : "temp",
		"note" : "温度",
		"type" : "float"
	}]
}
```
##### 返回示例  
```
{
    "code" : 0,
    "msg" : "suc",
    "project_id" : 1  //返回项目的id，设备需要保存这个id
}
```  
#### 1.2 查询项目列表  
返回HyCloud上所有项目的信息列表  
##### api地址  
`GET` `/hycloud/v1/data`  

## 2. 数据传输  
#### 2.1 上传数据  
设备以报文的形式将数据上传至HyCloud服务器，服务器根据请求内包含的项目id存储数据。  
##### 请求行 
`POST hycloud/v1/data/project_id HTTP/1.1`  
##### 请求行参数  
参数名 | 是否必须 | 说明
---|---|----
project_id | 是 | 项目的id
##### 请求头
关键字 | 取值说明
---|--- 
Host | 必须设置host地址
Content-Type|应设置为application/json;charset=utf-8
Content-Length| POST数据的字符串长度（实测中如果不带此项，无法正确交互）
##### post数据参数  
参数名 | 是否必须 | 说明
---|---|----  
status | 否 | 设备状态，1为正常，0为不正常  
data | 是 | 设备上传的数据，格式为json数组  
##### data内参数
参数名 | 是否必须 | 说明
---|---|----  
key | 是 | 数据名
value | 是 | 数据的值
##### 请求报文示例
```
POST /hycloud/v1/data/1 HTTP/1.1
Host:www.test.com
Content-Type:application/json;charset=utf-8
Content-Length:104

 {
	"status": 1,
	"data": [{
		"key": "humi",
		"value": 40
	}, {
		"key": "temp",
		"value": 26.2
	}]
}
```
#### 2.2 查询数据  
查询一个项目的数据  
##### api地址  
`GET` `/hycloud/v1/data/project_id`  
##### URL参数  
参数名 | 是否必须 | 说明
---|---|----
project_id | 是 | 项目的id  
page | 否 | 分页显示时的页数
size | 否 | 分页显示时每页的数据量
#### 请求示例
GET http://www.test.com/hycloud/v1/data/1?page=1&size=10
##### 返回参数  
参数名 |  说明
---|----
id | 该条数据的id
time | 数据上传的时间
data | 数据内容，格式为json数组
key | 数据名
value | 数据的值
##### 返回示例  
```
[{
	"id": 2,
	"time": "2018-10-22T13:52:15.000+0000",
	"data": [{
		"key": "humi",
		"value": 40
	}, {
		"key": "temp",
		"value": 26.2
	}]
},
{
	"id": 1,
	"time": "2018-10-22T13:51:25.000+0000",
	"data": [{
		"key": "humi",
		"value": 40
	}, {
		"key": "temp",
		"value": 26.3
	}]
}]
```
