package yuan.ocean.ThreadCollection;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import yuan.ocean.Entity.Station;
import yuan.ocean.Util.Decode;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Yuan on 2017/4/17.
 */
public class ObservationInsertThread extends Thread {
    private static Logger log=Logger.getLogger(ObservationInsertThread.class);
    Station station=null;
    String subFilePath;
    String fileDecodeClassName;
    String fileName;
    DownloadInsertStorage downloadInsertStorage;
    Map linkedProperty=null;

    public ObservationInsertThread(String stationID,Map linkedProperty,String subFilePath,String fileName,String fileDecodeClassName,DownloadInsertStorage downloadInsertStorage){
        this.downloadInsertStorage=downloadInsertStorage;
        this.linkedProperty=linkedProperty;
        this.fileName=fileName;
        this.subFilePath=subFilePath;
        this.fileDecodeClassName=fileDecodeClassName;
        //get station data structure form sos
        try {
         station = Decode.parseSensorML(stationID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            System.out.println("This sensor has problem:"+stationID);
            log.error("This sensor has problem:"+stationID);
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        downloadInsertStorage.insertObservation(station,subFilePath,fileName,fileDecodeClassName,linkedProperty);
    }
}
