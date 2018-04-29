import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBxAtlasClass {
	/*	External Archives
	 * 	mongodb-driver-3.6.3.jar
	 * 	mongo-java-driver-3.6.3.jar
	 */
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;
	
	//first connection
	public MongoDBxAtlasClass() {
		MongoClientURI uri = new MongoClientURI("mongodb+srv://kay:myRealPassword@cluster0.mongodb.net/");
		mongoClient = new MongoClient(uri);
		mongoDatabase = mongoClient.getDatabase("DatabaseName");
		mongoCollection = mongoDatabase.getCollection("CollectionName");
	}// end MongoDBxAtlasExemple constructor
	
	public void printfirstDocument() {
		Document myDoc = mongoCollection.find().first();
		System.out.println(myDoc.toJson());
	}
	
	public void printAllDocuments() {
		FindIterable<Document> cursor = mongoCollection.find();
		
		for(Document doc : cursor) {
			System.out.println(doc.toJson());
		}
	}
	
	public void printSpecificDocument(String parameter1, String parameter2) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("parameter1", parameter1);
		searchQuery.put("parameter2", parameter2);
		FindIterable<Document> cursor = mongoCollection.find(searchQuery);
		
		for(Document doc : cursor) {
			System.out.println(doc.toJson());//or System.out.println(doc.get("parameterx"));
		}
	}
	
	public void createNewDocumet(String parameter1, String parameter2) {
		Document document = new Document();
		document.put("parameter1", parameter1);
		document.put("parameter2", parameter2);
		document.put("_id_extra", new ObjectId("5ae350f51c525d2e4439f80f") );
		mongoCollection.insertOne(document);
	}
	
	public void modifyDocument(String parameter1oldDoc,String parameter1newDoc ) {
		BasicDBObject query = new BasicDBObject();
		query.put("parameter1", parameter1oldDoc);

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("parameter1", parameter1newDoc);
					
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		mongoCollection.updateOne(query, updateObj);// or mongoCollection.updateMany(query, updateObj);
	}
	
	
	public void deleteDocuments(String parameter) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("parameter", parameter);

		mongoCollection.deleteMany(searchQuery);//or mongoCollection.deleteOne(searchQuery);
	}
	
	
	public static void main (String [] Args) {
		MongoDBxAtlasClass mongoDBxAtlasExemple= new MongoDBxAtlasClass();
		mongoDBxAtlasExemple.printAllDocuments();
	}// end main
	
}

