import java.util.Scanner;

class Lettera {
    private char carattere;

    public Lettera(char carattere) {
        this.carattere = carattere;
    }

    public char getCarattere() {
        return carattere;
    }

    public void setCarattere(char carattere) {
        this.carattere = carattere;
    }

    public boolean isStampabile() {
        return carattere >= 32 && carattere <= 126;
    }

    public void trasla(int salto, String direzione) {
        if (!isStampabile()) {
            return;
        }

        int min = 32;
        int max = 126;
        int range = max - min + 1;

        int effettivoSalto = salto;
        if (direzione.equals("-")) {
            effettivoSalto = -salto;
        }

        int codiceCorrente = (int) carattere;
        int nuovoCodice = ((codiceCorrente - min + effettivoSalto) % range + range) % range + min;

        this.carattere = (char) nuovoCodice;
    }
}

class VigenereApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CIFRARIO DI VIGENÈRE SU TABELLA ASCII (32-126) ===");

        // Acquisizione e validazione della chiave letteraria
        String chiave = "";
        while (true) {
            System.out.print("Inserisci la chiave letteraria (solo caratteri ASCII da 32 a 126): ");
            chiave = scanner.nextLine();

            if (chiave.isEmpty()) {
                System.out.println("Errore: La chiave non può essere vuota.");
                continue;
            }

            boolean chiaveValida = true;
            for (int i = 0; i < chiave.length(); i++) {
                char c = chiave.charAt(i);
                if (c < 32 || c > 126) {
                    chiaveValida = false;
                    break;
                }
            }

            if (chiaveValida) {
                break;
            } else {
                System.out.println("Errore: La chiave contiene caratteri non validi (sono esclusi i codici 0-31 e 127).");
            }
        }

        int operazione = 0;
        while (true) {
            System.out.print("Scegli l'operazione (1 = Cifratura, 2 = Decifratura): ");
            if (scanner.hasNextInt()) {
                operazione = scanner.nextInt();
                if (operazione == 1 || operazione == 2) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Errore: Scelta non valida. Inserisci 1 per cifrare o 2 per decifrare.");
        }

        scanner.nextLine(); // Pulizia buffer

        System.out.print("Inserisci il testo: ");
        String testo = scanner.nextLine();

        StringBuilder risultato = new StringBuilder();
        int indiceChiave = 0;
        String direzione = (operazione == 1) ? "+" : "-";

        for (int i = 0; i < testo.length(); i++) {
            Lettera lettera = new Lettera(testo.charAt(i));

            if (lettera.isStampabile()) {
                // Prende il carattere corrente della chiave e calcola il salto (offset da 32)
                char carattereChiave = chiave.charAt(indiceChiave % chiave.length());
                int salto = carattereChiave - 32;

                lettera.trasla(salto, direzione);
                indiceChiave++;
            }

            risultato.append(lettera.getCarattere());
        }

        String nomeOperazione = (operazione == 1) ? "Testo cifrato" : "Testo decifrato";
        System.out.println("\n----------------------------------------");
        System.out.println(nomeOperazione + ": " + risultato.toString());
        System.out.println("----------------------------------------");

        scanner.close();
    }
}