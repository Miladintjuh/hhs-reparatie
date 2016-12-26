package io.gameoftrades.ui;

import io.gameoftrades.model.markt.actie.HandelsPositie;

/**
 * Maakt het mogelijk om een component te laten reageren op nieuwe HandelsPosities.  
 */
public interface HandelsPositieListener {

    void setHandelsPositie(HandelsPositie pos);

}
