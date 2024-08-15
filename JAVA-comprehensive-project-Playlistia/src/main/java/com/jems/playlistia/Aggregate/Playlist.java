package com.jems.playlistia.Aggregate;

import java.io.Serializable;
import java.util.Objects;

public class Playlist implements Serializable {
    private int playlistNo; // 플레이리스트 번호
    private String name;    // 플레이리스트 이름
    private int totalNum;   // 곡 수
    private int totalDuration;  // 총 시간

    public Playlist() {
    }

    public Playlist(String name, int totalNum, int totalDuration) {
        this.name = name;
        this.totalNum = totalNum;
        this.totalDuration = totalDuration;
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
        return playlistNo == playlist.playlistNo && totalNum == playlist.totalNum && totalDuration == playlist.totalDuration && Objects.equals(name, playlist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistNo, name, totalNum, totalDuration);
    }
}
