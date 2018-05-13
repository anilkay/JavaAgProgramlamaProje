import Ismakinesi.IsMakinesi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsMakinesiList {
   List<IsMakinesi> ısMakinesiList=Collections.synchronizedList(new ArrayList<>());
    public synchronized boolean addIsMakinesi(IsMakinesi is){
        ısMakinesiList.add(is);

        return true;
    }
}
