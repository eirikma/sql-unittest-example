package demo.jz2013.korreksjon.core;

import java.util.List;

/**
 * Interface for KorreksjonTilstand instances that have an history of KorreksjonTilstand
 * User: eim
 * Date: 09.02.11
 * Time: 15:55
 */
public interface KorreksjonTilstandHistory extends KorreksjonTilstand {

    List<KorreksjonTilstand> getHistory();

}
