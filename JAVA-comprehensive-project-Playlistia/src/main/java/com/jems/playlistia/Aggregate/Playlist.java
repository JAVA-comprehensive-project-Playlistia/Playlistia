package com.jems.playlistia.Aggregate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Playlist implements Serializable {
    private int playlistNo; // 플레이리스트 번호
    private String name;    // 플레이리스트 이름
    private int totalNum;   // 곡 수
    private int totalDuration;  // 총 시간
    private ArrayList<Music> musicList; // 플레이리스트에 들어있는 음악 목록

    public Playlist() {
    }

    public Playlist(int playlistNo, String name, int totalNum, int totalDuration) {
        this.playlistNo = playlistNo;
        this.name = name;
        this.totalNum = totalNum;
        this.totalDuration = totalDuration;
    }

    public Playlist(int playlistNo, String name, int totalNum, int totalDuration, ArrayList<Music> musicList) {
        this.playlistNo = playlistNo;
        this.name = name;
        this.totalNum = totalNum;
        this.totalDuration = totalDuration;
        this.musicList = musicList;
    }

    public int getPlaylistNo() {
        return playlistNo;
    }

    public void setPlaylistNo(int playlistNo) {
        this.playlistNo = playlistNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(ArrayList<Music> musicList) {
        this.musicList = musicList;
    }

    // 이것도 나중에 예쁘게 바꾸기!!


    @Override
    public String toString() {
        return "Playlist{" +
                "playlistNo=" + playlistNo +
                ", name='" + name + '\'' +
                ", totalNum=" + totalNum +
                ", totalDuration=" + totalDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return playlistNo == playlist.playlistNo && totalNum == playlist.totalNum && totalDuration == playlist.totalDuration && Objects.equals(name, playlist.name) && Objects.equals(musicList, playlist.musicList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistNo, name, totalNum, totalDuration, musicList);
    }


    public void addMusic(Music music) {
        if (musicList == null) {
            musicList = new ArrayList<>();
        }
        musicList.add(music);
        totalDuration++;
        totalDuration += music.getDuration();
        System.out.println("totlaDuration: " + totalDuration);
    }
}
