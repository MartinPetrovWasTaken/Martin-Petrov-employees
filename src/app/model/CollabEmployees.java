package app.model;

public class CollabEmployees {
    private Long firstEmployeeId;
    private Long secondEmployeeId;
    private long days;

    public CollabEmployees(Long firstEmployeeId, Long secondEmployeeId, long days) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.days = days;
    }

    public Long getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(Long firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public Long getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(Long secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public void addDays(long days){
        this.days += days;
    }
}
