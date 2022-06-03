package basic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import saaf.Inspector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Get provider and credential.json information from jsonfile
 */
public class MetadataRetriever {
    public static final JsonObject jsonfile = readIni("credential.json");
    public static final String provider = jsonfile.get("provider").getAsString();
    public static final String identity = jsonfile.get("identity").getAsString();// Access Key ID
    public static final String credential =jsonfile.get("credential").getAsString();//Secret Access Key.
    public static String containerName = jsonfile.get("containerName").getAsString();// bucket namespace
    public static String objectName=jsonfile.get("objectName").getAsString();
    public static String osname = System.getProperty("os.name");
    public static boolean isMac = osname.contains("Mac");

    // Add some common attributes to saaf
    public static void getMetrics(boolean isMac, Inspector inspector, int count, int actual_count, boolean connect, Long initializeConnectionTime) {
        inspector.addAttribute("initializeConnectionTime", initializeConnectionTime);// initialize connection Time for the storage connect
        inspector.addAttribute("connect",connect);// whether there is a connection to storage
        inspector.addAttribute("actual_count",actual_count);
        inspector.addAttribute("count",count);
        if(connect) inspector.addAttribute("duration", new Date().getTime()- initializeConnectionTime);
        if (!isMac){
            inspector.inspectAllDeltas();
        }
    }

    public static JsonObject readIni(String filename) {
        Gson gson = new Gson();
        String file="{}";
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filename);
            assert is != null;
            file = new String(is.readAllBytes(), StandardCharsets.UTF_8);
//            System.out.println(file);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return gson.fromJson(file, JsonObject.class);
    }

}
