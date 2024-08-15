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

    public void registQueue(int musicNo) {

        Music music = new Music();
        Queue queue = new Queue();

        int lastQueueNo = queueRepository.selectLastQueueNo();
        queue.setMusicNo(lastQueueNo + 1);

        int result = queueRepository.addQueueList(queue);

        if(result == 1) {
            System.out.println(music.getName() + "를 재생목록에 추가했습니다.");
        } else {
            System.out.println("입력하신 노래 번호가 없습니다.");
        }

    }
}
