package com.jems.playlistia.service;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.Aggregate.Queue;
import com.jems.playlistia.repository.MusicRepository;
import com.jems.playlistia.repository.PlaylistRepository;
import com.jems.playlistia.repository.QueueRepository;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void showAllQueue() {
        // 재생목록(Queue)에 있는 모든 음악을 보여주는 메소드
        ArrayList<Queue> showQueue = queueRepository.selectAllQueueMusic();

        for (Queue queue : showQueue) {
            System.out.println("music = " + queue);
        }
    }

    public void showAllPlaylist() {
        // 플레이리스트(Playlist)의 목록을 보여주는 메소드
        ArrayList<Playlist> showPlaylist = playlistRepository.showAllPlaylistList();

        for (Playlist playlist : showPlaylist) {
            System.out.println("music = " + playlist);
        }

    }
}
