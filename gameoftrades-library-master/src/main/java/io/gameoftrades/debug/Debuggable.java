package io.gameoftrades.debug;

/**
 * Maakt een algoritme visueel debugbaar. 
 * Dat wil zeggen dat het algoritme de debugger kan aanroepen op geselecteerde momenten om
 * zo een coordinaat of pad te tonen op de kaart. 
 */
public interface Debuggable {

    void setDebugger(Debugger debugger);

}
