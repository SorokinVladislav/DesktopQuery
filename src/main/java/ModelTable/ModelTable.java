package ModelTable;

public class ModelTable {

    String JID,JobName, JobStatus, DataTransferStage;

    public ModelTable(String JID, String jobName, String jobStatus, String dataTransferStage) {
        this.JID = JID;
        JobName = jobName;
        JobStatus = jobStatus;
        DataTransferStage = dataTransferStage;
    }

    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getJobStatus() {
        return JobStatus;
    }

    public void setJobStatus(String jobStatus) {
        JobStatus = jobStatus;
    }

    public String getDataTransferStage() {
        return DataTransferStage;
    }

    public void setDataTransferStage(String dataTransferStage) {
        DataTransferStage = dataTransferStage;
    }




}
