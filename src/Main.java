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

        int lunghezzaChiave = 0;
        while (true) {
            System.out.print("Inserisci la lunghezza della chiave (maggiore di 0): ");
            if (scanner.hasNextInt()) {
                lunghezzaChiave = scanner.nextInt();
                if (lunghezzaChiave > 0) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Errore: La lunghezza deve essere un numero intero maggiore di 0.");
        }

        int[] chiave = new int[lunghezzaChiave];
        System.out.println("Inserisci i " + lunghezzaChiave + " numeri della chiave:");
        for (int i = 0; i < lunghezzaChiave; i++) {
            while (true) {
                System.out.print("  Numero " + (i + 1) + ": ");
                if (scanner.hasNextInt()) {
                    chiave[i] = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Errore: Inserisci un numero intero valido.");
                    scanner.next();
                }
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

        scanner.nextLine();

        System.out.print("Inserisci il testo: ");
        String testo = scanner.nextLine();

        StringBuilder risultato = new StringBuilder();
        int indiceChiave = 0;
        String direzione = (operazione == 1) ? "+" : "-";

        for (int i = 0; i < testo.length(); i++) {
            Lettera lettera = new Lettera(testo.charAt(i));

            if (lettera.isStampabile()) {
                int salto = chiave[indiceChiave % chiave.length];
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