package hello;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlController {
	
	Method update, queryForList;
	
	public SqlController() throws NoSuchMethodException, SecurityException {
		update = JdbcTemplate.class.getMethod("update", new Class[]{String.class,Object[].class});
		queryForList = JdbcTemplate.class.getMethod("queryForList", new Class[]{String.class,Object[].class});
	}
	
	@Autowired
    protected JdbcTemplate db;
	
	@RequestMapping(value = "/sql", method = RequestMethod.POST)
	public @ResponseBody Object sqlQuery(@RequestBody List<Object> message){
		System.out.println(message);
		String q = (String) message.remove(0);
		try {

			if (q.trim().toUpperCase().startsWith("SELECT")){
				if (message.size()==0)
					return db.queryForList(q);
				else{
					return queryForList.invoke(db, q, message.toArray());
				}
			}else{
				if (message.size()==0)
					return db.update(q);
				else{
					return update.invoke(db, q, message.toArray());
				}
			}
			
			
		} catch (DataAccessException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "Exception: "+e.getMessage();
		}
	}
	
	
	@RequestMapping(value = "/tests", method = RequestMethod.POST)
	public @ResponseBody Object test(@RequestBody LinkedHashMap message){
		return message;
	}

}
