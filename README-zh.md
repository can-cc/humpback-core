

## 环境
jdk12

## 开发
环境中需要指定下面的[部署](#部署)中环境变量，这样做的原因是不想创建多个 `application.yaml` profile，因为这个仓库不是一个私有仓库

## 部署
环境变量
- `SPRING_DATA_MONGODB_HOST` (设置 mongodb 的 host，如果端口不是默认的27017，还需要设置`SPRING_DATA_MONGODB_PORT`)
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URI` 
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK-SET-URI`

