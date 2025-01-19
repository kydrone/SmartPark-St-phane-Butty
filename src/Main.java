public class Main {
    public static void main(String[] args) {
        GestionClass.Parking parking = new GestionClass.Parking("SmartPark", "123 Rue Exemple", 10, 5);
        System.out.println("Bienvenue chez SmartPark !");
        System.out.println();
        System.out.println("Menu : ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Combien d'actions voulez-vous effectuer dans ce menu ? : ");
        int maxIterations = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < maxIterations; i++) {
            System.out.println("\nMenu :");
            System.out.println("1. Enregistrer une entrée");
            System.out.println("2. Enregistrer une sortie");
            System.out.println("3. Afficher les places disponibles");
            System.out.println("4. Afficher la liste des voitures stationnées");
            System.out.println("5. Rechercher une voiture");
            System.out.println("6. Afficher l'historique des entrées/sorties");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.print("Immatriculation : ");
                    String immatriculation = scanner.nextLine();
                    System.out.print("Modèle : ");
                    String modele = scanner.nextLine();
                    System.out.print("Couleur : ");
                    String couleur = scanner.nextLine();
                    System.out.print("Type de véhicule (VOITURE/MOTO) : ");
                    String typeStr = scanner.nextLine();
                    GestionClass.TypePlace type = GestionClass.TypePlace.valueOf(typeStr.toUpperCase());

                    GestionClass.Voiture nouvelleVoiture = new GestionClass.Voiture(immatriculation, modele, couleur);
                    GestionClass.Ticket nouveauTicket = parking.enregistrerEntree(nouvelleVoiture, type);

                    if (nouveauTicket != null) {
                        System.out.println("Place n°" + nouveauTicket.getPlace().getNumero() + " attribuée.");
                    }
                }
                case 2 -> {
                    System.out.print("Immatriculation de la voiture : ");
                    String immatriculation = scanner.nextLine();
                    boolean sortie = false;

                    for (GestionClass.Ticket ticket : parking.getTickets()) {
                        if (ticket.getVoiture().getImmatriculation().equals(immatriculation) && ticket.getHeureSortie() == null) {
                            parking.enregistrerSortie(ticket);
                            GestionClass.Voiture voiture = ticket.getVoiture();
                            System.out.println("Sortie enregistrée pour le véhicule :");
                            System.out.println(" - Immatriculation : " + voiture.getImmatriculation());
                            System.out.println(" - Modèle : " + voiture.getModele());
                            System.out.println(" - Couleur : " + voiture.getCouleur());
                            System.out.println("Temps stationné : " + java.time.Duration.between(ticket.getHeureArrive(), ticket.getHeureSortie()).toMinutes() + " minutes");
                            System.out.println("Prix total : " + ticket.getPrix() + "€");
                            sortie = true;
                            break;
                        }
                    }
                }
                case 3 -> parking.afficherPlacesDisponibles();

                case 4 -> parking.listerVoituresStationnees();

                case 5 -> {
                    System.out.print("Immatriculation à rechercher : ");
                    String immatriculation = scanner.nextLine();
                    parking.rechercherVoiture(immatriculation);
                }

                case 6 -> parking.afficherHistorique();

                case 7 -> {
                    return;
                }
            }
        }
    }
}
