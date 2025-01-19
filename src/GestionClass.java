import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestionClass {

    static class Parking {
        private String nom;
        private String adresse;
        private List<Place> places;
        private List<Ticket> tickets;

        public Parking(String nom, String adresse, int nombrePlacesVoiture, int nombrePlacesMoto) {
            this.nom = nom;
            this.adresse = adresse;
            this.places = new ArrayList<>();
            this.tickets = new ArrayList<>();

            for (int i = 1; i <= nombrePlacesVoiture; i++) {
                places.add(new Place(i, TypePlace.VOITURE));
            }

            for (int i = nombrePlacesVoiture + 1; i <= nombrePlacesVoiture + nombrePlacesMoto; i++) {
                places.add(new Place(i, TypePlace.MOTO));
            }
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public List<Place> getPlaces() {
            return places;
        }

        public void setPlaces(List<Place> places) {
            this.places = places;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }
        public Ticket enregistrerEntree(Voiture voiture, TypePlace type) {
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                if (place.isEstLibre() && place.getType() == type) {
                    Ticket ticket = new Ticket(voiture, place, LocalDateTime.now());
                    tickets.add(ticket);
                    place.setEstLibre(false);
                    return ticket;
                }
            }
            System.out.println("Aucune place disponible pour " + type);

            return null;
        }

        public void enregistrerSortie(Ticket ticket) {
            ticket.setHeureSortie(LocalDateTime.now());
            ticket.calculerPrix();
            ticket.getPlace().setEstLibre(true);
        }

        public void afficherPlacesDisponibles() {
            int placesVoitureLibres = 0;
            int placesMotoLibres = 0;

            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                if (place.isEstLibre() && place.getType() == TypePlace.VOITURE) {
                    placesVoitureLibres++;
                } else if (place.isEstLibre() && place.getType() == TypePlace.MOTO) {
                    placesMotoLibres++;
                }
            }

            System.out.println("Places disponibles :");
            System.out.println("Voitures : " + placesVoitureLibres);
            System.out.println("Motos : " + placesMotoLibres);
        }

        public void listerVoituresStationnees() {
            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                if (ticket.getHeureSortie() == null) {
                    System.out.println(ticket.getVoiture());
                }
            }
        }

        public void rechercherVoiture(String immatriculation) {
            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                if (ticket.getVoiture().getImmatriculation().equals(immatriculation) && ticket.getHeureSortie() == null) {
                    System.out.println("Voiture trouvée : " + ticket);
                    return;
                }
            }
            System.out.println("Voiture non trouvée.");
        }

        public void afficherHistorique(){
            System.out.println("Historique : ");
        }
    }



    enum TypePlace {
        VOITURE,
        MOTO;
    }





    static class Voiture {
        private String immatriculation;
        private String modele;
        private String couleur;

        public Voiture(String immatriculation, String modele, String couleur) {
            this.immatriculation = immatriculation;
            this.modele = modele;
            this.couleur = couleur;
        }

        public String getImmatriculation() {
            return immatriculation;
        }

        public void setImmatriculation(String immatriculation) {
            this.immatriculation = immatriculation;
        }

        public String getModele() {
            return modele;
        }

        public void setModele(String modele) {
            this.modele = modele;
        }

        public String getCouleur() {
            return couleur;
        }

        public void setCouleur(String couleur) {
            this.couleur = couleur;
        }
    }




    static class Place {
        private int numero;
        private TypePlace type;
        private boolean estLibre;

        public Place(int numero, TypePlace type) {
            this.numero = numero;
            this.type = type;
            this.estLibre = true;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public TypePlace getType() {
            return type;
        }

        public void setType(TypePlace type) {
            this.type = type;
        }

        public boolean isEstLibre() {
            return estLibre;
        }

        public void setEstLibre(boolean estLibre) {
            this.estLibre = estLibre;
        }
    }




    static class Ticket {
        private Voiture voiture;
        private Place place;
        private LocalDateTime heureArrive;
        private LocalDateTime heureSortie;
        private double prix;

        public Ticket(Voiture voiture, Place place, LocalDateTime heureArrive) {
            this.voiture = voiture;
            this.place = place;
            this.heureArrive = heureArrive;
        }

        public Voiture getVoiture() {
            return voiture;
        }

        public void setVoiture(Voiture voiture) {
            this.voiture = voiture;
        }

        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public LocalDateTime getHeureArrive() {
            return heureArrive;
        }

        public void setHeureEntree(LocalDateTime heureArrive ){
            this.heureArrive = heureArrive;
        }

        public LocalDateTime getHeureSortie() {
            return heureSortie;
        }

        public void setHeureSortie(LocalDateTime heureSortie) {
            this.heureSortie = heureSortie;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public void calculerPrix() {
            float tempsHeure = java.time.Duration.between(heureArrive, heureSortie).toHours();
            this.prix = tempsHeure * 5.0;
        }
    }


}
