import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Perangkat {

    public static void updateData() throws IOException {
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        System.out.println("DAFTAR JURNAL");
        tampilkanData();

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor jurnal yang ingin dirubah: ");
        int updateNum = terminalInput.nextInt();


        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;

            StringTokenizer st = new StringTokenizer(data,",");

            if (updateNum == entryCounts){
                System.out.println("\nData yang ingin anda rubah adalah:");
                System.out.println("---------------------------------------");
                System.out.println("Referensi           : " + st.nextToken());
                System.out.println("Tahun               : " + st.nextToken());
                System.out.println("Mahasiswa           : " + st.nextToken());
                System.out.println("Dosen Jembimbing    : " + st.nextToken());
                System.out.println("Judul Jurnal        : " + st.nextToken());



                String[] fieldData = {"tahun","mahasiswa","Dosen Pembimbing","Judul Jurnal"};
                String[] tempData = new String[4];

                st = new StringTokenizer(data,",");
                String originalData = st.nextToken();

                for(int i=0; i < fieldData.length ; i++) {
                    boolean isUpdate = Tambahan.getYesorNo("apakah anda ingin merubah " + fieldData[i]);
                    originalData = st.nextToken();
                    if (isUpdate){

                        if (fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("masukan tahun penulisan, format=(YYYY): ");
                            tempData[i] = Tambahan.ambilTahun();
                        } else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + fieldData[i] + " baru: ");
                            tempData[i] = terminalInput.nextLine();
                        }

                    } else {
                        tempData[i] = originalData;
                    }
                }

                st = new StringTokenizer(data,",");
                st.nextToken();
                System.out.println("\nData baru anda adalah ");
                System.out.println("---------------------------------------");
                System.out.println("Tahun               : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Mahasiswa           : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Dosen Pembimbing    : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Judul               : " + st.nextToken() + " --> " + tempData[3]);


                boolean isUpdate = Tambahan.getYesorNo("apakah anda yakin ingin merubah data tersebut");

                if (isUpdate){

                    boolean isExist = Tambahan.cekDataDiDatabase(tempData,false);

                    if(isExist){
                        System.err.println("data Jurnal sudah ada di database, proses update dibatalkan, \nsilahkan hapus terlebih dahulu");
                        bufferedOutput.write(data);

                    } else {

                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        long nomorEntry = Tambahan.ambilEntryPerTahun(penulis, tahun) + 1;

                        String punulisTanpaSpasi = penulis.replaceAll("\\s+","");
                        String primaryKey = punulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;

                        bufferedOutput.write(primaryKey + "," + tahun + ","+ penulis +"," + penerbit + ","+judul);
                    }
                } else {
                    bufferedOutput.write(data);
                }
            } else {
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();

            data = bufferedInput.readLine();
        }

        bufferedOutput.flush();

        database.delete();
        tempDB.renameTo(database);

    }

    public static void deleteData() throws  IOException{

        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        System.out.println("List Buku");
        tampilkanData();

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor jurnal yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        boolean isFound = false;
        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while (data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data,",");

            if (deleteNum == entryCounts){
                System.out.println("\nData yang ingin anda hapus adalah:");
                System.out.println("-----------------------------------");
                System.out.println("Referensi         : " + st.nextToken());
                System.out.println("Tahun             : " + st.nextToken());
                System.out.println("Mahasiswa         : " + st.nextToken());
                System.out.println("Dosen Pembimbing  : " + st.nextToken());
                System.out.println("Judul Jurnal      : " + st.nextToken());

                isDelete = Tambahan.getYesorNo("Apakah anda yakin akan menghapus?");
                isFound = true;
            }

            if(isDelete){
                System.out.println("Data berhasil dihapus");
            } else {
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }

        if(!isFound){
            System.err.println("Jurnal tidak ditemukan");
        }

        bufferedOutput.flush();
        database.delete();
        tempDB.renameTo(database);

    }

    public static void tampilkanData() throws IOException{

        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }


        System.out.println("\n| No |\tTahun |\tNama Mahasiswa       |\tDosen Pembimbing      |\tJudul Jurnal");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s ", stringToken.nextToken());
            System.out.printf("|\t%-20s  ", stringToken.nextToken());
            System.out.printf("|\t%s  ", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    public static void cariData() throws IOException{


        try {
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }


        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Cari jurnal atau skripsi\n Masukkan kata kunci [nama mahasiswa/dospem/judul/tahun]: ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");

        Tambahan.cekDataDiDatabase(keywords,true);

    }

    public static void tambahData() throws IOException{


        FileWriter fileOutput = new FileWriter("database.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);


        Scanner terminalInput = new Scanner(System.in);
        String mahasiswa, judul, dospem, tahun;

        System.out.print("Masukan Nama Mahasiswa                : ");
        mahasiswa = terminalInput.nextLine();
        System.out.print("Masukan Judul Jurnal                  : ");
        judul = terminalInput.nextLine();
        System.out.print("Masukan Nama Dosen Pembimbing         : ");
        dospem = terminalInput.nextLine();
        System.out.print("Masukan Tahun Penulisan, format=(YYYY): ");
        tahun = Tambahan.ambilTahun();


        String[] keywords = {tahun+","+mahasiswa+","+dospem+","+judul};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Tambahan.cekDataDiDatabase(keywords,false);

        if (!isExist){
            System.out.println(Tambahan.ambilEntryPerTahun(mahasiswa, tahun));
            long nomorEntry = Tambahan.ambilEntryPerTahun(mahasiswa, tahun) + 1;

            String punulisTanpaSpasi = mahasiswa.replaceAll("\\s+","");
            String primaryKey = punulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
            System.out.println("\nData yang akan anda masukan adalah");
            System.out.println("----------------------------------------");
            System.out.println("primary key       : " + primaryKey);
            System.out.println("tahun penulisan   : " + tahun);
            System.out.println("Mahasiswa         : " + mahasiswa);
            System.out.println("Judul Jurnal      : " + judul);
            System.out.println("Dosen Pembimbing  : " + dospem);

            boolean isTambah = Tambahan.getYesorNo("Apakah akan ingin menambah data tersebut? ");

            if(isTambah){
                bufferOutput.write(primaryKey + "," + tahun + ","+ mahasiswa +"," + dospem + ","+judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }

        } else {
            System.out.println("Jurnal yang anda akan masukan sudah tersedia di database dengan data berikut:");
            Tambahan.cekDataDiDatabase(keywords,true);
        }


        bufferOutput.close();
    }

}
