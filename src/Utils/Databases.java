package Utils;

import Ismakinesi.IsMakinesi;
import Planlamaci.Planlamaci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Databases {
    List<IsMakinesi> ısMakinesiListesi = Collections.synchronizedList(new ArrayList<>());
    List<Planlamaci> planlamaciListesi = Collections.synchronizedList(new ArrayList<>());
}
