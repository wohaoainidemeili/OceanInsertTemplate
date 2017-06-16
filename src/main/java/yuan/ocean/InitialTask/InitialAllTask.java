package yuan.ocean.InitialTask;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Yuan on 2017/4/18.
 */
public class InitialAllTask {
    private static final Logger log=Logger.getLogger(InitialAllTask.class);
    private static ScheduledExecutorService executorService= Executors.newScheduledThreadPool(10);
    public static void startTask(){
        //the file has 6 columns
        //1.the url of the observation 2.the property you want to get 3.current kind of station IDs 4.property reflect relationship file
        // 5.the class package path for decode current kind station's observation file 6.the sub filepath for storing downloaded file
        //7.the property for restriction of current station
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("observationdownloadconfig.txt")));
        String tempStr;
        try {
            while ((tempStr=bufferedReader.readLine())!=null){
               String[] eles=tempStr.split("#");
                log.info("start to load ObservationDownInsertTask for"+eles[0]);
                ObservationDownInsertTask downInsertTask=new ObservationDownInsertTask(eles[0],eles[1],eles[2],eles[3],eles[4],eles[5],eles[6],eles[7]);
                executorService.execute(downInsertTask);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
