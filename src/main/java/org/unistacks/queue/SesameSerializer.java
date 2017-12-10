package org.unistacks.queue;

import java.util.List;

/**
 * Created by mazean on 2017/9/21
 */
public interface SesameSerializer {

    public byte[] serializeGDMList(List<GenericDataModel> gdmList);

    public List<GenericDataModel> deserializeGDMList(byte[] gdmListInBytes);

    public GenericDataModel deserializeGDM(byte[] gdmInBytes);

//    public GDMPage deserializeGDMPage(byte[] gdmPageInBytes);
}
