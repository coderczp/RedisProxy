#RedisProxy:基于Grizzly的Redis实现
 
目标:<br>
1、支持分片<br>
2、支持动态扩容<br>
3、支持主从的自动切换<br>
4、支持Web监控和管理<br>
5、支持多种存储方式[Redis/InVM/Memcache/LocalFile/LevelDB]

目前已实现协议解析,下一步将会做路由