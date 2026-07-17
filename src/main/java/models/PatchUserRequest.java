package models;

public class PatchUserRequest {

    private String job;

    public PatchUserRequest() {
    }

    public PatchUserRequest(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}