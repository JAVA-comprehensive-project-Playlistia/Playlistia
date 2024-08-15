package com.jems.playlistia.repository;

import com.jems.playlistia.Aggregate.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PlaylistRepository {
    private final ArrayList<Playlist> playlistList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/jems/playlistia/db/Playlist.dat";

    public PlaylistRepository() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Playlist> playlist = new ArrayList<>();
            playlist.add(new Playlist("등교", 1, 199));

            savePlaylist(file, playlist);    // 파일에 플레이리스트 목록 객체들을 저장
        }
        loadPlaylist(file);
    }

    private void savePlaylist(File file, ArrayList<Playlist> playlist) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Playlist pl : playlist) {
                oos.writeObject(pl);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPlaylist(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                playlistList.add((Playlist) ois.readObject());  // Object -> Playlist 타입으로 형 변환
            }

        } catch (EOFException e) {  // 파일의 끝을 읽으면 곡 정보 모두 로드 완료
            System.out.println("플레이리스트 목록을 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Playlist> showAllPlaylistList() {
        return playlistList;
    }

    public Playlist selectPlaylistByNo(int no) {
        for(Playlist playlist : playlistList) {
            if(playlist.getPlaylistNo() == no) {
                return playlist;
            }
        }
        return null;
    }

    public int selectLastPlaylistNo() {

        Playlist lastPlaylist = playlistList.get(playlistList.size()-1);
        return lastPlaylist.getPlaylistNo();

    }

    public void selectPlaylist (Playlist playlist) {
        Scanner sc = new Scanner(System.in);
        System.out.print("플레이리스트를 선택하세요 : ");
        int chooseplaylistNo  = sc.nextInt();
        sc.nextLine();

//        if(chooseplaylistNo == playlist.getPlaylistNo()) {
//            selecteAllMusic
//        }

    }
}
