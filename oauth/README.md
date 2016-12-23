###security相关配置必须放入application.properties
配置项可以去源码org.springframework.boot.autoconfigure.security.oauth2寻找
###测试oauth服务器
开启服务器，并在控制台获取
Using default security password: 630e4506-06f1-4d95-85a3-4064ed24039a
security.oauth2.client.clientId = 7887e57b-6619-42a3-8fb4-047f6ed6ef0b
security.oauth2.client.secret = a770ba84-5d4e-46f8-aca9-2b40fff2312e

###授权
浏览器打开 http://localhost:8000/oauth/authorize?client_id=7887e57b-6619-42a3-8fb4-047f6ed6ef0b&response_type=code&scope=all&redirect_uri=http://localhost:8000
期间会提示登录 用户名 user 密码 630e4506-06f1-4d95-85a3-4064ed24039a

redirect URL中含有code
###获取token
POST方式访问http://localhost:8000/oauth/token
期间会提示登录 用户名 7887e57b-6619-42a3-8fb4-047f6ed6ef0b 密码 a770ba84-5d4e-46f8-aca9-2b40fff2312e
POST form表单方式提交
code AmfPo8
grant_type authorization_code
scope all
redirect_uri http://localhost:8000

scope redirect_uri必须和授权时一致

{
"access_token": "e101ad2d-ae2b-438b-ba8c-c27be5e72722",
"token_type": "bearer",
"refresh_token": "a8ca6e2b-beb4-4d30-97b4-b41e8af5c172",
"expires_in": 43199,
"scope": "all"
}

###刷新token
token过期时，可以通过http://localhost:8000/oauth/token刷新token
期间会提示登录 用户名 7887e57b-6619-42a3-8fb4-047f6ed6ef0b 密码 a770ba84-5d4e-46f8-aca9-2b40fff2312e
POST form表单方式提交
refresh_token 78c15044-c2b0-4ed6-8771-200794caf8c7
grant_type refresh_token
scope all

###验证token
http://localhost:8000/oauth/check_token?token=e101ad2d-ae2b-438b-ba8c-c27be5e72722
期间会提示登录 用户名 7887e57b-6619-42a3-8fb4-047f6ed6ef0b 密码 a770ba84-5d4e-46f8-aca9-2b40fff2312e


###password方式直接获取token
POST方式访问http://localhost:8000/oauth/token
期间会提示登录 用户名 7887e57b-6619-42a3-8fb4-047f6ed6ef0b 密码 a770ba84-5d4e-46f8-aca9-2b40fff2312e
POST form表单方式提交
grant_type password
username user
password user
scope all