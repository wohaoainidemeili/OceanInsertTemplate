package yuan.ocean.InsertObservationService;

import yuan.ocean.Entity.ObservedProperty;
import yuan.ocean.Entity.SOSWrapper;
import yuan.ocean.Entity.Sensor;
import yuan.ocean.Entity.Station;
import yuan.ocean.SensorConfigInfo;
import yuan.ocean.Util.Encode;
import yuan.ocean.Util.HttpRequestAndPost;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yuan on 2017/6/3.
 */
public class KoreanDecodeFile implements IDecodeFile {
    public void decode(Map<String, String> linkedProperty, String fileName, Station station, String subFilePath) {
        //read file
        File file = new File(SensorConfigInfo.getDownloadpath()+"\\"+subFilePath+"\\" +fileName);
        if (file.exists()){
            BufferedReader bufferedReader= null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String temp = null;
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                int timePos = Integer.valueOf(linkedProperty.get("time"));
                while ((temp=bufferedReader.readLine())!=null){
                    String[] eles=temp.split(",");
                    String obsTimeStr=eles[timePos];
                    obsTimeStr= getTime(obsTimeStr);
                    if (obsTimeStr!=null) {
                        for (Sensor sensor : station.getSensors()) {
                            if (isSensorMatchProperty(linkedProperty, sensor)) {
                                SOSWrapper sosWrapper = new SOSWrapper();
                                sosWrapper.setLat(sensor.getLat());
                                sosWrapper.setLon(sensor.getLon());
                                sosWrapper.setSensorID(sensor.getSensorID());
                                sosWrapper.setSrid(4326);
                                sosWrapper.setSimpleTime(obsTimeStr);
                                for (ObservedProperty observedProperty : sensor.getObservedProperties()) {
                                    if (!eles[Integer.valueOf(linkedProperty.get(observedProperty.getPropertyID()))].equals("")) {
                                        observedProperty.setDataValue(eles[Integer.valueOf(linkedProperty.get(observedProperty.getPropertyID()))]);
                                    } else {
                                        observedProperty.setDataValue("-32768");
                                    }
                                }
                                sosWrapper.setProperties(sensor.getObservedProperties());
                                //encode xml and insert into sos
                                String insertXML = Encode.getInserObservationXML(sosWrapper);
                                String responseXML = HttpRequestAndPost.sendPost(SensorConfigInfo.getUrl(), insertXML);
                                System.out.println(responseXML);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists())
                    file.delete();
            }


        }
    }
    public boolean isSensorMatchProperty(Map<String,String> linkedProperty,Sensor sensor){
        boolean isContain=true;
        for (ObservedProperty property:sensor.getObservedProperties()){
            if (!linkedProperty.containsKey(property.getPropertyID()))
                isContain=false;
        }
        return isContain;
    }
    public String getTime(String timeStr){
        String resultDate=null;
        SimpleDateFormat simpleSourceDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleResultDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date obsTime= simpleSourceDateFormat.parse(timeStr);
            resultDate=simpleResultDateFormat.format(obsTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }
}
