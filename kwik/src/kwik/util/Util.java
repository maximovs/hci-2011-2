package kwik.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Util {
	public static List<? extends Map<String, ?>> getMapped(List<?> objects, String[] desired_fields) {
		ArrayList<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
		for (Object object : objects) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Class<? extends Object> c = object.getClass();
			for (Field field : c.getFields()) {
				Annotation[] ann = (Annotation[]) field.getAnnotations();
				for (Annotation annotation : ann) {
					if (annotation instanceof Element || 
						annotation instanceof Attribute) {
						try {
							boolean is_in = false;
							for (String desired_field : desired_fields) {
								if (desired_field.equals(field.getName())) {
									is_in = true;
									break;
								}
							}
							if (is_in) {
								map.put(field.getName(), field.get(object));
							}
							
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			list.add(map);
		}
		return list;
	}
}
