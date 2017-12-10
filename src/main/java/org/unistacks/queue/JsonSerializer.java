package org.unistacks.queue;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by mazean on 2017/9/21
 */
public class JsonSerializer implements SesameSerializer {
    @Override
    public byte[] serializeGDMList(List<GenericDataModel> gdmList) {
        String jsonString = JSON.toJSONString(gdmList);
        return jsonString.getBytes();
    }

    @Override
    public List<GenericDataModel> deserializeGDMList(byte[] gdmListInBytes) {
        List<GenericDataModel> gdmList = JSON.parseArray(new String(gdmListInBytes), GenericDataModel.class);
        return gdmList;
    }

    @Override
    public GenericDataModel deserializeGDM(byte[] gdmInBytes) {
        return JSON.parseObject(new String(gdmInBytes), GenericDataModel.class);
    }
//
//    @Override
//    public GDMPage deserializeGDMPage(byte[] gdmPageInBytes) {
//        return JSON.parseObject(new String(gdmPageInBytes), GDMPage.class);
//    }
}
