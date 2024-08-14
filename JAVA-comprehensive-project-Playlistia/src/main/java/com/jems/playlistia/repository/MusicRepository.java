package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Music;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MusicRepository {
    private final ArrayList<Music> musicList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/jems/playlistia/db/Music.dat";


    public ArrayList<Music> selectAllMusic() {
        return musicList;
    }

    public MusicRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Music> music = new ArrayList<>();
            music.add(new Music("Dynamite", "Cause I-I-I'm in the stars tonight...",
                    new String[] {"Jessica Agombar", "David Stewart"},
                    new String[] {"Jessica Agombar", "David Stewart"},
                    "Pop", "BE", "BTS", 199));

            music.add(new Music("사랑에 연습이 있었다면", "사랑에 연습이 있었다면...",
                    new String[] {"Jukjae"},
                    new String[] {"Jukjae"},
                    "Ballad", "이해", "Jukjae", 253));

            music.add(new Music("Shape of You", "The club isn't the best place to find a lover...",
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    "Pop", "÷ (Divide)", "Ed Sheeran", 233));

            saveMusics(file, music);    // 파일에 music 객체들을 저장
        }
        loadMusic(file);
    }

    private void saveMusics(File file, ArrayList<Music> music) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Music mu : music) {
                oos.writeObject(mu);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMusic(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                musicList.add((Music) ois.readObject());  // Object -> User 타입으로 형 변환
            }

        } catch (EOFException e) {  // 파일의 끝을 읽으면 곡 정보 모두 로드 완료
            System.out.println("노래 목록을 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
