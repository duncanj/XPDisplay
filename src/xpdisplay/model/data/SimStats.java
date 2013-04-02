/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class SimStats extends DataObject {
    
    // explo,__DIM |   explo,__USE |   cratr,__DIM |   cratr,_USE_ |   puffs,__TOT |   puffs,__VIS |   _tris,__vis |   ____q,depth
    
    @Metadata(displayLabel="Explo (DIM)")
    private Float exploDim;
    
    @Metadata(displayLabel="Explo (USE)")
    private Float exploUse;
    
    @Metadata(displayLabel="Cratr (DIM)")
    private Float cratrDim;
    
    @Metadata(displayLabel="Cratr (USE)")
    private Float cratrUse;
    
    @Metadata(displayLabel="Puffs (Total)")
    private Float puffsTot;
    
    @Metadata(displayLabel="Puffs (Visible)")
    private Float puffsVis;
    
    @Metadata(displayLabel="Number of visible triangles")
    private Float trianglesVisible;
    
    @Metadata(displayLabel="Q Depth")
    private Float qDepth;
    
            
    
    public SimStats() {
    }

    public Float getExploDim() {
        return exploDim;
    }

    public void setExploDim(Float exploDim) {
        this.exploDim = exploDim;
    }

    public Float getExploUse() {
        return exploUse;
    }

    public void setExploUse(Float exploUse) {
        this.exploUse = exploUse;
    }

    public Float getCratrDim() {
        return cratrDim;
    }

    public void setCratrDim(Float cratrDim) {
        this.cratrDim = cratrDim;
    }

    public Float getCratrUse() {
        return cratrUse;
    }

    public void setCratrUse(Float cratrUse) {
        this.cratrUse = cratrUse;
    }

    public Float getPuffsTot() {
        return puffsTot;
    }

    public void setPuffsTot(Float puffsTot) {
        this.puffsTot = puffsTot;
    }

    public Float getPuffsVis() {
        return puffsVis;
    }

    public void setPuffsVis(Float puffsVis) {
        this.puffsVis = puffsVis;
    }

    public Float getTrianglesVisible() {
        return trianglesVisible;
    }

    public void setTrianglesVisible(Float trianglesVisible) {
        this.trianglesVisible = trianglesVisible;
    }

    public Float getQDepth() {
        return qDepth;
    }

    public void setQDepth(Float qDepth) {
        this.qDepth = qDepth;
    }

    public void updateSimState(SimState simState) {
        SimStats s = simState.getSimStats();
        s.setCratrDim(cratrDim);
        s.setCratrUse(cratrUse);
        s.setExploDim(exploDim);
        s.setExploUse(exploUse);
        s.setPuffsTot(puffsTot);
        s.setPuffsVis(puffsVis);
        s.setQDepth(qDepth);
        s.setTrianglesVisible(trianglesVisible);
        s.notifyListeners();
    }
    
    
}
