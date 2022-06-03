package basic;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ObjectStorage {
    public static void readBlob(AmazonS3 s3Client, String containerName, String objectName) {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(containerName, objectName));
        InputStream inputStream = s3Object.getObjectContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        reader.lines().forEach(System.out::println);
        System.out.println(reader.lines().count());
    }
//    public static InputStream readBlobContent(AmazonS3 s3Client, String containerName, String objectName){
//        S3Object s3Object = s3Client.getObject(new GetObjectRequest(containerName, objectName));
//        return s3Object.getObjectContent();
//    }
//
//    public static void writeBlob(AmazonS3 s3Client, String containerName, String objectName, String content){
//        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
//        InputStream inputStream = new ByteArrayInputStream(bytes);
//        ObjectMetadata meta = new ObjectMetadata();
//        meta.setContentLength(bytes.length);
//        s3Client.putObject(containerName, objectName, inputStream, meta);
//    }
//
//
//    public static void createContainer(AmazonS3 s3Client, String containerName){
//        boolean exist = s3Client.doesBucketExistV2(containerName);
//        if (!exist){
//            s3Client.createBucket(containerName);
//            System.out.println("Created");
//        }else {
//            System.out.println(containerName + " is existed");
//        }
//    }
//
//    public static void deleteContainer(AmazonS3 s3Client, String containerName){
//        boolean exist = s3Client.doesBucketExistV2(containerName);
//        if (exist){
//            s3Client.deleteBucket(containerName);
//            System.out.println("Deleted");
//        } else {
//            System.out.println("No bucket name "+containerName);
//        }
//    }
//    public static void deleteBlob(AmazonS3 s3Client, String containerName, String objectName){
//        boolean exist = s3Client.doesObjectExist(containerName,objectName);
//        if (exist){
//            s3Client.deleteObject(containerName,objectName);
//            System.out.println(objectName+"was deleted");
//        }else {
//            System.out.println("No object Name" + objectName);
//        }
//    }
}
