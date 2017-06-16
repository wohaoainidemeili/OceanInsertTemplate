package yuan.ocean.ThreadCollection;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yuan on 2017/4/17.
 */
public class ObservationDownThread extends Thread {
    String urlFilePath;
    String url;
    String userName;
    String passWord;
    String subFilePath;
    String fileName;
    String downloadClassName;
    DownloadInsertStorage downloadInsertStorage;
    String platCode;
    public ObservationDownThread(String url,String urlFilePath,String userName,String passWord,String subFilePath,String fileName,String downloadClassName,DownloadInsertStorage downloadInsertStorage){
        //get the latesttime from database and create
        this.subFilePath=subFilePath;
        this.urlFilePath=urlFilePath;
        this.url=url;
        this.userName=userName;
        this.passWord=passWord;
        this.fileName=fileName;
        this.downloadClassName=downloadClassName;
        this.downloadInsertStorage=downloadInsertStorage;
        //get the latest time of the station
    }
    @Override
    public void run() {
        //download file
        downloadInsertStorage.downLoadFile(downloadClassName,url,urlFilePath,userName,passWord,subFilePath,fileName);
    }
}
