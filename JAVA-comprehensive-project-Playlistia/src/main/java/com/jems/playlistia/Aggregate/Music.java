package com.jems.playlistia.Aggregate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Music implements Serializable {
    private int musicNo; // 곡 번호
    private String name;    // 곡명
    private String lyrics;  // 가사
    private String[] lyricsWriter;  // 작사가
    private String[] composer;  // 작곡가
    private String genre;   // 장르
    private String albumName;   // 앨범명
    private String singer;  // 가사
    private int duration;   // 곡 시간

    public Music() {
    }

    public Music(int musicNo, String name, String lyrics, String[] lyricsWriter, String[] composer, String genre, String albumName, String singer, int duration) {
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

    @Override
    public String toString() {
        return "Music{" +
                "musicNo=" + musicNo +
                ", name='" + name + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", lyricsWriter=" + Arrays.toString(lyricsWriter) +
                ", composer=" + Arrays.toString(composer) +
                ", genre='" + genre + '\'' +
                ", albumName='" + albumName + '\'' +
                ", singer='" + singer + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return musicNo == music.musicNo && duration == music.duration && Objects.equals(name, music.name) && Objects.equals(lyrics, music.lyrics) && Arrays.equals(lyricsWriter, music.lyricsWriter) && Arrays.equals(composer, music.composer) && Objects.equals(genre, music.genre) && Objects.equals(albumName, music.albumName) && Objects.equals(singer, music.singer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(musicNo, name, lyrics, genre, albumName, singer, duration);
        result = 31 * result + Arrays.hashCode(lyricsWriter);
        result = 31 * result + Arrays.hashCode(composer);
        return result;
    }
}
