public class nodeSong {
    String title;
    String artist;
    String duration;
    nodeSong next;

    public nodeSong(String title, String artist, String duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null;
    }
}