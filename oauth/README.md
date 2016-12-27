###测试oauth服务器
开启服务器

###授权
浏览器打开 http://localhost:8000/oauth/authorize?client_id=client&response_type=code&scope=all&redirect_uri=http://localhost:8080
期间会提示登录 用户名 user 密码 123456

redirect URL中含有code
###获取token
POST方式访问http://localhost:8000/oauth/token
期间会提示登录 用户名 client 密码 123456
POST form表单方式提交
code AmfPo8
grant_type authorization_code
scope all
redirect_uri http://localhost:8080

scope redirect_uri必须和授权时一致

{
"access_token": "e101ad2d-ae2b-438b-ba8c-c27be5e72722",
"token_type": "bearer",
"refresh_token": "a8ca6e2b-beb4-4d30-97b4-b41e8af5c172",
"expires_in": 43199,
"scope": "all"
}

###验证token
http://localhost:8000/oauth/check_token?token=e101ad2d-ae2b-438b-ba8c-c27be5e72722
期间会提示登录 用户名 client 密码 123456

###访问ResourceServer
访问http://localhost:8000/pb/resource 无需验证
访问http://localhost:8000/pt/resource
header携带
Authorization: bearer e101ad2d-ae2b-438b-ba8c-c27be5e72722

###刷新token
token过期时，可以通过http://localhost:8000/oauth/token 刷新token
期间会提示登录 用户名 client 密码 123456
POST form表单方式提交
refresh_token a8ca6e2b-beb4-4d30-97b4-b41e8af5c172
grant_type refresh_token
scope all

###password方式直接获取token
POST方式访问http://localhost:8000/oauth/token
期间会提示登录 用户名 client 密码 123456
POST form表单方式提交
grant_type password
username user
password 123456
scope all


----
###生成JWT所需密钥
keytool -genkeypair -alias jwt -keyalg RSA  -keysize 1024 -validity 3650 -keystore jwt_123456.jks
输入密钥库口令: 123456
再次输入新口令: 123456
您的名字与姓氏是什么?
  [Unknown]:  Charles
您的组织单位名称是什么?
  [Unknown]:  Charles
您的组织名称是什么?
  [Unknown]:  Charles
您所在的城市或区域名称是什么?
  [Unknown]:  Wuhan
您所在的省/市/自治区名称是什么?
  [Unknown]:  Hubei
该单位的双字母国家/地区代码是什么?
  [Unknown]:  CN
CN=Charles, OU=Charles, O=Charles, L=Wuhan, ST=Hubei, C=CN是否正确?
  [否]:  y

输入 <jwt> 的密钥口令
        (如果和密钥库口令相同, 按回车): 123456
再次输入新口令: 123456

查看密钥
keytool -list -rfc -alias jwt -keystore jwt_123456.jks

注意：一定要有@Bean JwtAccessTokenConverter的存在
###获得公钥
http://localhost:8000/oauth/token_key
期间会提示登录 用户名 client 密码 123456
客户端可以直接解密，省去check_token，/oauth/token 刷新token

