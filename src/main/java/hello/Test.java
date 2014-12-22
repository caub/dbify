package hello;


//	@RestController
//	public class MongoCliCTestontroller {
//		
//		@Autowired
//		private DB db;
//		
//		@RequestMapping("/test")
//		public BasicDBObject mongo2(@RequestParam(value="s", defaultValue="World") String name) {
//			return new BasicDBObject("ee", name);
//		}
//		
//		@RequestMapping(value = "/mongo", method = RequestMethod.POST)
//		public BasicDBObject mongo(@RequestBody Map<String, Object> message) {
//			System.out.println(message);
//			String collection = message.getString("collection", "foo");
//			String method = message.getString("method", "find");
//			Object[] args = ((BasicDBList) message.get("args")).toArray();
//			DBCollection coll = db.getCollection(collection);
//			
//			Class[] types = new Class[args.length];
//	        for (int i = 0; i < args.length; i++) {
//	            if (args[i] == null) types[i] = DBObject.class;
//	            else {
//	                Class cls = args[i].getClass();
//	                types[i] = cls.equals(BasicDBObject.class) ? DBObject.class : cls.equals(Boolean.class) ? boolean.class : cls;//(Class) args.get(i).getClass().getGenericInterfaces()[0];
//	            }
//	        }
//			
//			Object result = null;
//			try {
//				result = coll.getClass().getMethod(method, types).invoke(coll, args);
//			} catch (IllegalAccessException | IllegalArgumentException
//					| InvocationTargetException | NoSuchMethodException
//					| SecurityException e) {
//				
//				e.printStackTrace();
//			}
//			
//			if (method.equals("find")){
//				DBCursor cursor = (DBCursor) result;
//		        List<Object> list = new ArrayList<Object>();
//		        while (cursor.hasNext())
//		        	list.add(cursor.next());
//		        cursor.close();
//		        return list;
//		        
//			
//			}
//			
//			return result;
//			
//			return new BasicDBObject("fr", "rvrv");
//		}
//		
//
//	
//}
