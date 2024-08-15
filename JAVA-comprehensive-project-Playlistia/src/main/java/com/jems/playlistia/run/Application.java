package com.jems.playlistia.run;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Queue;
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
            System.out.println("3. 플레이리스트 목록 보기");
            System.out.println("9. 플레이리스티아 나가기");
            System.out.print("메뉴 선택 : ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1: musicService.findAllMusic(); break;
                case 2: musicService.showAllQueue(); break;  // 1번과 유사하게
                case 3: musicService.showAllPlaylist(); break;  //
                case 9:
                    System.out.println("플레이리스티아를 종료합니다. 또 만나요!");
                    return;
                default:
                    System.out.println("메뉴 번호를 잘못 입력했습니다.");
                    break;
            }
        }

    }

    // 원하는 노래 번호를 입력 받아 반환하는 메소드
    // 이후에도 노래 번호를 입력 받을 경우가 있을까봐 Application에 선언함... 있나?
    private static int chooseMusicNo() {

        Scanner sc = new Scanner(System.in);
        int selectedMusicNo = 0;
        System.out.println("=====원하는 노래 번호를 선택하세요.=====");
        System.out.print("노래 번호 : ");
        selectedMusicNo = sc.nextInt();
        return selectedMusicNo;
    }

    // 원하는 메뉴 번호를 입력 받아 반환하는 메소드(1)
    private static int showMusicMenu1() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 노래 재생");
        System.out.println("2. 재생목록에 추가");
        System.out.println("3. 플레이리스트에 추가");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }

    private static int showMusicMenu2() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 노래 멈춤");
        System.out.println("2. 나가기");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }


    // Music 객체를 받아 해당 노래의 이름 가져오기
    private static void playMusic(Music music) {

        Scanner sc = new Scanner(System.in);
        // 저장된 노래 번호
        System.out.println(music.getName() + " 를 재생 중 ...");

        while (true) {
            int menuNo = showMusicMenu2();   // 노래 재생 후 추가 메뉴 선택 받기

            if (menuNo == 1) {  // 1. 노래 멈춤
                System.out.println(music.getName() + " 의 재생이 멈춤");
                return; // 노래 멈추고 return 해서 메인 메뉴로
                // 노래 멈추고 다시 재생도 할 수 있게 하려면 어케 하지??

            } else if (menuNo == 2) {   // 2. 나가기
                return;

            } else {
                System.out.println(" 메뉴 번호를 잘못 입력했습니다.");
            }
        }

    }


}
