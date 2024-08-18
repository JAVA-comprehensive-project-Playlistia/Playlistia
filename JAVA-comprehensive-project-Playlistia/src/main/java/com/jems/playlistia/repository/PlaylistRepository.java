package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;

import java.io.*;
import java.util.ArrayList;


public class PlaylistRepository {


    private final ArrayList<Playlist> playlistList = new ArrayList<>();
    private final MusicRepository musicRepository = new MusicRepository();

    private static final String FILE_PATH ="src/main/java/com/jems/playlistia/db/Playlist.dat";


    public PlaylistRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Playlist> playlists = new ArrayList<>();

            ArrayList<Music> musicList1 = new ArrayList<>();
            ArrayList<Music> musicList2 = new ArrayList<>();

            musicList1.add(new Music(1, "Happy", "그런 날이 있을까요 마냥 좋은 그런 날요...", new String[]{"Day6"},
                    new String[]{"Day6"}, "soft Rock", "Album1", "Day6", 200));
            playlists.add(new Playlist(1, "등교", 0, 0, musicList1));

            musicList2.add(new Music(1, "parachute", "Da-da, da-da-da-da, da-da, da-da-da-da, da-da", new String[]{"Day6"},
                    new String[]{"John K"}, "pop", "Album1", "John K", 150));
            playlists.add(new Playlist(2, "코딩할 때 듣는 음악", 0, 0, musicList2));

            savePlaylists(file, playlists);
        } else {
            loadPlaylists(file);
        }
    }

    public void savePlaylists(File file, ArrayList<Playlist> playlist) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Playlist pl : playlist) {
                oos.writeObject(pl);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPlaylists(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                playlistList.add((Playlist) ois.readObject());  // Object -> Music 타입으로 형 변환
            }

        } catch (EOFException e) {  // 파일의 끝을 읽으면 곡 정보 모두 로드 완료
            System.out.println("노래 목록을 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // PlayListRepository에 저장된 모든 Playlist 객체를 반환하기 위한 메소드
    public ArrayList<Playlist> selectAllPlaylist() {
        return playlistList;
    }


    // 특정 노래의 번호에 해당하는 Music 객체를 반환하는 메소드
    public Playlist findPlaylistByNo(int playlistNo) {
        // 플리가 존재하지 않을 경우 NullPointerException 발생 가능<== 이거 처리해야 함
        for (Playlist pl : playlistList) {
            // 사용자가 입력한 플리 번호와 일치하는 Playlist 객체를 반환
            if (pl.getPlaylistNo() == playlistNo) {
                return pl;
            }
        }
        return null;    // 해당 번호의 노래가 없을 경우 null 반환
    }

    // 플리 번호를 전달받아, 해당 플리에 저장된 Music ArrayList 배열을 반환하기 위한 메소드
    public ArrayList<Music> selectedAllMusicInPlaylist(int playlistNo) {
        Playlist playlist = findPlaylistByNo(playlistNo);

        if (playlist != null) {
            return playlist.getMusicList(); // 플리의 musicList 반환
        }
        return new ArrayList<>(); // 플리가 없을 경우 일단 빈 리스트 반환...
    }

    // 플레이리스트 내용 디버깅용 메소드
    public void printPlaylists() {
        for (Playlist playlist : playlistList) {
            System.out.println("Playlist No: " + playlist.getPlaylistNo());
            System.out.println("Playlist Name: " + playlist.getName());
            System.out.println("Music List: ");
            for (Music music : playlist.getMusicList()) {
                System.out.println("  - " + music.getName() + " by " + music.getSinger());
            }
            System.out.println();
        }
    }

    public void addMusicToPlaylist(Music music, int playlistNo) {

        Playlist playlist = findPlaylistByNo(playlistNo);
        if (playlist == null) {
            System.out.println("해당 번호의 플레이리스트가 없습니다.");
            return;
        }

        // 파일에 추가
        playlist.addMusic(music);
        System.out.println(music.getName() + " 이(가)" + playlist.getName() + " 에 추가되었습니다.");


//        ArrayList<Playlist> playlists = selectAllPlaylist();
//        File file = new File((FILE_PATH));
//        savePlaylists(file, playlists);

        System.out.println("저장 후 플레이리스트: ");
        printPlaylists();
        // 프로그램 종료 후 재실행해야 플레이리스트에 추가된 음악이 보이는 문제...

        //플레이리스트가 업데이트 된 playlist 객체를 참조하는 지 확인
        savePlaylists(new File(FILE_PATH), selectAllPlaylist());

    }

}
