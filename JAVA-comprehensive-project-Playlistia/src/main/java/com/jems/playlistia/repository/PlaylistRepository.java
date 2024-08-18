package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.stream.MyObjectOutputStream;

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

    public int addPlaylistList(Playlist playlist) {
        System.out.println("addPlaylistList 실행: ");
        int result  = 0;

        Playlist currPlaylist = findPlaylistByNo(playlist.getPlaylistNo());



        if (currPlaylist != null) {
            // 기존 플레이리스트가 있으면 음악 추가
            currPlaylist.getMusicList().addAll(playlist.getMusicList());
            currPlaylist.setTotalNum(currPlaylist.getTotalNum() + playlist.getMusicList().size());
            currPlaylist.setTotalDuration(currPlaylist.getTotalDuration() + playlist.getMusicList().stream().mapToInt(Music::getDuration).sum());
        } else {
            // 플레이리스트가 없으면 새 플레이리스트 추가
            playlistList.add(playlist);
        }

        try (MyObjectOutputStream moos = new MyObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            moos.writeObject(playlist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        savePlaylists(new File(FILE_PATH), playlistList);

        return 1;
    }


}
