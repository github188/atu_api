package com.atu.api.web.controller.utils;

public class DelRedis {
	
	public void clearRedis(){
		//安卓
		RedisUtils.del("api-cache-userId-30_1001_2");
		RedisUtils.del("api-cache-userId-30_1002_2");
		RedisUtils.del("api-cache-userId-30_1003_2");
		RedisUtils.del("api-cache-userId-30_1004_2");
		RedisUtils.del("api-cache-userId-30_1005_2");
		RedisUtils.del("api-cache-userId-30_1006_2");
		RedisUtils.del("api-cache-userId-30_1007_2");
		RedisUtils.del("api-cache-userId-30_1008_2");
		
		//iOS
		RedisUtils.del("api-cache-userId-40_1001_2");
		RedisUtils.del("api-cache-userId-40_1002_2");
		RedisUtils.del("api-cache-userId-40_1003_2");
		RedisUtils.del("api-cache-userId-40_1004_2");
		RedisUtils.del("api-cache-userId-40_1005_2");
		RedisUtils.del("api-cache-userId-40_1006_2");
		RedisUtils.del("api-cache-userId-40_1007_2");
		RedisUtils.del("api-cache-userId-40_1008_2");
	}

}
