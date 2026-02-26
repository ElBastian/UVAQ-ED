public class nodeB {
    String imagePath;
    String songData;
    nodeB next;

    public nodeB(String imagePath) {
        this.imagePath = imagePath;
        this.next = null;
    }
}