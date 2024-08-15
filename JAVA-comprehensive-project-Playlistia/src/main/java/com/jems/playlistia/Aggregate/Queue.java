package com.jems.playlistia.Aggregate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Queue implements Serializable {
    private int musicNo; // 곡 번호
    private String name;    // 곡명
    private String lyrics;  // 가사
    private String[] lyricsWriter;  // 작사가
    private String[] composer;  // 작곡가
    private String genre;   // 장르
    private String albumName;   // 앨범명
    private String singer;  // 가사
    private int duration;   // 곡 시간
    private int totalNum;   // 곡 수
    private int totalDuration;  // 총 시간

    public Queue() {
    }

    public Queue(int musicNo, String name, String lyrics, String[] lyricsWriter, String[] composer, String genre, String albumName, String singer, int duration) {
        this.musicNo = musicNo;
        this.name = name;
        this.lyrics = lyrics;
        this.lyricsWriter = lyricsWriter;
        this.composer = composer;
        this.genre = genre;
        this.albumName = albumName;
        this.singer = singer;
        this.duration = duration;
    }
    public Queue(int totalNum, int totalDuration) {
        this.totalNum = totalNum;
        this.totalDuration = totalDuration;
    }

    public int getMusicNo() {
        return musicNo;
    }

    public void setMusicNo(int musicNo) {
        this.musicNo = musicNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String[] getLyricsWriter() {
        return lyricsWriter;
    }

    public void setLyricsWriter(String[] lyricsWriter) {
        this.lyricsWriter = lyricsWriter;
    }

    public String[] getComposer() {
        return composer;
    }

    public void setComposer(String[] composer) {
        this.composer = composer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    @Override
    public String toString() {
        return "Queue{" +
                "musicNo=" + musicNo +
                ", name='" + name + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", lyricsWriter=" + Arrays.toString(lyricsWriter) +
                ", composer=" + Arrays.toString(composer) +
                ", genre='" + genre + '\'' +
                ", albumName='" + albumName + '\'' +
                ", singer='" + singer + '\'' +
                ", duration=" + duration +
                ", totalNum=" + totalNum +
                ", totalDuration=" + totalDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queue queue = (Queue) o;
        return musicNo == queue.musicNo && duration == queue.duration && totalNum == queue.totalNum && totalDuration == queue.totalDuration && Objects.equals(name, queue.name) && Objects.equals(lyrics, queue.lyrics) && Arrays.equals(lyricsWriter, queue.lyricsWriter) && Arrays.equals(composer, queue.composer) && Objects.equals(genre, queue.genre) && Objects.equals(albumName, queue.albumName) && Objects.equals(singer, queue.singer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(musicNo, name, lyrics, genre, albumName, singer, duration, totalNum, totalDuration);
        result = 31 * result + Arrays.hashCode(lyricsWriter);
        result = 31 * result + Arrays.hashCode(composer);
        return result;
    }
}
