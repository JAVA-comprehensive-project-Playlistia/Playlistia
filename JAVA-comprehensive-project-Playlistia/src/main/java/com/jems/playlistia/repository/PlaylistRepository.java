package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.stream.MyObjectOutputStream;

import java.io.*;
import java.util.ArrayList;

public class PlaylistRepository {

    private final ArrayList<Playlist> playlistList = new ArrayList<>();

    private static final String FILE_PATH ="src/main/java/com/jems/playlistia/db/Playlist.dat";


    public PlaylistRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Playlist> playlists = new ArrayList<>();

            ArrayList<Music> musicList1 = new ArrayList<>();

//            playlists.add(new Playlist(1, "등교", 0, 0));


            musicList1.add(new Music(1, "Happy", "그런 날이 있을까요 마냥 좋은 그런 날요...", new String[]{"Day6"},
                    new String[]{"Day6"}, "soft Rock", "Album1", "Day6", 200));
            playlists.add(new Playlist(1, "등교", 0, 0, musicList1));

            savePlaylists(file, playlists);
        }
        loadPlaylists(file);

    }

    private void savePlaylists(File file, ArrayList<Playlist> playlist) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Playlist pl : playlist) {
                oos.writeObject(pl);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPlaylists(File file) {
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

    // 플레이리스트에 노래를 저장하는 메소드
//    public void addMusicToPlaylist(int playlistNo, Music music) {
//        // 사용자에게 입력 받은 노래 번호를 ArrayList에 넣기
//        int idx = playlistNo - 1;
//
//        Playlist playlist = playlistList.get(idx);
//        playlist.addMusic(music);
//        savePlaylists(new File(FILE_PATH), playlistList);   // Plyalist.dat 파일에 노래를 추가한 새로운 플레이리스트 저장
//    }

    // 특정 노래의 번호에 해당하는 Music 객체를 반환하는 메소드
    // MusicRepo
    public Playlist findPlaylistByNo(int playlistNo) {
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

    public int addPlaylist(Playlist playlist) {
        System.out.println("addPlaylist 실행");

        // 메모리에 새 플레이리스트 추가
        playlistList.add(playlist);

        // 파일에 새 플레이리스트 저장
        try (MyObjectOutputStream moos = new MyObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            moos.writeObject(playlist);
            return 1; // 성공적으로 추가된 경우 1 반환
        } catch (IOException e) {
            throw new RuntimeException("파일에 플레이리스트를 추가하는 중 오류 발생", e);
        }
    }

    public int deletePlaylist(int playlistNo) {
        Playlist playlistToRemove = findPlaylistByNo(playlistNo);
        if (playlistToRemove != null) {
            playlistList.remove(playlistToRemove);
            savePlaylists(new File(FILE_PATH), playlistList);
            return 1; // 삭제 성공
        }
        return 0; // 삭제할 플레이리스트를 찾을 수 없는 경우
    }
}
