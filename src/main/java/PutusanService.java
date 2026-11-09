import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class PutusanService {
    private DynamicArray<Putusan> repository;
    private CustomStack<String> auditTrail;

    public PutusanService() {
        this.repository = new DynamicArray<>();
        this.auditTrail = new CustomStack<>();
    }

    public void loadCsv(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (parts.length >= 16) {
                    try {
                        String caseId = parts[0].trim();
                        String nomorPerkara = parts[1].trim();
                        String pengadilan = parts[2].trim();
                        LocalDate tanggal = LocalDate.parse(parts[3].trim());
                        String terdakwa = parts[4].trim();
                        String jenis = parts[5].trim();
                        double berat = Double.parseDouble(parts[6].trim());
                        String pasalDak = parts[7].trim();
                        String pasalTerb = parts[8].trim();
                        int tuntutan = Integer.parseInt(parts[9].trim());
                        int vonis = Integer.parseInt(parts[10].trim());
                        long denda = Long.parseLong(parts[11].trim());
                        String bb = parts[12].trim().replace("\"", "");
                        String memb = parts[13].trim().replace("\"", "");
                        String ring = parts[14].trim().replace("\"", "");
                        String pages = parts[15].trim();

                        Putusan p = new Putusan(caseId, nomorPerkara, pengadilan, tanggal, terdakwa,
                                jenis, berat, pasalDak, pasalTerb, tuntutan, vonis, denda, bb, memb, ring, pages);
                        repository.add(p);
                        count++;
                    } catch (Exception e) {
                        // Skip invalid rows gracefully
                    }
                }
            }
            auditTrail.push("Loaded " + count + " records from " + filePath);
        }
    }

    public DynamicArray<Putusan> getRepository() {
        return repository;
    }

    public Putusan searchByCaseIdOrNumber(String keyword) {
        int steps = 0;
        for (int i = 0; i < repository.size(); i++) {
            steps++;
            Putusan p = repository.get(i);
            if (p.getCaseId().equalsIgnoreCase(keyword) || p.getNomorPerkara().toLowerCase().contains(keyword.toLowerCase())) {
                auditTrail.push("Searched: " + keyword + " (Found in " + steps + " steps)");
                return p;
            }
        }
        auditTrail.push("Searched: " + keyword + " (Not found, steps: " + steps + ")");
        return null;
    }

    public void addPutusan(Putusan p) {
        repository.add(p);
        auditTrail.push("Added putusan: " + p.getCaseId());
    }

    public Putusan removePutusan(int index) {
        Putusan removed = repository.remove(index);
        auditTrail.push("Removed putusan at index " + index + " (" + removed.getCaseId() + ")");
        return removed;
    }

    public int getAuditSize() {
        return auditTrail.size();
    }
}