import org.apache.xmlbeans.XmlException;
import yuan.ocean.SensorConfigReader;
import yuan.ocean.Util.Decode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yuan on 2017/6/13.
 */
public class XMLParseTest {
    public static void main(String[] args) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        if (stringBuilder.toString().equals("")) System.out.println("I am null");
        System.out.println(stringBuilder.toString());
//        SensorConfigReader.reader();
//        BufferedReader reader=new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("stationID.csv")));
//        String temp=null;
//        while ((temp=reader.readLine())!=null){
//          String[] eles =temp.split(",");
//            try {
//                Decode.parseSensorML(eles[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (XmlException e) {
//                System.out.println(eles[0]);
//                e.printStackTrace();
//            }
//        }
//        try {
//            Decode.parseSensorML("urn:liesmars:insitusensor:platform:Korea-DaeheuksandoTideStation-DT_0035");
//        } catch (XmlException e) {
//            e.printStackTrace();
//        }
//        int x=0;
    }
}
