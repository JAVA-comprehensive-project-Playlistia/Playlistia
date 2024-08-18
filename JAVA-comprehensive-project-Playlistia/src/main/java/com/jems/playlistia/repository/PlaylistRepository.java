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
                Playlist playlist = (Playlist) ois.readObject();
                playlistList.add(playlist);
            }
        } catch (EOFException e) {
            // 파일 끝에 도달한 경우
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

    // 새 플레이리스트 실행하는 메소드
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


    public int addPlaylistList(Playlist playlist) {
        System.out.println("addPlaylistList 실행");

        int result = 0;

        try {
            // 플레이리스트가 메모리에 있는지 확인
            Playlist currPlaylist = findPlaylistByNo(playlist.getPlaylistNo());

            // 새로 추가된 플레이리스트의 음악과 총 개수 및 총 시간 업데이트
            if (currPlaylist != null) {
                currPlaylist.getMusicList().addAll(playlist.getMusicList());
                currPlaylist.setTotalNum(currPlaylist.getTotalNum() + playlist.getMusicList().size());
                currPlaylist.setTotalDuration(currPlaylist.getTotalDuration() + playlist.getMusicList().stream().mapToInt(Music::getDuration).sum());
            } else {
                playlistList.add(playlist);
            }

            // 플레이리스트를 파일에 추가
            try (MyObjectOutputStream moos = new MyObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
                if (currPlaylist != null) {
                    moos.writeObject(currPlaylist);
                } else {
                    moos.writeObject(playlist);
                }
                result = 1; // 성공적으로 추가된 경우 1 반환
            } catch (IOException e) {
                throw new RuntimeException("파일에 플레이리스트를 추가하는 중 오류 발생", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("플레이리스트를 추가하는 중 오류 발생", e);
        }

        return result; // 성공 시 1 반환
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
