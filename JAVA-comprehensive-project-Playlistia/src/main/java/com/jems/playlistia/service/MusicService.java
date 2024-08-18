package com.jems.playlistia.service;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.Aggregate.Queue;
import com.jems.playlistia.repository.MusicRepository;
import com.jems.playlistia.repository.PlaylistRepository;
import com.jems.playlistia.repository.QueueRepository;

import java.util.ArrayList;

public class MusicService {

    private final MusicRepository musicRepository = new MusicRepository();
    private final QueueRepository queueRepository = new QueueRepository();
    private final PlaylistRepository playlistRepository = new PlaylistRepository();
    public void findAllMusic() {
        ArrayList<Music> findMusic = musicRepository.selectAllMusic();

        for (Music music : findMusic) {
            System.out.println("music = " + music);
        }
    }

    // 모든 플레이리스트를 보여주는 메소드
    public void findAllPlaylist() {
        ArrayList<Playlist> findPlaylist = playlistRepository.selectAllPlaylist();

        for (Playlist pl : findPlaylist) {
            System.out.println(" playlist = " + pl);
        }
    }

    // 특정 플레이리스트의 모든 음악을 보여주는 메소드
    public void findAllMusicInPlaylist(int playlistNo) {
        ArrayList<Music> musicList = playlistRepository.selectedAllMusicInPlaylist(playlistNo);
        // 플레이리스트의 모든 음악을 musicList 배열에 넣기

        if (musicList == null | musicList.isEmpty()) {
            System.out.println("선택하신 플레이리스트가 비어있습니다.");
        } else {
            for (Music music : musicList) {
                System.out.println("music = " + music);
            }
        }

    }


    public void showAllQueue() {
        // 재생목록(Queue)에 있는 모든 음악을 보여주는 메소드
        ArrayList<Queue> showQueue = queueRepository.selectAllQueueMusic();

        for (Queue queue : showQueue) {
            System.out.println("music = " + queue);
        }
    }

    // 작동은 하는데 노래 객체가 넘어가진 않음
    public void registQueue(int selectedMusicNo) {

        Queue queue = new Queue();

        Music music = musicRepository.findMusicByNo(selectedMusicNo);
        if (music == null) {
            System.out.println("해당 번호의 음악이 없습니다");
        }

        // 재생목록 번호 3번 설정
        int lastQueueNo = queueRepository.selectLastQueueNo();
        queue.setMusicNo(lastQueueNo + 1);


        // 사용자가 선택한 Music 객체 정보를 재생목록에 넣기
        queue.setName(music.getName());
        queue.setLyrics(music.getLyrics());
        queue.setLyricsWriter(music.getLyricsWriter());
        queue.setComposer(music.getComposer());
        queue.setGenre(music.getGenre());
        queue.setAlbumName(music.getAlbumName());
        queue.setSinger(music.getSinger());
        queue.setDuration(music.getDuration());

        // 현재 총 곡 수 + 1
        int totalNum = queueRepository.selectAllQueueMusic().size() + 1;
        int totalDuration = queueRepository.selectAllQueueMusic().stream()
                .mapToInt(Queue::getDuration)
                .sum() + music.getDuration();

        // queue에 music 객체를 추가하지 않았기 때문에 여기서 이미 null이 들어감
        System.out.println("queue " + queue);

        int result = queueRepository.addQueueList(queue);
        System.out.println("result: " + result);

        if(result == 1) {
            System.out.println(music.getName() + "를 재생목록에 추가했습니다.");
        } else {
            System.out.println("입력하신 노래 번호가 없습니다.");
        }
        System.out.println("registQueue 종료");


    }

    public void removeQueueMusic(int selectMusicNo) {
        int result = queueRepository.deleteQueueMusic(selectMusicNo);

        if(result == 1) {
            System.out.println("노래가 삭제되었습니다.");
        } else {
            System.out.println("입력하신 노래 번호가 없습니다.");
        }
    }

}
