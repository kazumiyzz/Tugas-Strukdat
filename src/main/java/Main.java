public class Main {
    public static void main(String[] args) {
        System.out.println("=== APLIKASI PENGELOLAAN PUTUSAN PENGADILAN NARKOTIKA ===");
        System.out.println("Struktur Data Utama: DynamicArray<Putusan> (Tanpa Package)");
        System.out.println("Universitas Muhammadiyah Malang - Semester Ganjil 2026/2027");

        PutusanService service = new PutusanService();
        try {
            String csvPath = "data/putusan-50.csv";
            service.loadCsv(csvPath);
            System.out.println("[INFO] Berhasil memuat data. Total record dalam struktur utama: " + service.getRepository().size());

            // Test search
            Putusan found = service.searchByCaseIdOrNumber("P005");
            if (found != null) {
                System.out.println("[SEARCH RESULT] Ditemukan: " + found);
            }

            System.out.println("Audit trail size: " + service.getAuditSize());
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }
}