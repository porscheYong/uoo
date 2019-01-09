package cn.ffcs.interfaces.cpc.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * json ->Map
 * @author os.wuh@ffcs.cn
 */
public class Json2MapUtil {
	public static Map<String,Object> handle(String json) {
		
		HashMap<String,Object> initMap = JSON.parseObject(json, HashMap.class);
		
		Map<String,Object> map = new HashMap<>();
		initMap.forEach((str,obj)->{
			map.put(str, recursion(obj));
		});
		return map;
	}

	private static Object recursion(Object o) {
		if(o instanceof Map) {
			Map<String,Object> map = new HashMap<>();
			((Map<String,Object>) o).forEach((str,obj)->{
				map.put(str, recursion(obj));
			});
			return map;
		}else {
			return o;
		}
	}
}