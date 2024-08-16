package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Music;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MusicRepository {
    private final ArrayList<Music> musicList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/jems/playlistia/db/Music.dat";


    // MusicRepository에 저장된 모든 Music 객체를 반환하기 위한 메소드
    public ArrayList<Music> selectAllMusic() {
        return musicList;
    }

    public MusicRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Music> music = new ArrayList<>();
            music.add(new Music(1, "Dynamite", "Cause I-I-I'm in the stars tonight...",
                    new String[] {"Jessica Agombar", "David Stewart"},
                    new String[] {"Jessica Agombar", "David Stewart"},
                    "Pop", "BE", "BTS", 199));

            music.add(new Music(2, "사랑에 연습이 있었다면", "사랑에 연습이 있었다면...",
                    new String[] {"Jukjae"},
                    new String[] {"Jukjae"},
                    "Ballad", "이해", "Jukjae", 253));

            music.add(new Music(3, "Shape of You", "The club isn't the best place to find a lover...",
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    "Pop", "÷ (Divide)", "Ed Sheeran", 233));

            music.add(new Music(4, "봄날", "보고 싶다...",
                    new String[]{"RM", "Suga", "Adora", "Hitman Bang", "Arlissa Ruppert"},
                    new String[]{"Pdogg", "RM", "Suga", "Adora"},
                    "K-pop, Ballad", "YOU NEVER WALK ALONE", "BTS", 274));

            music.add(new Music(5, "Blinding Lights", "I've been tryna call...",
                    new String[]{"Abel Tesfaye", "Ahmad Balshe", "Jason Quenneville", "Max Martin", "Oscar Holter"},
                    new String[]{"Max Martin", "Oscar Holter", "The Weeknd"},
                    "Synthwave, Pop", "After Hours", "The Weeknd", 200));

            music.add(new Music(6, "가을 아침", "산촌에...",
                    new String[]{"Lee Mi-ja"},
                    new String[]{"Lee Mi-ja"},
                    "Folk", "꽃, 바람 그리고 당신", "IU", 213));

            music.add(new Music(7, "Bad Guy", "So you're a tough guy, like it really rough guy...",
                    new String[]{"Billie Eilish", "Finneas O'Connell"},
                    new String[]{"Finneas O'Connell"},
                    "Electropop", "When We All Fall Asleep, Where Do We Go?", "Billie Eilish", 194));

            music.add(new Music(8, "Bad Guy", "So you're a tough guy, like it really rough guy...",
                    new String[]{"Billie Eilish", "Finneas O'Connell"},
                    new String[]{"Finneas O'Connell"},
                    "Electropop", "When We All Fall Asleep, Where Do We Go?", "Billie Eilish", 194));

            music.add(new Music(9, "Celebrity", "Oh hey, ya 어두운 밤이 날 가두기 전에...",
                    new String[]{"IU", "Lee Chan-hyuk"},
                    new String[]{"Ryan S. Jhun", "Jeong Seung-hwan", "KIM SHIN HYE"},
                    "K-pop", "LILAC", "IU", 210));

            music.add(new Music(10, "Someone Like You", "I heard that you're settled down...",
                    new String[]{"Adele", "Dan Wilson"},
                    new String[]{"Adele", "Dan Wilson"},
                    "Soul", "21", "Adele", 285));

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
                musicList.add((Music) ois.readObject());  // Object -> Music 타입으로 형 변환
            }

        } catch (EOFException e) {  // 파일의 끝을 읽으면 곡 정보 모두 로드 완료
            System.out.println("노래 목록을 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 특정 노래의 번호에 해당하는 Music 객체를 반환하는 메소드
    // MusicRepo
    public Music findMusicByNo(int musicNo) {
        for (Music music : musicList) {
            // 노래 번호와 일치하는 Music 객체를 반환
            if (music.getMusicNo() == musicNo) {
                return music;
            }
        }
        return null;    // 해당 번호의 노래가 없을 경우 null 반환
    }

    public void originalMusicList() {
        ArrayList<Music> musics = new ArrayList<>(musicList);

        suffleMusic();

        musicList.clear();
        musics.addAll(musicList);
    }

    public void suffleMusic() {
        Collections.shuffle(musicList);
    }
}
