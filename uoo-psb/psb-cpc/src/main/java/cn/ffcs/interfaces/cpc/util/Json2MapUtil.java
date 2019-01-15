package cn.ffcs.interfaces.cpc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		}else if(o instanceof List){
			List<Object> list = new ArrayList<>();
			((List<Object>) o).forEach((m)->{
				Map<String,Object> map = new HashMap<>();
				if(m instanceof Map){
					((Map<String,Object>)m).forEach((str,obj)->{
						map.put(str, recursion(obj));
					});
					list.add(map);
				}else{
					list.add(recursion(m));
				}

			});
			return list;
		}else {
			return o;
		}
	}

	public static void main(String[] args) {
		String json = "{\"name\":\"test\",\"scores\":[{\"class\":\"语文\",\"score\":89},{\"class\":\"数学\",\"score\":99}],\"test\":[\"a1\",\"a2\",\"a3\"]}";
		Map<String,Object> map = handle(json);
		System.out.println(map);
	}
}