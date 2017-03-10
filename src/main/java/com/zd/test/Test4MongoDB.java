//package com.zd.test;
//
//import org.bson.Document;
//import org.bson.types.ObjectId;
//
//import com.mongodb.Bytes;
//import com.mongodb.MongoClient;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//
//public class Test4MongoDB {
//	
//	private static final String host = "127.0.0.1";
//	
//	private static final int port = 27017;
//	
//	private static final String DB_NAME = "testDB";
//	
//	private static MongoClient client;
//	
//	private static MongoDatabase db;
//	static{
//		
//		client = new MongoClient(host, port);
//		db = client.getDatabase(DB_NAME);
//	}
//	
//	public static void test1(){
//		System.out.println(db.getName());
//		MongoCollection<Document> collection = db.getCollection("deviceInfo");
//		long count = collection.count();
//		System.out.println(count);
//		FindIterable<Document> find = collection.find();
//		MongoCursor<Document> iterator = find.iterator();
//		while (iterator.hasNext()) {
//			Document document = (Document) iterator.next();
//			System.out.println(document);
//		}
//		
//		ObjectId objectId = new ObjectId();
//		Bytes bytes = new com.mongodb.Bytes();
//	}
//	
//	public static void main(String[] args) {
//		test1();
//	}
//}
