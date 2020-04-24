package penta.database.util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;

public class DtoManager {
	
	private Object dtoObject;
	
	public DtoManager(Object dto)
	{
		dtoObject = dto;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> MakeDtoObjectArray(ResultSet result, Object dtoObject)
	{
		ArrayList<T> objects = new ArrayList<T>();
		
		try{
			Class<? extends Object> dtoClass = dtoObject.getClass();
			Field[] fields = dtoClass.getDeclaredFields();
			
			while(result.next())
			{
				Object _object = dtoClass.newInstance();
				
				for(Field field : fields)
				{
					field.setAccessible(true);
					field.set(_object, result.getObject(field.getName()));
				}
				
				objects.add((T)_object);
			}
			
			return objects;
			
		}catch(Exception e) {}
		
		return null;
	}
	
	public ArrayList<Tuple<String, String>> Map()
	{
		List<Field> fields = new ArrayList<Field>();
		Class<? extends Object> dtoClass = dtoObject.getClass();
		
		fields = Arrays.asList(dtoClass.getDeclaredFields()); 
		ArrayList<Tuple<String, String>> mapping = new ArrayList<Tuple<String, String>>();
		
		for(int i = 0; i < fields.size(); i++)
		{
			Field field = fields.get(i);
			field.setAccessible(true);
			try {
				String key = field.getName();
				Object value = field.get(dtoObject);
				
				if(field.getAnnotation(EntityType.class) != null)
					if(field.getAnnotation(EntityType.class).type() == Type.PrimaryKey &&
					   field.getAnnotation(EntityType.class).pkType() == PKType.Auto_Increment)
						continue;
				
			mapping.add(new Tuple<String, String>(key, value.toString()));
				
			} catch (IllegalArgumentException e) {}
			  catch (IllegalAccessException e) {}
		}
		
		Collections.reverse(mapping);
		return mapping;
	}
	
	public String GetPrimaryKeyName()
	{
		try{
			Class<? extends Object> dtoClass = dtoObject.getClass();
			
			Field[] fields = dtoClass.getDeclaredFields();
			for(Field field : fields)
			{
				if(field.getAnnotation(EntityType.class) != null)
					if(field.getAnnotation(EntityType.class).type() == Type.PrimaryKey)
						return field.getName();
			}
			
		}catch(Exception e) {}
		
		return null;
	}
	
	public String GetPrimaryKey()
	{
		try{
			Class<? extends Object> dtoClass = dtoObject.getClass();
			
			Field[] fields = dtoClass.getDeclaredFields();
			for(Field field : fields)
			{
				if(field.getAnnotation(EntityType.class) != null)
				{
					if(field.getAnnotation(EntityType.class).type() == Type.PrimaryKey)
					{
						field.setAccessible(true);
						return field.get(dtoObject).toString();
					}
				}
			}
			
		}catch(Exception e) {}
		
		return null;
	}
	
	public String GetTableName()
	{
		try{
			Class<? extends Object> dtoClass = dtoObject.getClass();
			
			@SuppressWarnings("rawtypes")
			Constructor[] constructors = dtoClass.getConstructors();

			for(Constructor<?> constructor : constructors)
			{
				if(constructor.getAnnotation(TableName.class) != null)
					return ((TableName)constructor.getAnnotation(TableName.class)).name();
			}
			
		}catch(Exception e) {}
		
		return null;
	}

}
