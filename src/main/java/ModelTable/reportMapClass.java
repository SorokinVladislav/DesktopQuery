package ModelTable;

public class reportMapClass {
    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public String getPkg_Level() {
        return Pkg_Level;
    }

    public void setPkg_Level(String pkg_Level) {
        Pkg_Level = pkg_Level;
    }

    public String getNextLevel_UIDCode() {
        return NextLevel_UIDCode;
    }

    public void setNextLevel_UIDCode(String nextLevel_UIDCode) {
        NextLevel_UIDCode = nextLevel_UIDCode;
    }

    public String getUIDCode() {
        return UIDCode;
    }

    public void setUIDCode(String UIDCode) {
        this.UIDCode = UIDCode;
    }

    public reportMapClass(String jobID, String pkg_Level, String nextLevel_UIDCode, String UIDCode) {
        JobID = jobID;
        Pkg_Level = pkg_Level;
        if (nextLevel_UIDCode==null)
            NextLevel_UIDCode="";
        else
        NextLevel_UIDCode = nextLevel_UIDCode;
        this.UIDCode = UIDCode;
    }

    public reportMapClass(String jobID, String pkg_Level, String nextLevel_UIDCode, String UIDCode, String isPrinted, String isInspected, String isRejected, String isSampled, String isAggregated) {
        JobID = jobID;
        Pkg_Level = pkg_Level;
        if (nextLevel_UIDCode==null)
            NextLevel_UIDCode="";
        else
            NextLevel_UIDCode = nextLevel_UIDCode;
        this.UIDCode = UIDCode;
        this.isPrinted = isPrinted;
        this.isInspected = isInspected;
        this.isRejected = isRejected;
        this.isSampled = isSampled;
        this.isAggregated = isAggregated;
    }

    public reportMapClass(String jobID, String pkg_Level, String UIDCode, String isPrinted, String isInspected, String isRejected, String isSampled, String isAggregated) {
        JobID = jobID;
        Pkg_Level = pkg_Level;
        this.UIDCode = UIDCode;
        this.isPrinted = isPrinted;
        this.isInspected = isInspected;
        this.isRejected = isRejected;
        this.isSampled = isSampled;
        this.isAggregated = isAggregated;
    }

    String JobID, Pkg_Level, NextLevel_UIDCode, UIDCode, isPrinted;

    public void setIsInspected(String isInspected) {
        this.isInspected = isInspected;
    }

    public String getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(String isRejected) {
        this.isRejected = isRejected;
    }

    public String getIsSampled() {
        return isSampled;
    }

    public void setIsSampled(String isSampled) {
        this.isSampled = isSampled;
    }

    public String getIsAggregated() {
        return isAggregated;
    }

    public void setIsAggregated(String isAggregated) {
        this.isAggregated = isAggregated;
    }

    String isInspected, isRejected, isSampled, isAggregated;

    public String getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(String isPrinted) {
        this.isPrinted = isPrinted;
    }

    public String getIsInspected() {
        return isInspected;
    }



}
