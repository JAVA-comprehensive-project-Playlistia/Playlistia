package com.jems.playlistia.service;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.repository.MusicRepository;

import java.util.ArrayList;

public class MusicService {

    private final MusicRepository musicRepository = new MusicRepository();
    public void findAllMusic() {
        ArrayList<Music> findMusic = musicRepository.selectAllMusic();

        for (Music music : findMusic) {
            System.out.println("music = " + music);
        }
    }
}
