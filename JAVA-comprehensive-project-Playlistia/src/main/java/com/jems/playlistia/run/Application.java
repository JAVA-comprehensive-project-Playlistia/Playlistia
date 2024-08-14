package com.jems.playlistia.run;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.service.MusicService;

import java.util.Scanner;

public class Application {

    private static final MusicService musicService = new MusicService();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Playlistia =====");
            System.out.println("세상 모든 음악 여기 모여라! 플레이리스티아에 오신 것을 환영합니다.");
            System.out.println("1. 전체 음악 보기");
            System.out.println("2. 재생 목록 보기");
            System.out.println("3. 플레이리스트 보기");
            System.out.println("9. 플레이리스티아 나가기");
            System.out.print("메뉴 선택 : ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1: musicService.findAllMusic(); break;
                case 2: break;  // 1번과 유사하게
                case 3: break;  //
                case 9:
                    System.out.println("플레이리스티아를 종료합니다. 또 만나요!");
                    return;
                default:
                    System.out.println("메뉴 번호를 잘못 입력했습니다.");
                    break;
            }
        }

    }

    private static Music showPlaylist(Music selected) {
        Music selectedPlaylist = selected;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== 플레이리스트 ======");
            // 1. 캐롤송
            // 2. 등교음악
            // 3. 자기 전 음악
            System.out.println("플레이리스트를 선택하세요: ");
            String choosePlaylist = scanner.nextLine();
            System.out.println("");
            switch(choosePlaylist) {

            }
        }
    }
}
