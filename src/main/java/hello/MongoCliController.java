package hello;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteConcernException;
import com.mongodb.WriteResult;

@RestController
public class MongoCliController {
	
	@Autowired
	private DB db;
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Object test(@RequestBody BasicDBObject message){
		return new BasicDBObject("test", message);
	}
	
	
	@RequestMapping(value = "/mongo", method = RequestMethod.POST)
	public Object mongo(@RequestBody BasicDBObject message){
		String collection = message.getString("collection", "foo");
		String method = message.getString("method", "find");
		Object[] args = ((ArrayList) message.get("args")).toArray();
		DBCollection coll = db.getCollection(collection);
		
		Class[] types = new Class[args.length];
		if(method.equals("insert") ){
			// wraps insert(DBObject) in insert(DBObject...) or insert(List<DBObject>) because it fails
			List<DBObject> temp = new ArrayList<DBObject>();
			args[0] = unwrap(Arrays.asList(args));
			types[0] = List.class;

        }else{
        	for (int i = 0; i < args.length; i++) {
                if (args[i] == null) types[i] = DBObject.class;
                else {
                	types[i] = args[i].getClass();
                    if (types[i].equals(LinkedHashMap.class)){ // convert Maps to DBObjects
                    	args[i] = new BasicDBObject( (LinkedHashMap) args[i]);
                    	types[i] = DBObject.class;
                    }else if(types[i].equals(Boolean.class)){
                    	types[i] = boolean.class;
                    }
                }
            }
        }
        
        
        
		Object result = null;
		try {
			result = coll.getClass().getMethod(method, types).invoke(coll, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | WriteConcernException e) {
			
			e.printStackTrace();
			return new BasicDBObject("error", e.getMessage());
		}
		
		if (method.equals("find")){
			DBCursor cursor = (DBCursor) result;
	        List<Object> list = new ArrayList<Object>();
	        while (cursor.hasNext())
	        	list.add(cursor.next());
	        cursor.close();
	        return list;
	        
		
		}
		
		return result;
	}
	
	
	public List<DBObject> unwrap(List<Object> args){
		List<DBObject> ret = new ArrayList<DBObject>();
		for (Object o : args){
			if (o.getClass().equals(LinkedHashMap.class)){
				ret.add(new BasicDBObject( (LinkedHashMap) o));
			}else if (o.getClass().equals(ArrayList.class)){
				ret.addAll(unwrap((ArrayList) o));
			}
		}
		return ret;
	}

}
