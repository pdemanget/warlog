package fr.warlog.util;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Utilitaire de conversion JSON.
 *
 * @author ObjetDirect1
 */
public class JSONUtils {
	
	private static final Logger logger = Logger.getLogger(JSONUtils.class);
	
	protected static JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

	/**
	 * Version acceptant les Type Generics pour les listes typées.
	 * @param s
	 * @param type
	 * @return
	 */
	public static Object fromJsonString(String s, Type type) {
		 try {
			  ObjectMapper objectMapper = new ObjectMapper();
			  JavaType javaType = objectMapper.getTypeFactory().constructType(type);
			  objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		      return objectMapper.readValue(s, javaType);
		  }
		  catch (JsonProcessingException ex) {
		      throw new IllegalArgumentException("Could not read JSON: " + ex.getMessage(), ex);
		  } catch (IOException ex) {
			  throw new IllegalArgumentException("Could not read JSON: " + ex.getMessage(), ex);
		}	
	}

	public static <T> T fromJsonString(String s, Class<T> clazz) {
		JavaType javaType = getJavaType(clazz);
	  try {
		  ObjectMapper objectMapper = new ObjectMapper();
		  objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		  objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
//		  objectMapper.configure(MapperFeature.ALLOW_SINGLE_QUOTES, true);
	      return objectMapper.readValue(s, javaType);
	  }
	  catch (JsonProcessingException ex) {
	      throw new IllegalArgumentException("Could not read JSON: " + ex.getMessage(), ex);
	  } catch (IOException ex) {
		  throw new IllegalArgumentException("Could not read JSON: " + ex.getMessage(), ex);
	}	
	}
	
	
	public static String toJsonString(Object o) {
		  ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			logError("transfo JSON", e);
		}
		return "{}";
	}
	
	public static String toJsonStringWithData(Object o) {
	  return toJsonString(new Data(o));
	}
	  
	  
	
	private static void logError(String string, Throwable e) {
		logger.error(string, e);

	}

	public static String toJsonError(Throwable e) {
	   	e.printStackTrace();
    	Data<Object> data = new Data<>(null);
    	data.setSuccess(false);
    	data.setMessage(e.getMessage());
		return toJsonString(data);
	}
	
}
