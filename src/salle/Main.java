package salle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ===== 1) Créer 3 prestations =====
        Prestation sauna = new Prestation("SAUNA", "Accès sauna", 5);
        Prestation coach = new Prestation("COACH", "Coach personnel", 25);
        Prestation serviette = new Prestation("SERVIETTE", "Location serviette", 2);

        System.out.println("=== Prestations ===");
        System.out.println(sauna);
        System.out.println(coach);
        System.out.println(serviette);

        // ===== 2) Créer 3 séances (dates futures) =====
        Seance s1 = new Seance(1, "BodyPump", LocalDateTime.of(2026, 3, 10, 10, 0), 20);
        Seance s2 = new Seance(2, "Yoga", LocalDateTime.of(2026, 3, 12, 14, 30), 15);
        Seance s3 = new Seance(3, "CrossFit", LocalDateTime.of(2026, 3, 15, 18, 0), 25);

        System.out.println("\n=== Séances ===");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        // ===== 3) Créer la salle de sport =====
        SalleDeSport salle = new SalleDeSport();
        salle.ajouterSeance(s1);
        salle.ajouterSeance(s2);
        salle.ajouterSeance(s3);

        // ===== 4) Créer 2 adhérents =====
        Abonnement aboBasic = new AbonnementBasic("BAS-001", LocalDate.of(2026, 1, 1), 12, 29.99);
        Adherent adherent1 = new Adherent(1, "Alice Dupont", aboBasic);

        Abonnement aboPremium = new AbonnementPremium("PRE-001", LocalDate.of(2026, 2, 1), 6, 59.99, 5);
        Adherent adherent2 = new Adherent(2, "Bob Martin", aboPremium);

        salle.ajouterAdherent(adherent1);
        salle.ajouterAdherent(adherent2);

        System.out.println("\n=== Adhérents et leur abonnement ===");
        System.out.println(adherent1);
        System.out.println(adherent2);

        // ===== 5) Créer des réservations avec prestations =====
        // Alice réserve BodyPump + serviette
        Reservation r1 = adherent1.reserver(s1);
        r1.ajouterPrestation(serviette);

        // Alice réserve Yoga + sauna + serviette
        Reservation r2 = adherent1.reserver(s2);
        r2.ajouterPrestation(sauna);
        r2.ajouterPrestation(serviette);

        // Bob réserve CrossFit + coach + sauna
        Reservation r3 = adherent2.reserver(s3);
        r3.ajouterPrestation(coach);
        r3.ajouterPrestation(sauna);

        // Bob réserve Yoga (puis l'annule)
        Reservation r4 = adherent2.reserver(s2);
        r4.ajouterPrestation(serviette);
        r4.annuler();

        System.out.println("\n=== Réservations ===");
        System.out.println("Alice :");
        for (Reservation r : adherent1.getReservations()) {
            System.out.println("  " + r);
        }
        System.out.println("Bob :");
        for (Reservation r : adherent2.getReservations()) {
            System.out.println("  " + r);
        }

        // ===== 6) Afficher les réservations futures d'Alice =====
        System.out.println("\n=== Réservations futures d'Alice ===");
        List<Reservation> futuresAlice = adherent1.reservationsFutures();
        for (Reservation r : futuresAlice) {
            System.out.println("  " + r);
        }

        // ===== 7) Adhérents ayant accès sauna (polymorphisme) =====
        System.out.println("\n=== Adhérents avec accès sauna ===");
        List<Adherent> avecSauna = salle.adherentsAvecSauna();
        for (Adherent a : avecSauna) {
            System.out.println("  " + a.getNom());
        }

        // ===== 8) Chiffre d'affaires prestations =====
        System.out.println("\n=== Chiffre d'affaires prestations ===");
        System.out.println("Total : " + salle.chiffreAffairesPrestations() + " EUR");

        // ===== 9) Dépenses par adhérent =====
        System.out.println("\n=== Dépenses totales par adhérent ===");
        System.out.println("Alice : " + adherent1.depensesTotales() + " EUR");
        System.out.println("Bob : " + adherent2.depensesTotales() + " EUR");

        // ===== 10) Test exception : chercher un adhérent inexistant =====
        System.out.println("\n=== Test exception : adhérent introuvable ===");
        try {
            Adherent trouve = salle.trouverAdherent(99);
            System.out.println(trouve);
        } catch (AdherentIntrouvableException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }

        // ===== 11) Test trouverAdherent qui existe =====
        try {
            Adherent trouve = salle.trouverAdherent(2);
            System.out.println("Adhérent trouvé : " + trouve.getNom());
        } catch (AdherentIntrouvableException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
