public class Node {
    private String task; 
    private String details;
    private String date;  
    private Node next = null;
    public Node(String task, String details, String date) {
        this.task = task;
        this.details = details;
        this.date = date;
    }
    public String getTask() { return task; }
    public String getDetails() { return details; }
    public String getDate() { return date; }
    public Node getNext() { return next; }
    public void setNext(Node next) { this.next = next; }
}
