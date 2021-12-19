package video;

public class Main {
    public static void main(String[] args) {
        Video video1 = new Video("TituloVideo", Category.CSGO, "www.videos1.com");
        Video video2 = new Video("TituloVideo2", Category.CSGO, "www.videos2.com");
        Videos list = new Videos();
        list.listVideos.add(video1);
        list.listVideos.add(video2);
        list.showVideosByCategory(Category.CSGO);
    }
}
