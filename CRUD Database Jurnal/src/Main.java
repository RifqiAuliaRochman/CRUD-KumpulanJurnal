import java.io.IOException;
import java.util.Scanner;

// import CRUD library
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while (isLanjutkan) {
            Tambahan.clearScreen();
            System.out.println("\n==========================================================================================================================");
            System.out.println("                                              KUMPULAN JURNAL DAN SKRIPSI");
            System.out.println("==========================================================================================================================\n");
            System.out.println("1. Tampilkan Data");
            System.out.println("2. Cari Jurnal");
            System.out.println("3. Tambah Jurnal baru");
            System.out.println("4. Ubah data");
            System.out.println("5. Hapus data");

            System.out.print("\n\nPilih Menu: ");
            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n==========================================================================================================================");
                    System.out.println("                                               DAFTAR JURNAL DAN SKRIPSI");
                    System.out.println("==========================================================================================================================");
                    Perangkat.tampilkanData();
                    break;
                case "2":
                    System.out.println("\n==========================================================================================================================");
                    System.out.println("                                                     MENU PENCARIAN");
                    System.out.println("=========================================================================================================================");
                    Perangkat.cariData();
                    break;
                case "3":
                    System.out.println("\n==========================================================================================================================");
                    System.out.println("                                                     TAMBAH JURNAL");
                    System.out.println("==========================================================================================================================");
                    Perangkat.tambahData();
                    Perangkat.tampilkanData();
                    break;
                case "4":
                    System.out.println("\n==========================================================================================================================");
                    System.out.println("                                                       UBAH DATA");
                    System.out.println("==========================================================================================================================");
                    Perangkat.updateData();
                    break;
                case "5":
                    System.out.println("\n==========================================================================================================================");
                    System.out.println("                                                      HAPUS DATA");
                    System.out.println("==========================================================================================================================");
                    Perangkat.deleteData();
                    break;
                default:
                    System.err.println("\nMenu yang anda masukkan salah\nSilahkan pilih [1-5]");
            }

            isLanjutkan = Tambahan.getYesorNo("Apakah Anda ingin melanjutkan");
        }
    }

}