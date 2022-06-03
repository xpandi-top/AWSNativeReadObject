package lambda;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import saaf.Inspector;

import java.util.Date;
import java.util.HashMap;

import static basic.MetadataRetriever.*;
import static basic.ObjectStorage.readBlob;

public class ReadObject implements RequestHandler<Request, HashMap<String,Object>> {
    static AmazonS3 s3Client;
    static BasicAWSCredentials awsCred = new BasicAWSCredentials(identity, credential);
    private static Long initializeConnectionTime;
    public void procedure(Request request, Inspector inspector){
        if (!isMac){
            inspector.inspectAll();
        }
        //*************FunctionStart**************
        boolean connect = false;
        int count = 2;
        String myObjectName = objectName;
        String myContainerName = containerName;
        if (request!=null&&request.getObjectName()!=null) myObjectName = request.getObjectName();
        if (request!=null&&request.getContainerName()!=null) myContainerName = request.getContainerName();
        if (request!=null && request.getCount()>0) count = request.getCount();
        int actual_count = count;
        // initial s3Client
        if (s3Client==null){
            s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCred))
                    .withRegion(Regions.DEFAULT_REGION)
                    .build();
            initializeConnectionTime = new Date().getTime();
            System.out.println("start initializing");
        }
        // function
        try{
            readBlob(s3Client,myContainerName,myObjectName);
            connect=true;
        }catch  (Exception e) {
            inspector.addAttribute("duration", new Date().getTime()-initializeConnectionTime);
            e.printStackTrace();
        }
        //*********************Function end
        getMetrics(isMac,inspector,count,actual_count,connect,initializeConnectionTime);
    }
    @Override
    public HashMap<String, Object> handleRequest(Request request, Context context) {
        Inspector inspector = new Inspector();
        procedure(request,inspector);
        return inspector.finish();
    }
//    public static void main(String[] args) {
//        ReadObject readObject = new ReadObject();
////        readObject.handleRequest(new Request(),null);
//        System.out.println(readObject.handleRequest(new Request(),null));
//    }
}
